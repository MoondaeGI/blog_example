package com.example.blog_example.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
    OK(200, "정상적으로 요청이 작동되었습니다."),
    CREATED(201, "요청에 의해 리소스가 성공적으로 생성되었습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다"),
    FORBIDDEN(403, "해당 요청에 접근 할 수 있는 권한이 없습니다."),
    NOT_FOUND(404, "해당 요청을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 에러 발생");

    private final Integer code;
    private final String description;
}
