package com.example.blog_example.service.user;

import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.user.user.UserSignupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signup(UserSignupDTO userSignUpDTO) {
        if (userRepository.existsByEmail(userSignUpDTO.getEmail())) return null;

        User user = new User(userSignUpDTO);
        user.encryptPassword(passwordEncoder);

        return userRepository.save(user)
                .getEmail();
    }

    public void login() {}
}
