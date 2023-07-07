package com.example.blog_example.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "유저");

    private final String value;
    private final String name;
}
