package com.example.blog_example.util.enums.state;

import com.example.blog_example.util.enums.state.EnumState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LikedState implements EnumState {
    LIKED("좋아요"),
    CANCEL("취소됨");

    private final String description;

    @Override
    public String getState() { return name(); }

    @Override
    public String getDescription() { return description; }
}
