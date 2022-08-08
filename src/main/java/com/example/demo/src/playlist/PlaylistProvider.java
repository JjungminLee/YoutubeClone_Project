package com.example.demo.src.playlist;

import com.example.demo.config.BaseException;
import com.example.demo.src.playlist.model.GetPlaylistByChannelRes;
import com.example.demo.src.playlist.model.GetPlaylistRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PlaylistProvider {
    private final PlaylistDao playlistDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public PlaylistProvider(PlaylistDao playlistDao) {
        this.playlistDao=playlistDao;
    }


    //[GET] 플레이리스트 조회 
    public List<GetPlaylistRes> getPlaylistByName(String playlistName)throws BaseException {

        try{
            List<GetPlaylistRes>playlistRes=playlistDao.getPlayListByName(playlistName);
            return playlistRes;

        }catch (Exception exception) {


            throw new BaseException(DATABASE_ERROR);

        }

    }
    
    //[GET] 채널 이름으로 플리 조회
    public List<GetPlaylistByChannelRes> getPlaylistByChannel(String channelName)throws BaseException {

        try{
            List<GetPlaylistByChannelRes>playlistByChannelRes=playlistDao.getPlaylistByChannel(channelName);
            return playlistByChannelRes;

        }catch (Exception exception) {


            throw new BaseException(DATABASE_ERROR);

        }

    }

    //[GET] 좋아요 표시한 영상 조회

    public List<GetPlaylistRes>getLikeVideo(int userID)throws BaseException{
        try{
            List<GetPlaylistRes>getPlaylistRes=playlistDao.getLikeVideo(userID);
            return getPlaylistRes;
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);

        }
    }
}
