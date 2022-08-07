package com.example.demo.src.video;

import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.video.model.GetVideoRes;
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



}
