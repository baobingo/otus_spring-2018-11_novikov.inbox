package ru.otus.spring13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
import ru.otus.spring13.repository.BookRepositoryReactive;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) {
        /*
            Получаем контекст
         */
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Main.class);

        /*
            В ручную аутенфицируемся
         */
        ReactiveAuthenticationManager authenticationManager = configurableApplicationContext.getBean(UserDetailsRepositoryReactiveAuthenticationManager.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("admin", "password");
        Mono<Authentication> authentication = authenticationManager.authenticate(token);
        Authentication authenticationInstance = authentication.block();

        /*
            Создаем ACL записи для наших объектов
         */

        BookRepositoryReactive bookRepositoryReactive = configurableApplicationContext.getBean(BookRepositoryReactive.class);
        MongoDBMutableAclService aclService = configurableApplicationContext.getBean(MongoDBMutableAclService.class);

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
                    }catch (AlreadyExistsException e){
                        aclService.deleteAcl(objectIdentity, true);
                        mutableAcl = aclService.createAcl(objectIdentity);
                    }

                    mutableAcl.insertAce( 0, BasePermission.ADMINISTRATION, mutableAcl.getOwner(), true);

                    aclService.updateAcl(mutableAcl);
                });
    }
}
