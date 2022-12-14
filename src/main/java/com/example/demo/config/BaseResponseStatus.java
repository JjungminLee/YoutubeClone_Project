package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SignUPSUCCESS(true,1001,"회원가입에 성공하였습니다"),
    LogInSUCCESS(true,1002,"로그인에 성공하였습니다."),
    ModifyUserSUCCESS(true,1003,"유저 정보 수정에 성공하였습니다."),

    ModifyDeleteUserSUCCESS(true,1008,"유저 탈퇴에 성공하였습니다."),





    //video
    UploadSUCCESS(true,1004,"업로드에 성공하였습니다."),

    ModifyVideoSUCCESS(true,1005,"영상 수정에 성공하였습니다."),

    CreatePlaylistSUCCESS(true,1006,"재생목록 생성에 성공하였습니다."),

    ModifyPlaylistSUCCESS(true,1007,"재생목록 수정에 성공하였습니다."),

    //comment
    CommentSUCCESS(true,1009,"댓글 작성에 성공하였습니다."),

    ModifyCommentSUCCESS(true,1010,"댓글 수정에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */

    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    //이 아이디값은 뭐죠...
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_USER_NAME(false,2011,"유저 이름을 확인해주세요."),
    POST_USERS_EMPTY_USER_PWD(false,2012,"패스워드를 확인해주세요."),

    POST_USERS_EMPTY_USER_COUNTRY(false,2013,"국가명을 입력해주세요"),

    POST_USERS_EMPTY_USER_BIRTHDATE(false,2014,"생일을 입력해주세요"),

    POST_USERS_EMPTY_USER_GENDER(false,2015,"성별을 입력해주세요"),

    POST_USERS_EMPTY_EMAIL(false, 2016, "이메일을 입력해주세요."),

    POST_USERS_EMPTY_USER_PHONENUMBER(false,2017,"전화번호를 확인해주세요."),

    POST_USERS_INVALID_PASSWORD(false,2019,"패스워드 형식을 확인해주세요."),
    POST_USERS_INVALID_USERNAME(false,2020,"이름 형식을 확인해주세요."),
    POST_USERS_INVALID_PHONENUMBER(false,2021,"전화번호 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2022,"중복된 이메일입니다."),
    POST_USER_EXISTS_USERNAME(false,2023,"중복된 userName입니다."),

    POST_USER_INVALID_STATUS(false,2024,"존재하지 않는 유저입니다."),

    //video

    //[POST]Video
    POST_VIDEO_EMPTY_TITLE(false,2025,"영상 제목을 확인해주세요."),
    POST_VIDEO_EMPTY_UPLOADERNAME(false,2026,"게시자 이름을 확인하세요."),
    POST_VIDEO_EMPTY_LENGTH(false,2027,"영상의 길이를 확인해주세요."),

    //[POST] Playlist

    POST_PLAYLIST_EMPTY_NAME(false,2028,"재생목록 이름을 확인해주세요."),
    POST_PLAYLIST_EMPTY_USER_ID(false,2029,"사용자 ID를 홖인해주세요."),

    //[POST] Comment

    POST_COMMENT_EMPTY(false,2030,"댓글을 확인해주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),
    MODIFY_FAIL_USERPWD(false,4015,"유저 비밀번호 수정 실패"),

    MODIFY_FAIL_DELETE_USER(false,4018,"유저 탈퇴 실패"),


    MODIFY_FAIL_VIDEOTITLE(false,4016,"영상 제목 수정 실패"),

    MODIFY_FAIL_PLAYLISTNAME(false,4017,"재생목록 수정 실패"),

    MODIFY_FAIL_COMMENT(false,4018,"댓글 수정 실패"),




    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
