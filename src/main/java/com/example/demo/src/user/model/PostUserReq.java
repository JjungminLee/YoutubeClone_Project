package com.example.demo.src.user.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.Optional;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(email, password, nickname, profileImage)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.


public class PostUserReq {


    private String userName;//auto
    private String passWord;//입력해줘야함
    private String country;//default korea

    private String birthDate; //입력해줘야함
    private String channelIntroduction; //default 채널소개 
    private String userProfileImg; //default 프로필 이미지
    private String bannerImg; //null되는 지 실험  -> NULL 들어감! 동적쿼리에 안써도됨!
    private Integer gender; //입력해줘야함.
    private String phoneNumber;//입력해줘야함
    private String createdAt;//default time stamp
    private String updatedAt;//default time stamp
    private String status;//default a




}
