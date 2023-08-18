package com.example.blog_example.service.user;

import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.user.user.UserInfoUpdateDTO;
import com.example.blog_example.model.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserVO find(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return UserVO.from(user);
    }

    @Transactional(readOnly = true)
    public Boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Boolean isExistByName(String name) {
        return userRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public Boolean isExistByBlogName(String blogName) {
        return userRepository.existsByBlogName(blogName);
    }

    @Transactional
    public Long update(UserInfoUpdateDTO userInfoUpdateDTO) {
        User user = userRepository.findById(userInfoUpdateDTO.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 번호를 가진 유저가 없습니다."));

        user.updateUserInfo(userInfoUpdateDTO.getName(), userInfoUpdateDTO.getBlogName());

        return user.getUserNo();
    }
}
