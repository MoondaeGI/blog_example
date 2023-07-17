package com.example.blog_example.service;

import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCount;
import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCountRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.service.user.BlogVisitCountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BlogVisitCountServiceTest {
    @Autowired private BlogVisitCountService blogVisitCountService;

    @Autowired private BlogVisitCountRepository blogVisitCountRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .email("test")
                        .password("test")
                        .build());

        blogVisitCountRepository.save(
                BlogVisitCount.builder()
                        .user(user)
                        .date(LocalDate.of(2023, 7, 8))
                        .build());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @Test
    public void addVisitCountTest() {
        Long visitorNo = userRepository.save(
                User.builder()
                        .blogName("test1")
                        .name("test1")
                        .email("test1234@gmail.com")
                        .password("jklskjf@")
                        .build())
                .getUserNo();
        BlogVisitCount blogVisitCount = blogVisitCountRepository.findAll().get(0);
        Long bloggerNo = blogVisitCount.getUser().getUserNo();

        blogVisitCountService.addVisitCount(bloggerNo);
        assertThat(blogVisitCount.getVisitCount()).isEqualTo(1);

        blogVisitCountService.addVisitCount(bloggerNo);
        assertThat(blogVisitCount.getVisitCount()).isEqualTo(2);
    }

    @Test
    public void countAllVisitByUserTest() {
        Long visitorNo = userRepository.save(
                        User.builder()
                                .blogName("test1")
                                .name("test1")
                                .email("test1234@gmail.com")
                                .password("jklskjf@")
                                .build())
                .getUserNo();
        Long bloggerNo = blogVisitCountRepository.findAll().get(0).getUser().getUserNo();

        assertThat(blogVisitCountService.countTotalVisit(bloggerNo)).isEqualTo(0);

        blogVisitCountService.addVisitCount(bloggerNo);

        assertThat(blogVisitCountService.countTotalVisit(bloggerNo)).isEqualTo(1);
    }

    @Test
    public void countDailyVisitTest() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Long userNo = userRepository.findAll().get(0).getUserNo();

        BlogVisitCountDailyDTO blogVisitCountDailyDTO = new BlogVisitCountDailyDTO(userNo, date);

        assertThat(blogVisitCountService.countDailyVisit(blogVisitCountDailyDTO)).isEqualTo(0);
    }
}
