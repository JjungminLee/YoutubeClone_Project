package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {


    //[USER] TABLE


    
    //userName 형식 체크

    public static boolean isRegexUserName(String target){
        String REGEX_USER_Name = "^(?=.*[a-zA-z])(?=.*[0-9])(?!.*[^a-zA-z0-9]).{5,20}$"; // 정규식 패턴 (사용자 ID - 영문자/숫자 포함 5~20자)
        Pattern pattern = Pattern.compile(REGEX_USER_Name);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    

    
    //비밀번호 형식체크
    public static boolean isRegexPassword(String target){
        String REGEX_USER_PW = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$" ;
        //'숫자', '문자', '특수문자' 무조건 1개 이상, 비밀번호 '최소 8자에서 최대 16자'까지 허용
        // //(특수문자는 정의된 특수문자만 사용 가능)
        Pattern pattern = Pattern.compile(REGEX_USER_PW);
        Matcher matcher=pattern.matcher(target);
        return matcher.find();

    }
    
    //전화번호 형식체크

    public static boolean isRegexPhoneNum(String target){
        String regex_phone="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        Pattern pattern=Pattern.compile(regex_phone);
        Matcher matcher=pattern.matcher(target);
        return matcher.find();


    }
}

