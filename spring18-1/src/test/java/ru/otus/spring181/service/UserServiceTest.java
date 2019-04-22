package ru.otus.spring181.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring181.domain.UserAccount;
import ru.otus.spring181.repository.UserServiceRepo;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserService.class)
class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceRepo userServiceRepo;

    @Test
    void getUserAccountByUsername() throws Exception{
        when(userServiceRepo.findByUsername("user")).thenReturn(new UserAccount("user", ""));
        mockMvc.perform(get("/user/user").contentType(APPLICATION_JSON)).andExpect(jsonPath("$.username", is("user"))).andExpect(status().isOk());
    }
}