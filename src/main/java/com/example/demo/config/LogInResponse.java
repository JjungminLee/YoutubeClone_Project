package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.demo.config.BaseResponseStatus.LogInSUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class LogInResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public LogInResponse(T result) {
        this.isSuccess = LogInSUCCESS.isSuccess();
        this.message = LogInSUCCESS.getMessage();
        this.code = LogInSUCCESS.getCode();
        this.result = result;
    }




    // 요청에 실패한 경우
    public LogInResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
