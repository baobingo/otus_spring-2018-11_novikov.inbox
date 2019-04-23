package ru.otus.spring18.security;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import ru.otus.spring18.config.ConfigProperties;
import ru.otus.spring18.domain.security.Role;
import ru.otus.spring18.domain.security.UserAccount;

import java.util.Optional;

@Component
public class SimpleUserDetailsService implements ReactiveUserDetailsService{

    private RestTemplate restTemplate;
    private ConfigProperties configProperties;
    private PasswordEncoder bCryptPasswordEncoder;
    private static Logger logger = LoggerFactory.getLogger(SimpleUserDetailsService.class);

    public SimpleUserDetailsService(RestTemplate restTemplate, ConfigProperties configProperties, PasswordEncoder bCryptPasswordEncoder) {
        this.restTemplate = restTemplate;
        this.configProperties = configProperties;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultUser", groupKey = "UserService", commandKey = "findBy")
    public Mono<UserDetails> findByUsername(String username) {
        UserAccount userAccount = restTemplate.getForObject(configProperties.getUrl() + "/user/{username}",
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
