package ru.otus.spring16.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.spring16.domain.security.Role;
import ru.otus.spring16.domain.security.UserAccount;

@ChangeLog
public class Security {

    @ChangeSet(order = "002", id = "userFillout", author = "baobingo")
    public void userFillout(MongoTemplate mongoTemplate) {
        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();

        UserAccount user = new UserAccount("user", bCryptPasswordEncoder.encode("password"));
        Role userRole = new Role("ROLE_USER");
        mongoTemplate.insert(userRole);
        user.addRoles(userRole);
        mongoTemplate.insert(user);

        UserAccount admin = new UserAccount("admin", bCryptPasswordEncoder.encode("password"));
        Role adminRole = new Role("ROLE_ADMIN");
        mongoTemplate.insert(adminRole);
        admin.addRoles(adminRole);
        admin.addRoles(userRole);
        mongoTemplate.insert(admin);
    }
}