package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.UploadResponse;
import com.example.demo.config.VideoModifyResponse;
import com.example.demo.src.video.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_VIDEO_EMPTY_TITLE;

@RestController

@RequestMapping("app/video")


public class VideoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VideoProvider videoProvider;

    @Autowired
    private VideoService videoService;

    public VideoController(VideoProvider videoProvider,VideoService videoService){
        this.videoProvider=videoProvider;
        this.videoService=videoService;
    }

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
    @ResponseBody
    @PostMapping("/new")

    public UploadResponse<PostVideoRes> createVideo(@RequestBody PostVideoReq postVideoReq){
 
         //형식적 validation 검사
        if(postVideoReq.getVideoTitle()==null){
            return new UploadResponse<>(POST_VIDEO_EMPTY_TITLE);
        }
        if(postVideoReq.getUserName()==null){
            return new UploadResponse<>(POST_VIDEO_EMPTY_TITLE);
        }

        if(postVideoReq.getVideoLength()==null){
            return new UploadResponse<>(POST_VIDEO_EMPTY_TITLE);
        }

        try{
            PostVideoRes postVideoRes=videoService.createVideo(postVideoReq);
            return new UploadResponse<>(postVideoRes);
        }catch(BaseException exception){
            return new UploadResponse<>((exception.getStatus()));
        }



    }


    //[PATCH] 영상 수정 app/video/videoID/{videoID}

    @ResponseBody
    @PatchMapping("/videoID/{videoID}")
    
    //왜 PatchVideoReq를 RequestBody로 받아오면 안되는지 생각해보기 -> 결국에 ID를 PathVariable에서 가져와야하기에 새롭게
    //PatchVideoReq객체 만들어야
    public VideoModifyResponse<String> modifyVideoInfo(@PathVariable("videoID")int videoID,@RequestBody PatchVideoReq patchVideoReq){

        try{
            //ID는 pathvariable의 ID
            //PatchVideoReq patchVideoReq=new PatchVideoReq(videoID,video.getVideoTitle(),video.getChannelIntroduction(),video.getStatus());

            patchVideoReq.setVideoID(videoID); //굳이 객체 안만들고 videoID만 set해주기
            //영상 제목 변경
            videoService.modifyVideoTitle(patchVideoReq);
            //영상 소개 변경
            videoService.modifyVideoIntro(patchVideoReq);


            String result="영상 정보가 수정되었습니다.";
            return new VideoModifyResponse<>(result);
        }
        catch(BaseException exception){

            return new VideoModifyResponse<>(exception.getStatus());

        }
    }

    //[PATCH] 영상 삭제 app/video/d/videoID/{videoID}


    @ResponseBody
    @PatchMapping("/d/videoID/{videoID}")
    public VideoModifyResponse<String> deleteVideo(@PathVariable("videoID")int videoID,@RequestBody Video video){

        try{
            //ID는 pathvariable의 ID
            PatchVideoReq patchVideoReq=new PatchVideoReq(videoID,video.getVideoTitle(),video.getChannelIntroduction(),video.getStatus());


            //영상 삭제
            videoService.deleteVideo(patchVideoReq);
            String result="영상이 삭제 되었습니다.";
            return new VideoModifyResponse<>(result);
        }
        catch(BaseException exception){

            return new VideoModifyResponse<>(exception.getStatus());

        }
    }

}
