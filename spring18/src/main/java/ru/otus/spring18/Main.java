package ru.otus.spring18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
//import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.acls.mongodb.MongoDBMutableAclService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;
import ru.otus.spring18.config.ConfigProperties;
import ru.otus.spring18.repository.BookRepositoryReactive;

@SpringBootApplication
@EnableHystrix
//@EnableHystrixDashboard
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        /*
            Получаем контекст
         */
        context = SpringApplication.run(Main.class);

        /*
            В ручную аутенфицируемся
         */
        ReactiveAuthenticationManager authenticationManager = context.getBean(UserDetailsRepositoryReactiveAuthenticationManager.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("admin", "password");
        Mono<Authentication> authentication = authenticationManager.authenticate(token);
        Authentication authenticationInstance = authentication.block();

        /*
            Создаем ACL записи для наших объектов
         */

        BookRepositoryReactive bookRepositoryReactive = context.getBean(BookRepositoryReactive.class);
        MongoDBMutableAclService aclService = context.getBean(MongoDBMutableAclService.class);

        bookRepositoryReactive.findAll().take(5).log()
                .subscribe(book -> {
                    /*
                        Закладываем в ните принципал в секурити контекст, требуется для .createAcl()
                     */
                    SecurityContextHolder.getContext().setAuthentication(authenticationInstance);
                    ObjectIdentity objectIdentity = new ObjectIdentityImpl(book);
                    MutableAcl mutableAcl;
                    try {
                        mutableAcl = aclService.createAcl(objectIdentity);
                        mutableAcl.insertAce( 0, BasePermission.ADMINISTRATION, mutableAcl.getOwner(), true);
                        aclService.updateAcl(mutableAcl);
                    }catch (AlreadyExistsException e){
                        logger.warn("ACL exist.");
                    }
                });
    }
}
