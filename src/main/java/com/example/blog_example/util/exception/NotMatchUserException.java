package com.example.blog_example.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotMatchUserException extends IllegalArgumentException {
    private String message;
}
