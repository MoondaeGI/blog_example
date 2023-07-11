package com.example.blog_example.model;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private LowerCategoryRepository lowerCategoryRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .email("test1234@test.com")
                        .password("tesdfsfew#@3")
                        .build());

        UpperCategory upperCategory = upperCategoryRepository.save(
                UpperCategory.builder()
                        .name("test")
                        .user(user)
                        .build());

        lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test")
                        .upperCategory(upperCategory)
                        .build());
    }

    @Test
    public void test() { System.out.println("test"); }

    @DisplayName("유저로 상위 카테고리 검색 동작 확인")
    @Test
    public void findUpperCategoryByUserTest() {
        User user = userRepository.findAll().get(0);

        assertThat(upperCategoryRepository.findByUser(user).size()).isEqualTo(1);
    }
}
