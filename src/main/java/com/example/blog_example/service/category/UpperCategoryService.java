package com.example.blog_example.service.category;

import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.dto.category.upper.UpperCategoryDeleteDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryFindDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategorySaveDTO;
import com.example.blog_example.model.dto.category.upper.UpperCategoryUpdateDTO;
import com.example.blog_example.model.vo.category.UpperCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UpperCategoryService {
    private final UpperCategoryRepository upperCategoryRepository;

    @Transactional(readOnly = true)
    public List<UpperCategoryVO> findAll() {
        return upperCategoryRepository.findAll().stream()
                .map(UpperCategoryVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UpperCategoryVO find(UpperCategoryFindDTO upperCategoryFindDTO) {
        return UpperCategoryVO.from(
                upperCategoryRepository.findById(upperCategoryFindDTO.getUpperCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다.")));
    }

    @Transactional
    public Long save(UpperCategorySaveDTO upperCategorySaveDTO) {
        return upperCategoryRepository.save(
                UpperCategory.builder()
                        .name(upperCategorySaveDTO.getName())
                        .build())
                .getUpperCategoryNo();
    }

    @Transactional
    public Long update(UpperCategoryUpdateDTO upperCategoryUpdateDTO) {
        UpperCategory upperCategory = upperCategoryRepository.findById(
                upperCategoryUpdateDTO.getUpperCategoryNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        upperCategory.update(upperCategoryUpdateDTO.getName());

        return upperCategory.getUpperCategoryNo();
    }

    @Transactional
    public void delete(UpperCategoryDeleteDTO upperCategoryDeleteDTO) {
        UpperCategory upperCategory = upperCategoryRepository.findById(
                upperCategoryDeleteDTO.getUpperCategoryNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리는 없습니다."));

        upperCategoryRepository.delete(upperCategory);
    }
}
