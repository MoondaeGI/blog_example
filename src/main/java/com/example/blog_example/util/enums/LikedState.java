package com.example.blog_example.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LikedState {
    LIKED("좋아요"),
    CANCEL("취소됨");

    private final String state;
}
