package com.example.blog_example.controller;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.domain.user.user.User;
import com.example.blog_example.model.domain.user.user.UserRepository;
import com.example.blog_example.model.dto.category.lower.LowerCategorySaveDTO;
import com.example.blog_example.model.dto.category.lower.LowerCategoryUpdateDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {
    private final String URL = "/category";

    @Autowired private TestRestTemplate testRestTemplate;
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
                        .password("testjsldkf@1234")
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

    @AfterEach
    public void teardown() {
        upperCategoryRepository.deleteAll();
    }

    @Test
    public void test() { System.out.println("test"); }

    @Test
    public void findAllUpperCategoryTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();

        ResponseEntity<List<UpperCategoryVO>> responseEntity =
                testRestTemplate.exchange(URL + "/upper/list/user?no=" + userNo, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UpperCategoryVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(upperCategoryRepository.findAll().size());
    }

    @Test
    public void findAllLowerCategoryTest() {
        ResponseEntity<List<LowerCategoryVO>> responseEntity =
                testRestTemplate.exchange(URL + "/lower/list", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<LowerCategoryVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(lowerCategoryRepository.findAll().size());
    }

    @Test
    public void findUpperCategoryTest() {
        UpperCategory upperCategory = upperCategoryRepository.findAll().get(0);

        ResponseEntity<UpperCategoryVO> responseEntity =
                testRestTemplate.getForEntity(
                        URL + "/upper?no=" + upperCategory.getUpperCategoryNo(), UpperCategoryVO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(upperCategory.getName());
    }

    @Test
    public void findLowerCategoryTest() {
        LowerCategory lowerCategory = lowerCategoryRepository.findAll().get(0);

        ResponseEntity<LowerCategoryVO> responseEntity =
                testRestTemplate.getForEntity(
                        URL + "/lower?no=" + lowerCategory.getLowerCategoryNo(), LowerCategoryVO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(lowerCategory.getName());
    }

    @Test
    public void findLowerCategoryByUpperTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        ResponseEntity<List<LowerCategoryVO>> responseEntity =
                testRestTemplate.exchange(
                        URL + "/lower/list/upper?no=" + upperCategoryNo, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<LowerCategoryVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isEqualTo(1);
    }

    @Test
    public void saveUpperCategoryTest() {
        UpperCategorySaveDTO upperCategorySaveDTO = UpperCategorySaveDTO.builder()
                .name("test")
                .userNo(userRepository.findAll().get(0).getUserNo())
                .build();

        ResponseEntity<Long> responseEntity =
                testRestTemplate.postForEntity(URL + "/upper", upperCategorySaveDTO, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(upperCategoryRepository.findById(responseEntity.getBody())
                .orElseThrow(IllegalArgumentException::new)).isNotNull();

        UpperCategory upperCategory = upperCategoryRepository.findById(responseEntity.getBody())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(lowerCategoryRepository.findByUpperCategory(upperCategory)).isNotNull();
    }

    @Test
    public void saveLowerCategoryTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        LowerCategorySaveDTO lowerCategorySaveDTO = LowerCategorySaveDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .name("test1")
                .build();

        ResponseEntity<Long> responseEntity =
                testRestTemplate.postForEntity(URL + "/lower", lowerCategorySaveDTO, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody())
                .isEqualTo(lowerCategoryRepository.findAll().get(1).getLowerCategoryNo());
    }

    @Test
    public void updateUpperCategoryTest() {
        String name = "test1";
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        UpperCategoryUpdateDTO upperCategoryUpdateDTO = UpperCategoryUpdateDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .name(name)
                .build();
        HttpEntity<UpperCategoryUpdateDTO> requestEntity = new HttpEntity<>(upperCategoryUpdateDTO);

        ResponseEntity<Long> responseEntity = testRestTemplate
                .exchange(URL + "/upper", HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(upperCategoryRepository.findAll().get(0).getName()).isEqualTo(name);
    }

    @Test
    public void updateLowerCategoryTest() {
        String name = "test1";
        LowerCategory lowerCategory = lowerCategoryRepository.findAll().get(0);
        Long lowerCategoryNo = lowerCategory.getLowerCategoryNo();
        Long upperCategoryNo = lowerCategory.getUpperCategory().getUpperCategoryNo();

        LowerCategoryUpdateDTO lowerCategoryUpdateDTO = LowerCategoryUpdateDTO.builder()
                .lowerCategoryNo(lowerCategoryNo)
                .upperCategoryNo(upperCategoryNo)
                .name(name)
                .build();
        HttpEntity<LowerCategoryUpdateDTO> requestEntity = new HttpEntity<>(lowerCategoryUpdateDTO);

        ResponseEntity<Long> responseEntity = testRestTemplate
                .exchange(URL + "/lower", HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(lowerCategoryRepository.findAll().get(0).getName()).isEqualTo(name);
    }

    @Test
    public void deleteUpperCategoryTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        ResponseEntity<Void> responseEntity = testRestTemplate
                .exchange(URL + "/upper?no=" + upperCategoryNo, HttpMethod.DELETE,
                        new HttpEntity<>(upperCategoryNo), Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(upperCategoryRepository.findById(upperCategoryNo)).isEmpty();
    }

    @Test
    public void deleteLowerCategoryTest() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        ResponseEntity<Void> responseEntity = testRestTemplate
                .exchange(URL + "/lower?no=" + lowerCategoryNo, HttpMethod.DELETE,
                        new HttpEntity<>(lowerCategoryNo), Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(lowerCategoryRepository.findById(lowerCategoryNo)).isEmpty();
    }
}
