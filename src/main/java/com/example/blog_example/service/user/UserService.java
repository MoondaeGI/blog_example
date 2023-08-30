package com.example.blog_example.service.user;

import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.auth.UserSignupDTO;
import com.example.blog_example.model.dto.user.user.UserInfoUpdateDTO;
import com.example.blog_example.model.vo.user.UserVO;
import com.example.blog_example.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserVO find(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return UserVO.from(user);
    }

    @Transactional
    public Long save(UserSignupDTO userSignUpDTO) {
        if (userRepository.existsByEmail(userSignUpDTO.getEmail()) &&
                userRepository.existsByBlogName(userSignUpDTO.getBlogName()) &&
                userRepository.existsByName(userSignUpDTO.getName()))
            return null;

        User user = User.builder()
                .name(userSignUpDTO.getName())
                .blogName(userSignUpDTO.getBlogName())
                .email(userSignUpDTO.getEmail())
                .password(userSignUpDTO.getPassword())
                .build();

        user.encryptPassword(passwordEncoder);

        return userRepository.save(user)
                .getUserNo();
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
        httpSession.setAttribute("user", UserVO.from(user));

        return new CustomUserDetails(user);
    }
}
