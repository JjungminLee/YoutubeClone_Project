package com.example.demo.src.commentVideo;


import com.example.demo.src.commentVideo.model.GetCommentRes;
import com.example.demo.src.commentVideo.model.PatchCommentReq;
import com.example.demo.src.commentVideo.model.PostCommentReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommentVideoDao {

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCommentRes> getVideoComments(int videoID){

        String getVideoCommentsQuery="SELECT " +
                "USER.userName," +
                "    text," +
                "    concat(cast(datediff(now(),CommentVideo.created)as char),'일 전') as dates," +
                "    (select count(VCL.videoCommentID=CommentVideo.ID) from VideoComment_Like VCL where VCL.videoCommentID=CommentVideo.ID) as likes " +
                "from CommentVideo " +
                "inner join Video V on CommentVideo.videoID = V.ID " +
                "inner join USER on CommentVideo.userID = USER.ID " +
                "where V.ID=? AND CommentVideo.depth=0";

        return this.jdbcTemplate.query(getVideoCommentsQuery,(rs,rowNum)->
                new GetCommentRes(
                    rs.getString("userName"),
                    rs.getString("text"),
                    rs.getString("dates"),

                        rs.getInt("likes")

        ),videoID);
    }

    public int createComment(PostCommentReq postCommentReq){

        String createCommentQuery="INSERT into CommentVideo(userID,videoID,text,recommentGroup) VALUE(?,?,?,?) ";
        Object[] createCommentParams={postCommentReq.getUserID(),postCommentReq.getVideoID(),postCommentReq.getText(),postCommentReq.getRecommentGroup()};
        this.jdbcTemplate.update(createCommentQuery,createCommentParams);
        String lastInsertIdQuery="SELECT ID FROM USER ORDER BY ID DESC LIMIT 1"; //last_insert_id()대신 사용
        //String lastInsertIdQuery = "SELECT last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다. 원래는 last_insert_id()
        int ans=this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);


        return ans;
    }

    public int modifyComment(PatchCommentReq patchCommentReq){

        String modifyCommentQuery="Update CommentVideo set text=? where ID=?";
        Object[] modifyCommentParams={patchCommentReq.getText(),patchCommentReq.getID()};
        return  this.jdbcTemplate.update(modifyCommentQuery,modifyCommentParams);

    }

    public int deleteComment(PatchCommentReq patchCommentReq){

        String deleteCommentQuery="Update CommentVideo set status=? where ID=?";
        Object[] deleteCommentParams={patchCommentReq.getStatus(),patchCommentReq.getID()};
        return this.jdbcTemplate.update(deleteCommentQuery,deleteCommentParams);

    }
}

