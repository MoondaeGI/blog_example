package com.example.blog_example.util.enums.state;

import com.example.blog_example.util.enums.state.EnumState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OpenState implements EnumState {
    OPEN("공개"),
    CLOSE("비공개");

    private final String description;

    @Override
    public String getState() { return name(); }

    @Override
    public String getDescription() { return description; }
}
