package com.example.demo.src.playlist;

import com.example.demo.src.playlist.model.GetPlaylistByChannelRes;
import com.example.demo.src.playlist.model.GetPlaylistRes;
import com.example.demo.src.playlist.model.PatchPlaylistReq;
import com.example.demo.src.playlist.model.PostPlaylistReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PlaylistDao {

    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //[GET] playlistName으로 플리 조회

    public List<GetPlaylistRes> getPlayListByName(String playlistName){

        String getPlaylistNameQuery="select USER.userName,V.videoTitle " +
                "from Playlist_belong_Video pv " +
                "inner join Playlist P on pv.playlistID=P.ID " +
                "INNER JOIN Video V on pv.videoID=V.ID " +
                "INNER JOIN USER on USER.ID=V.uploaderID " +
                "where playlistName like ?";
        String getPlaylistNameQueryParam="%"+playlistName+"%";
        return this.jdbcTemplate.query(getPlaylistNameQuery,(rs,rowNum)->new GetPlaylistRes(
                rs.getString("userName"),
                rs.getString("videoTitle")

        ),getPlaylistNameQueryParam);

    }

    //[GET] 채널 이름으로 플레이리스트 조회

    public List<GetPlaylistByChannelRes>getPlaylistByChannel(String channelName){

        String getPlaylistByChannelQuery="SELECT playlistName " +
                "from Playlist " +
                "inner join USER U on Playlist.userID = U.ID " +
                "where U.userName like ?";
        String getPlaylistNameQueryParam="%"+channelName+"%";

        return this.jdbcTemplate.query(getPlaylistByChannelQuery,(rs,rowNum)->new GetPlaylistByChannelRes(
                        rs.getString("playlistName")
        ),getPlaylistNameQueryParam);

    }

    //[GET] 좋아요 표시한 영상 조회

    public List<GetPlaylistRes>getLikeVideo(int userID){
        String getLikeVideoQuery="SELECT U.userName," +
                " V.videoTitle " +

                "from Video_Like L " +
                "inner join Video V on L.videoID=V.ID " +
                "inner join USER U on V.uploaderID=U.ID " +
                "WHERE L.userID = ?";

        return this.jdbcTemplate.query(getLikeVideoQuery,(rs,rowNum)->new GetPlaylistRes(
                rs.getString("userName"),
                rs.getString("videoTitle")
        ),userID);
    }


    //[POST] 새로운 플레이리스트 등록

    public int createPlaylist(PostPlaylistReq postPlaylistReq){

        String createPlaylistQuery="insert into Playlist(userID, playlistName) Value(?,?)";
        Object createPlaylistParams[]={postPlaylistReq.getUserID(),postPlaylistReq.getPlaylistName()};

        this.jdbcTemplate.update(createPlaylistQuery,createPlaylistParams);

        String lastInsertIdQuery="SELECT ID FROM USER ORDER BY ID DESC LIMIT 1"; //last_insert_id()대신 사용
        //String lastInsertIdQuery = "SELECT last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다. 원래는 last_insert_id()
        int ans=this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);


        return ans;
    }

    public int modifyPlaylist(PatchPlaylistReq patchPlaylistReq){
        String modifyPlaylistQuery="UPDATE Playlist set playlistName=? where ID=?";
        Object modifyPlaylistParams[]={patchPlaylistReq.getPlaylistName(),patchPlaylistReq.getID()};
        return jdbcTemplate.update(modifyPlaylistQuery,modifyPlaylistParams);

    }

    public int deletePlaylist(PatchPlaylistReq patchPlaylistReq){
        String modifyPlaylistQuery="UPDATE Playlist set status=? where ID=?";
        Object modifyPlaylistParams[]={patchPlaylistReq.getStatus(),patchPlaylistReq.getID()};
        return jdbcTemplate.update(modifyPlaylistQuery,modifyPlaylistParams);

    }
}
