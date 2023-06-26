package com.example.blog_example.service.user;

import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCount;
import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCountRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountAddDTO;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountByUserDTO;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogVisitCountService {
    private final BlogVisitCountRepository blogVisitCountRepository;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void saveAll() {
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            blogVisitCountRepository.save(
                    BlogVisitCount.builder()
                            .user(user)
                            .date(LocalDate.now())
                            .build());
        }
    }

    @Transactional
    public Integer addVisitCount(BlogVisitCountAddDTO blogVisitCountAddDTO) {
        BlogVisitCount blogVisitCount =
                blogVisitCountRepository.findById(blogVisitCountAddDTO.getBlogVisitCountNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 블로그가 없습니다."));

        return blogVisitCount.addVisitCount();
    }

    @Transactional(readOnly = true)
    public Integer countAll(BlogVisitCountByUserDTO blogVisitCountByUserDTO) {
        User user = userRepository.findById(blogVisitCountByUserDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그가 없습니다."));

        return blogVisitCountRepository.countByUser(user).intValue();
    }

    @Transactional(readOnly = true)
    public Integer countDailyVisit(BlogVisitCountDailyDTO blogVisitCountDailyDTO) {
        User user = userRepository.findById(blogVisitCountDailyDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그가 없습니다."));

        return blogVisitCountRepository.findByUserAndDate(user, blogVisitCountDailyDTO.getDate())
                .getVisitCount();
    }
}
