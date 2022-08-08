package com.example.demo.src.user;

import com.example.demo.config.*;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController // Rest API 또는 WebAPI를 개발하기 위한 어노테이션. @Controller + @ResponseBody 를 합친것.
                // @Controller      [Presentation Layer에서 Contoller를 명시하기 위해 사용]
                //  [Presentation Layer?] 클라이언트와 최초로 만나는 곳으로 데이터 입출력이 발생하는 곳
                //  Web MVC 코드에 사용되는 어노테이션. @RequestMapping 어노테이션을 해당 어노테이션 밑에서만 사용할 수 있다.
                // @ResponseBody    모든 method의 return object를 적절한 형태로 변환 후, HTTP Response Body에 담아 반환.
@RequestMapping("/app/users")
// method가 어떤 HTTP 요청을 처리할 것인가를 작성한다.
// 요청에 대해 어떤 Controller, 어떤 메소드가 처리할지를 맵핑하기 위한 어노테이션
// URL(/app/users)을 컨트롤러의 메서드와 매핑할 때 사용
/**
 * Controller란?
 * 사용자의 Request를 전달받아 요청의 처리를 담당하는 Service, Prodiver 를 호출
 */
public class UserController {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired  // 객체 생성을 스프링에서 자동으로 생성해주는 역할. 주입하려 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
    // IoC(Inversion of Control, 제어의 역전) / DI(Dependency Injection, 의존관계 주입)에 대한 공부하시면, 더 깊이 있게 Spring에 대한 공부를 하실 수 있을 겁니다!(일단은 모르고 넘어가셔도 무방합니다.)
    // IoC 간단설명,  메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미
    // DI 간단설명, 객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // ******************************************************************************

    /**
     * 회원가입 API
     * [POST] /users
     */
    // Body
    @ResponseBody
    @PostMapping("/new")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public SignUpResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        //  @RequestBody란, 클라이언트가 전송하는 HTTP Request Body(우리는 JSON으로 통신하니, 이 경우 body는 JSON)를 자바 객체로 매핑시켜주는 어노테이션

        // 유저네임이 빈값으로 들어왔는지 확인, 빈값으로 요청하면 에러메세지
        if(postUserReq.getUserName()==null){
            return new SignUpResponse<>(POST_USERS_EMPTY_USER_NAME);

        }
        //UserProvider가서 중복확인 해줄거임!

        //패스워드가 빈 값으로 들어왔는지 확인
        if(postUserReq.getPassWord()==null) {
            return new SignUpResponse<>(POST_USERS_EMPTY_USER_PWD);
        }
        //패스워드 정규표현 (대문자 주의!)

        if (!isRegexPassword(postUserReq.getPassWord())) {
            return new SignUpResponse<>(POST_USERS_INVALID_PASSWORD);
        }
        //생일이 빈값인지 확인
        if(postUserReq.getBirthDate()==null){
            return new SignUpResponse<>(POST_USERS_EMPTY_USER_BIRTHDATE);
        }
        /*
        //성별 입력했는지 확인
        if(postUserReq.getGender()==null){
            return new SignUpResponse<>(POST_USERS_EMPTY_USER_GENDER);
        }

         */

        //전화번호 정규표현
        if(!isRegexPhoneNum(postUserReq.getPhoneNumber())){
            return new SignUpResponse<>(POST_USERS_INVALID_PHONENUMBER);
        }




        
        //여기까지 형식적 validation검사




        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new SignUpResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new SignUpResponse<>((exception.getStatus()));
        }


        

    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     */
    @ResponseBody
    @PostMapping("/log-in")
    public LogInResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {

        // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!


        if(postLoginReq.getUserName()==null){
            return new LogInResponse<>(POST_USERS_EMPTY_USER_NAME);
        }

        if(postLoginReq.getPassWord()==null){
            return new LogInResponse<>(POST_USERS_EMPTY_USER_PWD);
        }


        try {


            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new LogInResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new LogInResponse<>(exception.getStatus());
        }
    }


    /**
     * 모든 회원들의  조회 API
     * [GET] /users
     *
     * 또는
     *
     * 해당 닉네임을 갖는 유저들의 정보 조회 API
     * [GET] /users? NickName=
     */
    //Query String
    @ResponseBody   // return되는 자바 객체를 JSON으로 바꿔서 HTTP body에 담는 어노테이션.
    //  JSON은 HTTP 통신 시, 데이터를 주고받을 때 많이 쓰이는 데이터 포맷.
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String userName) {
        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음
        // required=false면 특정 닉네임값이 없어도 된다는거
        try {
            if (userName == null) { // query string인 nickname이 없을 경우, 그냥 전체 유저정보를 불러온다.
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            else{
                // query string인 nickname이 있을 경우, 조건을 만족하는 유저정보들을 불러온다.
                List<GetUserRes> getUsersRes = userProvider.getUsersByUserName(userName);
                return new BaseResponse<>(getUsersRes);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**




    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/userId/{userId}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int userId) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            GetUserRes getUserRes = userProvider.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    //[GET] 채널 홈 조회 /app/users?userName={userName}/featured

    @ResponseBody
    @GetMapping("/featured")
    public BaseResponse <GetChannelRes> getChannelFeature(@RequestParam String channelName){
        try{
            GetChannelRes getChannelRes=userProvider.getChannelFeature(channelName);
            return new BaseResponse<>(getChannelRes);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    //[GET] 채널 영상 조회

    @ResponseBody
    @GetMapping("/videos")

    public BaseResponse <List<GetChannelVideoRes>> getChannelVideos(@RequestParam String channelName){
        try{
            List<GetChannelVideoRes> getChannelVideoRes=userProvider.getChannelVideos(channelName);
            return new BaseResponse<>(getChannelVideoRes);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //[GET] 채널 정보 조회

    @ResponseBody
    @GetMapping("/about")

    public BaseResponse<GetChannelAbout> getChannelAbout(@RequestParam String channelName){
        try{
            GetChannelAbout getChannelAbout=userProvider.getChannelAbout(channelName);
            return new BaseResponse<>(getChannelAbout);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    
    

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     */
    @ResponseBody
    @PatchMapping("userID/{userID}")
    public UserModifyResponse<String> modifyUserName(@PathVariable("userID") int ID, @RequestBody User user) {
        try {

            logger.warn("아이디는"+String.valueOf(ID));
/**
  *********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
  **************************************************************************
 */
            PatchUserReq patchUserReq = new PatchUserReq(ID, user.getUserName(),user.getPassWord(),user.getStatus());
            //이름 변경
            userService.modifyUserName(patchUserReq);
            //패스워드 변경
            userService.modifyUserPassword(patchUserReq);





            String result = "회원정보가 수정되었습니다.";
            return new UserModifyResponse<>(result);
        } catch (BaseException exception) {
            return new UserModifyResponse<>((exception.getStatus()));
        }
    }
    
    
    //유저정보 삭제

    @ResponseBody
    @PatchMapping("/d/userID/{userID}")
    public UserModifyResponse<String> modifyUserStatus(@PathVariable("userID") int ID, @RequestBody User user) {
        try {
                PatchUserReq patchUserReq = new PatchUserReq(ID, user.getUserName(),user.getPassWord(),user.getStatus());
                userService.modifyUserStatus(patchUserReq);





            String result = "회원탈퇴가 완료되었습니다.";
            return new UserModifyResponse<>(result);
        } catch (BaseException exception) {
            return new UserModifyResponse<>((exception.getStatus()));
        }
    }
}
