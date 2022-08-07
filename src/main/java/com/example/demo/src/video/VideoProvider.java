package com.example.demo.src.video;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.video.model.GetVideoRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class VideoProvider {

    private final VideoDao videoDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtService jwtService;

    @Autowired

    public VideoProvider(VideoDao videoDao, JwtService jwtService){
        this.videoDao=videoDao;
        this.jwtService=jwtService;
    }


    //[GET]

    //전체 영상 조회

    public List<GetVideoRes> getVideos()throws BaseException{

        try{
            List<GetVideoRes>getVideoRes=videoDao.getVideos();
            return getVideoRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    //영상 검색
    public List<GetVideoRes>getVideosByVideoTitle(String videoTitle)throws BaseException{
        try{
            List<GetVideoRes>getVideoRes=videoDao.getVideosByVideoTitle(videoTitle);
            return getVideoRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    //videoID를 통한 영상 재생
    public List<GetVideoRes>getVideosByVideoID(int videoID)throws BaseException{
        try{
            List<GetVideoRes>getVideoRes=videoDao.getVideosByVideoID(videoID);
            return getVideoRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }




}
