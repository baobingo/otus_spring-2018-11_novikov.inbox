package ru.otus.spring181.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring181.domain.UserAccount;
import ru.otus.spring181.repository.UserServiceRepo;

@RestController
public class UserService {

    UserServiceRepo userServiceRepo;

    public UserService(UserServiceRepo userServiceRepo) {
        this.userServiceRepo = userServiceRepo;
    }

    @GetMapping("/user/{username}")
    public UserAccount getUserAccountByUsername(@PathVariable String username){
        return userServiceRepo.findByUsername(username);
    }
}
