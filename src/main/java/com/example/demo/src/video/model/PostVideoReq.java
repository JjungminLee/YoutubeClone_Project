package com.example.demo.src.video.model;

import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(email, password, nickname, profileImage)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.


public class PostVideoReq {

    private String videoTitle; //null안됨
    private String userName; //null 안됨
    private String videoIntroduction; //null가능
    private String videoThumbnail; //null가능
    private Time videoLength; //null 안됨
    private int HashTagID; //null 가능
    private int shortsID; //null가능
    private Timestamp createdAt; //default설정함
    private Timestamp updatedAt; //default 설정함
    private String status;//default설정함


}
