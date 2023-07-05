package com.example.blog_example.service.security;

import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.auth.JwtRequestDTO;
import com.example.blog_example.model.dto.auth.UserSignupDTO;
import com.example.blog_example.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String signup(UserSignupDTO userSignUpDTO) {
        if (userRepository.existsByEmail(userSignUpDTO.getEmail())) return null;

        User user = User.builder()
                .name(userSignUpDTO.getName())
                .blogName(userSignUpDTO.getBlogName())
                .email(userSignUpDTO.getEmail())
                .password(userSignUpDTO.getPassword())
                .build();

        user.encryptPassword(passwordEncoder);

        return userRepository.save(user)
                .getEmail();
    }

    @Transactional
    public String login(JwtRequestDTO jwtRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequestDTO.getEmail(), jwtRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        return principal.getUsername();
    }
}
