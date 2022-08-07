package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.video.model.PatchVideoReq;
import com.example.demo.src.video.model.PostVideoReq;
import com.example.demo.src.video.model.PostVideoRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VideoService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final VideoDao videoDao;
    private final VideoProvider videoProvider;
    private final JwtService jwtService;

    @Autowired

    public VideoService(VideoDao videoDao,VideoProvider videoProvider,JwtService jwtService){
        this.videoDao=videoDao;
        this.videoProvider=videoProvider;
        this.jwtService=jwtService;
    }

    //[POST] 영상 업로드
    public PostVideoRes createVideo(PostVideoReq postVideoReq) throws BaseException{

        //영상 내용 중복확인

        int videoID=videoDao.createVideo(postVideoReq);

        try{
            return new PostVideoRes(videoID);
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    //[PATCH] 영상 이름 수정

    public void modifyVideoTitle(PatchVideoReq patchVideoReq)throws BaseException{

        try{
            int res=videoDao.modifyVideoTitle(patchVideoReq);
            if(res==0){
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_VIDEOTITLE);
            }

        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    
    //[PATCH] 영상 소개글 수정

    public void modifyVideoIntro(PatchVideoReq patchVideoReq)throws BaseException{
        try{
            int res=videoDao.modifyVideoIntro(patchVideoReq);
            if(res==0){
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_VIDEOTITLE);
            }

        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // [PATCH] 영상 삭제

    public void deleteVideo(PatchVideoReq patchVideoReq)throws BaseException{

        try{
            int res=videoDao.deleteVideo(patchVideoReq);
            if(res==0){
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_VIDEOTITLE);
            }

        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    

}
