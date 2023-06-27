package com.example.blog_example.model;

import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCount;
import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCountRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BlogVisitCountRepositoryTest {
    @Autowired private BlogVisitCountRepository blogVisitCountRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .build());

        blogVisitCountRepository.save(
                BlogVisitCount.builder()
                        .user(user)
                        .date(LocalDate.of(2023, 1, 1))
                        .build());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void addVisitCountTest() {
        BlogVisitCount blogVisitCount = blogVisitCountRepository.findAll().get(0);

        assertThat(blogVisitCount.addVisitCount()).isEqualTo(1);
        assertThat(blogVisitCount.addVisitCount()).isEqualTo(2);

        assertThat(blogVisitCountRepository.findAll().get(0).getVisitCount()).isEqualTo(2);
    }

    @Test
    public void sumVisitCountByUserTest1() {
        User user = userRepository.findAll().get(0);

        BlogVisitCount blogVisitCount = blogVisitCountRepository.save(
                BlogVisitCount.builder()
                        .user(user)
                        .date(LocalDate.of(2023, 1, 2))
                        .build());

        assertThat(blogVisitCountRepository.sumVisitCountByUser(user)).isEqualTo(0);

        blogVisitCount.addVisitCount();

        assertThat(blogVisitCountRepository.sumVisitCountByUser(user)).isEqualTo(1);
    }

    @Test
    public void sumVisitCountByUserTest2() {
        User user = userRepository.findAll().get(0);

        assertThat(blogVisitCountRepository.sumVisitCountByUser(user)).isEqualTo(0);

        BlogVisitCount blogVisitCount = blogVisitCountRepository.findAll().get(0);

        blogVisitCount.addVisitCount();

        assertThat(blogVisitCountRepository.sumVisitCountByUser(user)).isEqualTo(1);
    }
}
