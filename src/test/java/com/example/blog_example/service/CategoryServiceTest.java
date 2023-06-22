package com.example.blog_example.service;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.dto.category.lower.LowerCategoryFindByUpperDTO;
import com.example.blog_example.model.dto.category.lower.LowerCategoryFindDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryFindDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
    @Autowired private UpperCategoryService upperCategoryService;
    @Autowired private UpperCategoryRepository upperCategoryRepository;
    @Autowired private LowerCategoryService lowerCategoryService;
    @Autowired private LowerCategoryRepository lowerCategoryRepository;

    @BeforeEach
    public void setUp() {
        UpperCategory upperCategory =
                upperCategoryRepository.save(
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
    public void tearDown() {
        upperCategoryRepository.deleteAll();
    }

    @Test
    public void test() {
        System.out.println("test");
    }

    @Test
    public void findTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        UpperCategoryFindDTO upperCategoryFindDTO = new UpperCategoryFindDTO(upperCategoryNo);
        LowerCategoryFindDTO lowerCategoryFindDTO = new LowerCategoryFindDTO(lowerCategoryNo);

        assertThat(upperCategoryNo).isEqualTo(upperCategoryService.find(upperCategoryFindDTO).getUpperCategoryNo());
        assertThat(lowerCategoryNo).isEqualTo(lowerCategoryService.find(lowerCategoryFindDTO).getLowerCategoryNo());
    }

    @Test
    public void findByUpperCategoryTest() {
        UpperCategory upperCategory = upperCategoryRepository.findAll().get(0);

        lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test1")
                        .upperCategory(upperCategory)
                        .build());

        LowerCategoryFindByUpperDTO lowerCategoryFindByUpperDTO =
                new LowerCategoryFindByUpperDTO(upperCategory.getUpperCategoryNo());

        List<LowerCategoryVO> lowerCategoryVOs =
                lowerCategoryService.findByUpperCategory(lowerCategoryFindByUpperDTO);

        assertThat("test").isEqualTo(lowerCategoryVOs.get(0).getName());
        assertThat("test1").isEqualTo(lowerCategoryVOs.get(1).getName());
    }

    @Test
    public void saveTest() {
        UpperCategorySaveDTO upperCategorySaveDTO = new UpperCategorySaveDTO("test");

        Long upperCategoryNo = upperCategoryService.save(upperCategorySaveDTO);

        assertThat(upperCategoryNo).isEqualTo(upperCategoryRepository.findAll().get(1).getUpperCategoryNo());
    }
}
