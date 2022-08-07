package com.example.demo.src.video;

import com.example.demo.src.video.model.GetVideoRes;
import com.example.demo.src.video.model.PatchVideoReq;
import com.example.demo.src.video.model.PostVideoReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class VideoDao {

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //[GET] Video 테이블에 존재하는 전체 영상 조회

     public List<GetVideoRes> getVideos(){
        String getVideosQuery="select * from Video INNER JOIN USER ON Video.uploaderID=USER.ID";
        return this.jdbcTemplate.query(getVideosQuery,
                (rs,row)->new GetVideoRes(

                        rs.getInt("ID"),
                        rs.getString("videoTitle"),
                        rs.getString("userName"),
                        rs.getString("videoIntroduction")
                )
        );


     }

     //[GET] 영상 검색 DAO

     public List<GetVideoRes>getVideosByVideoTitle(String videoTitle){
        String getVideosByVideoTitleQuery="select * from Video INNER JOIN USER ON Video.uploaderID=USER.ID" +
                " where videoTitle like ?";

        String getVideosByVideoTitleParams="%"+videoTitle+"%";

        return this.jdbcTemplate.query(getVideosByVideoTitleQuery,
                (rs,rowNum)->new GetVideoRes(
                        rs.getInt("ID"),
                        rs.getString("videoTitle"),
                        rs.getString("userName"),
                        rs.getString("videoIntroduction")),
                getVideosByVideoTitleParams
                );
     }

     //[GET] videoID로 영상 재생 DAO

    public List<GetVideoRes>getVideosByVideoID(int videoID){

        String getVideosByVideoTitleQuery="select * from Video INNER JOIN USER ON Video.uploaderID=USER.ID" +
                " where Video.ID =?";

        int getVideosByVideoTitleParams=videoID;

        return this.jdbcTemplate.query(getVideosByVideoTitleQuery,
                (rs,rowNum)->new GetVideoRes(
                        rs.getInt("ID"),
                        rs.getString("videoTitle"),
                        rs.getString("userName"),
                        rs.getString("videoIntroduction")),
                getVideosByVideoTitleParams
        );
    }

    //[POST] 영상 등록

    public int createVideo(PostVideoReq postVideoReq){

        //userName을 통해 게시자의 id를 찾아내는 쿼리 작성
        int uploaderID;
        String findUploaderIDQuery="SELECT USER.ID FROM USER INNER JOIN Video on Video.uploaderID=USER.ID where userName=?";
        String findUploaderIDQueryParam=postVideoReq.getUserName();
        uploaderID=this.jdbcTemplate.queryForObject(findUploaderIDQuery,int.class,findUploaderIDQueryParam);


        String createVideoQuery="insert into Video(videoTitle,uploaderID,videoLength) VALUES(?,?,?)";
        Object[]createVideoParams=new Object[]{postVideoReq.getVideoTitle(),uploaderID,postVideoReq.getVideoLength()};

        this.jdbcTemplate.update(createVideoQuery,createVideoParams);

        String lastInsertIdQuery="SELECT ID FROM Video ORDER BY ID DESC LIMIT 1";
        int ans=this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
        return ans;
    }

    //[PATCH] 영상 이름 수정

    public int modifyVideoTitle(PatchVideoReq patchVideoReq){

        //ID는 pathVariable의 ID
        String modifyVideoTitleQuery="update Video set videoTitle=? where ID=?";
        Object[] modifyVideoTitleParams=new Object[]{patchVideoReq.getVideoTitle(),patchVideoReq.getVideoID()};

        return this.jdbcTemplate.update(modifyVideoTitleQuery,modifyVideoTitleParams);


    }

    //[PATCH] 영상 소개글 수정

    public int modifyVideoIntro(PatchVideoReq patchVideoReq){
        String modifyVideoIntroQuery="update Video set videoIntroduction=? where ID=?";
        Object[]modifyVideoIntroParams=new Object[]{patchVideoReq.getChannelIntroduction(),patchVideoReq.getVideoID()};

        return this.jdbcTemplate.update(modifyVideoIntroQuery,modifyVideoIntroParams);
    }

    //[PATCH] 영상 삭제

    public int deleteVideo(PatchVideoReq patchVideoReq){
        String deleteVideoQuery="update Video set status=? where ID=?";
        Object[]deleteVideoIntoParams=new Object[]{patchVideoReq.getStatus(),patchVideoReq.getVideoID()};

        return this.jdbcTemplate.update(deleteVideoQuery,deleteVideoIntoParams);


    }



}
