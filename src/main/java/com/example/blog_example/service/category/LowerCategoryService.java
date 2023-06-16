package com.example.blog_example.service.category;

import com.example.blog_example.model.domain.category.lower.LowerCategory;
import com.example.blog_example.model.domain.category.lower.LowerCategoryRepository;
import com.example.blog_example.model.domain.category.upper.UpperCategory;
import com.example.blog_example.model.domain.category.upper.UpperCategoryRepository;
import com.example.blog_example.model.dto.category.lower.*;
import com.example.blog_example.model.vo.category.LowerCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LowerCategoryService {
    private final LowerCategoryRepository lowerCategoryRepository;
    private final UpperCategoryRepository upperCategoryRepository;

    @Transactional(readOnly = true)
    public List<LowerCategoryVO> findAll() {
        return lowerCategoryRepository.findAll().stream()
                .map(LowerCategoryVO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LowerCategoryVO find(LowerCategoryFindDTO lowerCategoryFindDTO) {
        return LowerCategoryVO.from(
                lowerCategoryRepository.findById(lowerCategoryFindDTO.getLowerCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다.")));
    }

    @Transactional(readOnly = true)
    public List<LowerCategoryVO> findByUpperCategory(LowerCategoryFindByUpperDTO lowerCategoryFindByUpperDTO) {
        UpperCategory upperCategory =
                upperCategoryRepository.findById(lowerCategoryFindByUpperDTO.getUpperCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return lowerCategoryRepository.findByUpperCategory(upperCategory).stream()
                .map(LowerCategoryVO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(LowerCategorySaveDTO lowerCategorySaveDTO) {
        UpperCategory upperCategory =
                upperCategoryRepository.findById(lowerCategorySaveDTO.getUpperCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        return lowerCategoryRepository.save(
                LowerCategory.builder()
                        .name(lowerCategorySaveDTO.getName())
                        .upperCategory(upperCategory)
                        .build())
                .getLowerCategoryNo();
    }

    @Transactional
    public Long update(LowerCategoryUpdateDTO lowerCategoryUpdateDTO) {
        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(lowerCategoryUpdateDTO.getLowerCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        UpperCategory upperCategory =
                upperCategoryRepository.findById(lowerCategoryUpdateDTO.getUpperCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        lowerCategory.update(lowerCategoryUpdateDTO.getName(), upperCategory);

        return lowerCategoryUpdateDTO.getLowerCategoryNo();
    }

    @Transactional
    public void delete(LowerCategoryDeleteDTO lowerCategoryDeleteDTO) {
        LowerCategory lowerCategory =
                lowerCategoryRepository.findById(
                        lowerCategoryDeleteDTO.getLowerCategoryNo())
                        .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));

        lowerCategoryRepository.delete(lowerCategory);
    }
}
