package com.example.blog_example.service;

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
import com.example.blog_example.service.category.LowerCategoryService;
import com.example.blog_example.service.category.UpperCategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @Autowired private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(
                User.builder()
                        .name("test")
                        .blogName("test")
                        .email("test1234@test.com")
                        .password("tesffs@123")
                        .build());

        UpperCategory upperCategory =
                upperCategoryRepository.save(
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
    public void tearDown() {
        upperCategoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void test() {
        System.out.println("test");
    }

    @Test
    public void upperCategoryFindAllTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();

        Integer size = upperCategoryRepository.findAll().size();

        assertThat(size).isEqualTo(upperCategoryService.findAll(userNo).size());
    }

    @Test
    public void lowerCategoryFindAllTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();

        Integer size = lowerCategoryRepository.findAll().size();

        assertThat(size).isEqualTo(lowerCategoryService.findAll(userNo).size());
    }

    @DisplayName("상위 카테고리 검색 동작 확인")
    @Test
    public void upperCategoryFindTest1() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        assertThat(upperCategoryNo)
                .isEqualTo(upperCategoryService.find(upperCategoryNo).getUpperCategoryNo());
    }

    @DisplayName("상위 카테고리 검색으로 받은 VO에서 lowerCategoryList가 정상으로 출력되는지 확인")
    @Test
    public void findUpperCategoryTest2() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        UpperCategoryVO upperCategoryVO = upperCategoryService.find(upperCategoryNo);

        assertThat(upperCategoryVO.getLowerCategoryVOList().get(0).getLowerCategoryNo())
                .isEqualTo(lowerCategoryService.findByUpperCategory(upperCategoryNo).get(0).getLowerCategoryNo());
    }

    @Test
    public void lowerCategoryFindTest() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        assertThat(lowerCategoryNo)
                .isEqualTo(lowerCategoryService.find(lowerCategoryNo).getLowerCategoryNo());
    }

    @Test
    public void findByUpperCategoryTest() {
        UpperCategory upperCategory = upperCategoryRepository.findAll().get(0);

        lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("test1")
                        .upperCategory(upperCategory)
                        .build());

        Long upperCategoryNo = upperCategory.getUpperCategoryNo();

        List<LowerCategoryVO> lowerCategoryVOs =
                lowerCategoryService.findByUpperCategory(upperCategoryNo);

        assertThat("test").isEqualTo(lowerCategoryVOs.get(0).getName());
        assertThat("test1").isEqualTo(lowerCategoryVOs.get(1).getName());
    }

    @Test
    public void upperCategorySaveTest() {
        Long userNo = userRepository.findAll().get(0).getUserNo();

        UpperCategorySaveDTO upperCategorySaveDTO = UpperCategorySaveDTO.builder()
                .name("test1")
                .userNo(userNo)
                .build();
        Long upperCategoryNo = upperCategoryService.save(upperCategorySaveDTO);

        assertThat(upperCategoryNo)
                .isEqualTo(upperCategoryRepository.findAll().get(1).getUpperCategoryNo());
    }

    @Test
    public void lowerCategorySaveTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();

        LowerCategorySaveDTO lowerCategorySaveDTO = LowerCategorySaveDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .name("test")
                .build();
        Long lowerCategoryNo = lowerCategoryService.save(lowerCategorySaveDTO);

        assertThat(lowerCategoryNo)
                .isEqualTo(lowerCategoryRepository.findAll().get(1).getLowerCategoryNo());
    }

    @Test
    public void upperCategoryUpdateTest() {
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        String name = "updateName";

        UpperCategoryUpdateDTO upperCategoryUpdateDTO = UpperCategoryUpdateDTO.builder()
                .upperCategoryNo(upperCategoryNo)
                .name("test")
                .build();
        upperCategoryService.update(upperCategoryUpdateDTO);

        assertThat(name).isEqualTo(upperCategoryRepository.findAll().get(0).getName());
    }

    @Test
    public void lowerCategoryUpdateTest1() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();
        Long upperCategoryNo = upperCategoryRepository.findAll().get(0).getUpperCategoryNo();
        String name = "updateName";

        LowerCategoryUpdateDTO lowerCategoryUpdateDTO = LowerCategoryUpdateDTO.builder()
                .lowerCategoryNo(lowerCategoryNo)
                .upperCategoryNo(upperCategoryNo)
                .name(name)
                .build();
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

        upperCategoryService.delete(upperCategoryNo);

        assertThat(upperCategoryRepository.findById(upperCategoryNo)).isEmpty();
    }

    @Test
    public void lowerCategoryDeleteTest() {
        Long lowerCategoryNo = lowerCategoryRepository.findAll().get(0).getLowerCategoryNo();

        lowerCategoryService.delete(lowerCategoryNo);

        assertThat(lowerCategoryRepository.findById(lowerCategoryNo)).isEmpty();
    }
}
