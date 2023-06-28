package com.example.blog_example.service;

import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCount;
import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCountRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountAddDTO;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountAllByUserDTO;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.service.user.BlogVisitCountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
                        .date(LocalDate.of(2023, 1, 1))
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
        Long blogVisitCountNo = blogVisitCountRepository.findAll().get(0).getBlogVisitCountNo();

        BlogVisitCountAddDTO blogVisitCountAddDTO = new BlogVisitCountAddDTO(blogVisitCountNo);

        assertThat(blogVisitCountService.addVisitCount(blogVisitCountAddDTO)).isEqualTo(1);
        assertThat(blogVisitCountService.addVisitCount(blogVisitCountAddDTO)).isEqualTo(2);
    }

    @Test
    public void countAllVisitByUserTest1() {
        Long blogVisitCountNo = blogVisitCountRepository.findAll().get(0).getBlogVisitCountNo();
        Long userNo = userRepository.findAll().get(0).getUserNo();

        BlogVisitCountAllByUserDTO blogVisitCountAllByUserDTO = new BlogVisitCountAllByUserDTO(userNo);

        assertThat(blogVisitCountService.countAllVisitByUser(blogVisitCountAllByUserDTO)).isEqualTo(0);

        BlogVisitCountAddDTO blogVisitCountAddDTO = new BlogVisitCountAddDTO(blogVisitCountNo);
        blogVisitCountService.addVisitCount(blogVisitCountAddDTO);

        assertThat(blogVisitCountService.countAllVisitByUser(blogVisitCountAllByUserDTO)).isEqualTo(1);
    }

    @Test
    public void countAllVisitByUserTest2() {
        User user = userRepository.findAll().get(0);
        Long userNo = user.getUserNo();

        BlogVisitCountAllByUserDTO blogVisitCountAllByUserDTO = new BlogVisitCountAllByUserDTO(userNo);

        assertThat(blogVisitCountService.countAllVisitByUser(blogVisitCountAllByUserDTO)).isEqualTo(0);

        Long blogVisitCountNo = blogVisitCountRepository.save(
                BlogVisitCount.builder()
                        .user(user)
                        .date(LocalDate.of(2022, 1, 1))
                        .build())
                .getBlogVisitCountNo();

        BlogVisitCountAddDTO blogVisitCountAddDTO = new BlogVisitCountAddDTO(blogVisitCountNo);
        blogVisitCountService.addVisitCount(blogVisitCountAddDTO);

        assertThat(blogVisitCountService.countAllVisitByUser(blogVisitCountAllByUserDTO)).isEqualTo(1);
    }

    @Test
    public void countDailyVisitTest() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Long userNo = userRepository.findAll().get(0).getUserNo();

        BlogVisitCountDailyDTO blogVisitCountDailyDTO = new BlogVisitCountDailyDTO(userNo, date);

        assertThat(blogVisitCountService.countDailyVisit(blogVisitCountDailyDTO)).isEqualTo(0);
    }
}
