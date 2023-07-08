package com.example.blog_example.service.category;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
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
    private final LowerCategoryRepository lowerCategoryRepository;

    @Transactional(readOnly = true)
    public List<UpperCategoryVO> findAll() {
        return upperCategoryRepository.findAll().stream()
                .map(UpperCategoryVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UpperCategoryVO find(Long upperCategoryNo) {
        return UpperCategoryVO.from(upperCategoryRepository.findById(upperCategoryNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다.")));
    }

    @Transactional
    public Long save(UpperCategorySaveDTO upperCategorySaveDTO) {
        UpperCategory upperCategory = upperCategoryRepository.save(
                UpperCategory.builder()
                        .name(upperCategorySaveDTO.getName())
                        .build());

        lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name("전체")
                        .upperCategory(upperCategory)
                        .build());

        return upperCategory.getUpperCategoryNo();
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
    public void delete(Long upperCategoryNo) {
        UpperCategory upperCategory = upperCategoryRepository.findById(upperCategoryNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리는 없습니다."));

        upperCategoryRepository.delete(upperCategory);
    }
}
