package ru.otus.spring18.security;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import ru.otus.spring18.domain.security.Role;
import ru.otus.spring18.domain.security.UserAccount;

import java.util.Optional;

@Component
public class SimpleUserDetailsService implements ReactiveUserDetailsService{

    private RestTemplate restTemplate;
    private String ushost;
    private BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();
    private static Logger logger = LoggerFactory.getLogger(SimpleUserDetailsService.class);

    public SimpleUserDetailsService(RestTemplate restTemplate, @Value("${spring.data.mongodb.url}") String ushost) {
        this.restTemplate = restTemplate;
        this.ushost = ushost;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultUser", groupKey = "UserService", commandKey = "findBy")
    public Mono<UserDetails> findByUsername(String username) {
        UserAccount userAccount = restTemplate.getForObject(ushost + "/user/{username}",
                        UserAccount.class, username);
        System.out.println(userAccount);
        return Mono.justOrEmpty(Optional.of(userAccount));
    }

    public Mono<UserDetails> getDefaultUser(String username){
        logger.info("User service is down, set default user");
        UserAccount user = new UserAccount("user", bCryptPasswordEncoder.encode("password"));
        Role userRole = new Role("ROLE_USER");
        user.addRoles(userRole);
        return Mono.just(user);
    }
}
