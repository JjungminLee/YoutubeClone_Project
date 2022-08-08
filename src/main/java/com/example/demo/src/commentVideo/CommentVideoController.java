package com.example.demo.src.commentVideo;


import com.example.demo.config.*;
import com.example.demo.src.commentVideo.model.GetCommentRes;
import com.example.demo.src.commentVideo.model.PatchCommentReq;
import com.example.demo.src.commentVideo.model.PostCommentReq;
import com.example.demo.src.commentVideo.model.PostCommentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app/commentVideo")
public class CommentVideoController {

    @Autowired
    CommentVideoService commentVideoService;

    @Autowired
    CommentVideoProvider commentVideoProvider;

    public CommentVideoController(CommentVideoService commentVideoService,CommentVideoProvider commentVideoProvider){
        this.commentVideoProvider=commentVideoProvider;
        this.commentVideoService=commentVideoService;
    }
    
    //[GET] 한 영상에 달린 댓글들 조회하기

    @ResponseBody
    @GetMapping("/videoID/{videoID}")
    public BaseResponse<List<GetCommentRes>> getVideoComments(@PathVariable("videoID") int videoID){

        try{
            List<GetCommentRes>getCommentRes=commentVideoProvider.getVideoComments(videoID);
            return new BaseResponse<>(getCommentRes);
        }catch(BaseException exception){ //provider에서 던진 BaseException 받음

            return new BaseResponse<>((exception.getStatus())); //예외가 발생하면 예외코드 실행시킨다.

        }

    }

    //[POST]댓글 작성하기
    @ResponseBody
    @PostMapping("/new")

    public CreateCommentResponse<PostCommentRes>createComment(@RequestBody PostCommentReq postCommentReq){
        try{
            PostCommentRes postCommentRes=commentVideoService.createComment(postCommentReq);
            return new CreateCommentResponse<>(postCommentRes);

        }catch(BaseException exception){
            //CommentVideoService 의 createComment에서 던져준 에러를 받음

            return new CreateCommentResponse<>(exception.getStatus());

        }
    }

    //[PATCH] 댓글 수정하기
    @ResponseBody
    @PatchMapping("/commentID/{commentID}")

    public CommentModifyResponse<String>modifyComment(@PathVariable("commentID") int commentID, @RequestBody PatchCommentReq patchCommentReq){

        try{
            patchCommentReq.setID(commentID);

            commentVideoService.modifyComment(patchCommentReq);

            String res="댓글 수정에 성공하였습니다.";

            return new CommentModifyResponse<>(res);
        }catch(BaseException exception){

            return new CommentModifyResponse<>(BaseResponseStatus.MODIFY_FAIL_COMMENT);

        }
    }

    //[PATCH] 댓글 삭제하기

    @ResponseBody
    @PatchMapping("/d/commentID/{commentID}")

    public CommentModifyResponse<String>deleteComment(@PathVariable("commentID") int commentID, @RequestBody PatchCommentReq patchCommentReq){

        try{
            patchCommentReq.setID(commentID);

            commentVideoService.deleteComment(patchCommentReq);

            String res="댓글 삭제에 성공하였습니다.";

            return new CommentModifyResponse<>(res);
        }catch(BaseException exception){

            return new CommentModifyResponse<>(BaseResponseStatus.MODIFY_FAIL_COMMENT);

        }
    }
}
