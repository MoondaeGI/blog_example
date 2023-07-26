package com.example.blog_example.service.user;

import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCount;
import com.example.blog_example.model.domain.user.blog_visit_count.BlogVisitCountRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.user.blog_visit_count.BlogVisitCountDailyDTO;
import com.example.blog_example.model.vo.user.BlogVisitCountVO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BlogVisitCountService {
    private final BlogVisitCountRepository blogVisitCountRepository;
    private final UserRepository userRepository;

    @Transactional
    public BlogVisitCountVO find(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 번호를 가진 유저(블로거)가 없습니다."));
        BlogVisitCount blogVisitCount = isExist(userNo) ?
                blogVisitCountRepository.findByUserAndDate(user, LocalDate.now()) :
                blogVisitCountRepository.save(
                        BlogVisitCount.builder()
                                .user(user)
                                .date(LocalDate.now())
                                .build());

        Integer totalVisit = blogVisitCountRepository.sumVisitCountByUser(user).intValue();

        return BlogVisitCountVO.of(totalVisit, blogVisitCount.getVisitCount());
    }

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
    public void addVisitCount(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 번호를 가진 유저(블로거)가 없습니다."));
        BlogVisitCount blogVisitCount = blogVisitCountRepository.findByUserAndDate(user, LocalDate.now());

        blogVisitCount.addVisitCount();
    }

    @Transactional(readOnly = true)
    public Integer countTotalVisit(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그가 없습니다."));

        return blogVisitCountRepository.sumVisitCountByUser(user).intValue();
    }

    @Transactional(readOnly = true)
    public Integer countDailyVisit(BlogVisitCountDailyDTO blogVisitCountDailyDTO) {
        User user = userRepository.findById(blogVisitCountDailyDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그가 없습니다."));

        return blogVisitCountRepository.findByUserAndDate(user, blogVisitCountDailyDTO.getDate())
                .getVisitCount();
    }

    @Transactional(readOnly = true)
    public Boolean isVisit(Long visitorNo, Long bloggerNo) {
        User visitor = userRepository.findById(visitorNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저(방문자)가 없습니다."));

        User blogger = userRepository.findById(bloggerNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저(블로거)가 없습니다."));

        return Objects.equals(visitor, blogger);
    }

    @Transactional(readOnly = true)
    public Boolean isExist(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 블로그가 없습니다."));

        return blogVisitCountRepository.findByUserAndDate(user, LocalDate.now()) != null;
    }
}
