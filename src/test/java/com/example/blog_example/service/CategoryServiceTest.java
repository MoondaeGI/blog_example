package com.example.blog_example.service;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.dto.category.lower.*;
import com.example.blog_example.model.dto.category.upper.UpperCategoryDeleteDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryFindDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
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
    public void upperCategoryFindAllTest() {
        Integer size = upperCategoryRepository.findAll().size();

        assertThat(size).isEqualTo(upperCategoryService.findAll().size());
    }

    @Test
    public void lowerCategoryFindAllTest() {
        Integer size = lowerCategoryRepository.findAll().size();

        assertThat(size).isEqualTo(lowerCategoryService.findAll().size());
    }

    @Test
    public void upperCategoryFindTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        UpperCategoryFindDTO upperCategoryFindDTO = new UpperCategoryFindDTO(upperCategoryNo);

        assertThat(upperCategoryNo)
                .isEqualTo(upperCategoryService.find(upperCategoryFindDTO).getUpperCategoryNo());
    }

    @Test
    public void lowerCategoryFindTest() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        LowerCategoryFindDTO lowerCategoryFindDTO = new LowerCategoryFindDTO(lowerCategoryNo);

        assertThat(lowerCategoryNo)
                .isEqualTo(lowerCategoryService.find(lowerCategoryFindDTO).getLowerCategoryNo());
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
    public void upperCategorySaveTest() {
        UpperCategorySaveDTO upperCategorySaveDTO = new UpperCategorySaveDTO("test");

        Long upperCategoryNo = upperCategoryService.save(upperCategorySaveDTO);

        assertThat(upperCategoryNo)
                .isEqualTo(upperCategoryRepository.findAll().get(1).getUpperCategoryNo());
    }

    @Test
    public void lowerCategorySaveTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        LowerCategorySaveDTO lowerCategorySaveDTO = new LowerCategorySaveDTO("test", upperCategoryNo);

        Long lowerCategoryNo = lowerCategoryService.save(lowerCategorySaveDTO);

        assertThat(lowerCategoryNo)
                .isEqualTo(lowerCategoryRepository.findAll().get(1).getLowerCategoryNo());
    }

    @Test
    public void upperCategoryUpdateTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        String name = "updateName";

        UpperCategoryUpdateDTO upperCategoryUpdateDTO = new UpperCategoryUpdateDTO(upperCategoryNo, name);

        upperCategoryService.update(upperCategoryUpdateDTO);

        assertThat(name).isEqualTo(upperCategoryRepository.findAll().get(0).getName());
    }

    @Test
    public void lowerCategoryUpdateTest1() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        String name = "updateName";

        LowerCategoryUpdateDTO lowerCategoryUpdateDTO =
                new LowerCategoryUpdateDTO(lowerCategoryNo, upperCategoryNo, name);

        lowerCategoryService.update(lowerCategoryUpdateDTO);

        assertThat(name).isEqualTo(lowerCategoryRepository.findAll().get(0).getName());
    }

    @Test
    public void lowerCategoryUpdateTest2() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();
        Long upperCategoryNo = upperCategoryRepository.save(
                UpperCategory.builder()
                        .name("updateTest")
                        .build())
                .getUpperCategoryNo();
        String name = lowerCategoryRepository.findAll().get(0).getName();

        LowerCategoryUpdateDTO lowerCategoryUpdateDTO =
                new LowerCategoryUpdateDTO(lowerCategoryNo, upperCategoryNo, name);

        lowerCategoryService.update(lowerCategoryUpdateDTO);

        assertThat(upperCategoryNo).isEqualTo(
                lowerCategoryRepository.findAll().get(0).getUpperCategory().getUpperCategoryNo());
    }

    @Test
    public void upperCategoryDeleteTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        UpperCategoryDeleteDTO upperCategoryDeleteDTO = new UpperCategoryDeleteDTO(upperCategoryNo);

        upperCategoryService.delete(upperCategoryDeleteDTO);

        assertThat(upperCategoryRepository.findById(upperCategoryNo)).isEmpty();
    }

    @Test
    public void lowerCategoryDeleteTest() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        LowerCategoryDeleteDTO lowerCategoryDeleteDTO = new LowerCategoryDeleteDTO(lowerCategoryNo);

        lowerCategoryService.delete(lowerCategoryDeleteDTO);

        assertThat(lowerCategoryRepository.findById(lowerCategoryNo)).isEmpty();
    }
}
