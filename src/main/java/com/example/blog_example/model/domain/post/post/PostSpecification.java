package com.example.blog_example.model.domain.post.post;

import com.example.blog_example.util.enums.state.OpenState;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostSpecification {
    public static Specification<Post> search(Map<String, Object> searchKey) {
        return (Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (searchKey == null) return null;

            List<Predicate> predicates = new ArrayList<>();
            for (String key : searchKey.keySet()) {
                predicates.add(criteriaBuilder.like(root.get(key), "%" + searchKey.get(key) + "%"));
            }
            predicates.add(criteriaBuilder.equal(root.get("openState"), OpenState.OPEN));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Post> findOpenState() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("openState"), OpenState.OPEN);
    }
}
