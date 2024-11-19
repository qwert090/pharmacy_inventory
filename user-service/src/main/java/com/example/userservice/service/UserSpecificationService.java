package com.example.userservice.service;

import com.example.userservice.dto.UserFilterDto;
import com.example.userservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserSpecificationService {
    private static final String LIKE_PATTERN = "%%%s%%";

    public Specification<User> getSpecification(UserFilterDto filter) {
        log.debug("Build specification by filter: {}", filter);
        return Specification.where(deleteDateIsNull())
                .and(fieldIsLike(filter.getFirstName(), "firstName"))
                .and(fieldIsLike(filter.getLastName(), "lastName"))
                .and(fieldIsLike(filter.getEmail(), "email"));
    }

    private Specification<User> deleteDateIsNull() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deleteDate"));
    }

    private Specification<User> fieldIsLike(String filterValue, String fieldName) {
        if (filterValue == null || filterValue.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        } else {
            log.info("Add [{}] with value [{}] to specification", fieldName, filterValue);
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                    root.get(fieldName), String.format(LIKE_PATTERN, filterValue));
        }
    }
}
