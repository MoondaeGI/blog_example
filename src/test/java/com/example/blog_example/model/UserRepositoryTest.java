package com.example.blog_example.model;

import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void test() {
        System.out.println("test");
    }

    @Test
    public void passwordEncodeTest() {
        String password = "1234";

        User user = User.builder()
                .name("test")
                .blogName("test")
                .email("test1234@test.com")
                .password(password)
                .build();

        user.encryptPassword(passwordEncoder);

        assertThat(password).isNotEqualTo(user.getPassword());
    }
}
