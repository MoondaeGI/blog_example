package com.example.blog_example.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OpenYN {
    OPEN("공개"),
    CLOSE("비공개");

    private final String name;
}
