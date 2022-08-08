package com.example.demo.src.commentVideo;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.commentVideo.model.PatchCommentReq;
import com.example.demo.src.commentVideo.model.PostCommentReq;
import com.example.demo.src.commentVideo.model.PostCommentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentVideoService {

    @Autowired
    CommentVideoDao commentVideoDao;
    public CommentVideoService(CommentVideoDao commentVideoDao){this.commentVideoDao=commentVideoDao;}

    public PostCommentRes createComment(PostCommentReq postCommentReq)throws BaseException{
        try{

            int idx=commentVideoDao.createComment(postCommentReq);
            return new PostCommentRes(idx);

        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);

        }
    }

    //[PATCH] 댓글 수정
    public void modifyComment(PatchCommentReq patchCommentReq) throws BaseException{

        try{
            int res=commentVideoDao.modifyComment(patchCommentReq);
            if(res==0){
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_COMMENT); //댓글 수정 에러
            }

        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    //[PATCH] 댓글 삭제

    public void deleteComment(PatchCommentReq patchCommentReq)throws BaseException{

        try{
            int res=commentVideoDao.deleteComment(patchCommentReq);
            if(res==0){
                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_COMMENT); //댓글 수정 에러
            }

        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
