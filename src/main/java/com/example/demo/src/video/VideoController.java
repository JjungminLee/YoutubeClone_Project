package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.video.model.GetVideoRes;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("app/video")


public class VideoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VideoProvider videoProvider;

    @Autowired
    private VideoService videoService;

    //[GET] 전체 영상 조회 app/video
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetVideoRes>> getVideos(){

        try{

            List<GetVideoRes>getVideoRes=videoProvider.getVideos();
            return new BaseResponse<>(getVideoRes); //예외가 발생하지 않으면 정보를 잘 담아주고

        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus())); //예외가 발생하면 예외코드 실행시킨다.
        }
    }
    
    // [GET] videoID로 영상 재생
    @ResponseBody
    @GetMapping("/videoID/{videoID}")

    public BaseResponse<List<GetVideoRes>> getVideosByVideoID(@PathVariable("videoID") int videoID){

        try{
            List<GetVideoRes>getVideoRes=videoProvider.getVideosByVideoID(videoID);
            return new BaseResponse<>(getVideoRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus())); //예외가 발생하면 예외코드 실행시킨다.
        }
    }


    //[GET]  영상 검색 /app/video/result?videoTitle={videoTitle}
    @ResponseBody
    @GetMapping("/result")
    public BaseResponse<List<GetVideoRes>> getVideosByVideoTitle(@RequestParam String videoTitle){

        try{
            List<GetVideoRes>getVideoRes=videoProvider.getVideosByVideoTitle(videoTitle);
            return new BaseResponse<>(getVideoRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus())); //예외가 발생하면 예외코드 실행시킨다.
        }
    }

    




    //[POST] 영상 업로드 app/video/new


    //[PATCH] 영상 수정 app/video/videoID/{videoID}

    //[PATCH] 영상 삭제 app/video/d/videoID/{videoID}

}
