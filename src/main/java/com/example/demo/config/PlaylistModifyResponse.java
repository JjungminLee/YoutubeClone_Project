package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.demo.config.BaseResponseStatus.ModifyPlaylistSUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class PlaylistModifyResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public PlaylistModifyResponse(T result) {
        this.isSuccess = ModifyPlaylistSUCCESS.isSuccess();
        this.message = ModifyPlaylistSUCCESS.getMessage();
        this.code = ModifyPlaylistSUCCESS.getCode();
        this.result = result;
    }





    // 요청에 실패한 경우
    public PlaylistModifyResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
