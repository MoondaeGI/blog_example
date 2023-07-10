package com.example.blog_example.util;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {
    private final String URL = "/category/upper";

    @Autowired private TestRestTemplate testRestTemplate;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private LowerCategoryRepository lowerCategoryRepository;

    @BeforeEach
    public void setup() {
        UpperCategory upperCategory = upperCategoryRepository.save(
                UpperCategory.builder()
                        .name("test")
                        .build());

        lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test")
                        .upperCategory(upperCategory)
                        .build());
    }

    @AfterEach
    public void teardown() {
        upperCategoryRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @DisplayName("잘못된 인자를 사용해 발생하는 IllegalArgumentException 동작 확인")
    @Test
    public void handleIllegalArgumentExceptionTest() {
        ResponseEntity<UpperCategoryVO> responseEntity = testRestTemplate
                .getForEntity(URL + "?no=1000", UpperCategoryVO.class);

        System.out.println(responseEntity.getStatusCodeValue());
    }
}
