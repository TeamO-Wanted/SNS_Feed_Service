package com.wanted.sns_feed_service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;

    public CommonResponse(String message, Integer status) {
        this.message = message;
        this.statusCode = status;
        this.data = null;
    }
}
