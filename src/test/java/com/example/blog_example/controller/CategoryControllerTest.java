package com.example.blog_example.controller;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
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

    @Test
    public void upperCategoryFindAllTest() {
        ResponseEntity<List<UpperCategoryVO>> responseEntity =
                testRestTemplate.exchange(URL + "/upper/list", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UpperCategoryVO>>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size())
                .isEqualTo(upperCategoryRepository.findAll().size());
    }

    @Test
    public void upperCategoryFindTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        ResponseEntity<UpperCategoryVO> responseEntity =
                testRestTemplate.getForEntity(URL + "/upper?no=" + upperCategoryNo, UpperCategoryVO.class);

        UpperCategory upperCategory = upperCategoryRepository.findAll().get(0);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getName()).isEqualTo(upperCategory.getName());
    }

    @Test
    public void upperCategorySaveTest() {
        UpperCategorySaveDTO upperCategorySaveDTO = UpperCategorySaveDTO.builder()
                .name("test")
                .build();

        ResponseEntity<Long> responseEntity =
                testRestTemplate.postForEntity(URL + "/upper", upperCategorySaveDTO, Long.class);

        UpperCategory upperCategory = upperCategoryRepository.findAll().get(1);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(upperCategory.getUpperCategoryNo());
    }

    @Test
    public void upperCategoryUpdateTest() {
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
    public void upperCategoryDeleteTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        ResponseEntity<Void> responseEntity = testRestTemplate
                .exchange(URL + "/upper?no=" + upperCategoryNo, HttpMethod.DELETE, new HttpEntity<>(upperCategoryNo), Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(upperCategoryRepository.findById(upperCategoryNo)).isEmpty();
    }
}
