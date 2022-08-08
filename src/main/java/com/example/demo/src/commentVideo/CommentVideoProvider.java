package com.example.demo.src.commentVideo;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.commentVideo.model.GetCommentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentVideoProvider {

    @Autowired

    public CommentVideoDao commentVideoDao;

    public CommentVideoProvider(CommentVideoDao commentVideoDao){this.commentVideoDao=commentVideoDao;}

    public List<GetCommentRes> getVideoComments(int videoID) throws BaseException {
        try{
            List<GetCommentRes> getCommentRes=commentVideoDao.getVideoComments(videoID);
            return  getCommentRes;

        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
