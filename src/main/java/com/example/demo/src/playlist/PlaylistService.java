package com.example.demo.src.playlist;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.playlist.model.PatchPlaylistReq;
import com.example.demo.src.playlist.model.PostPlaylistReq;
import com.example.demo.src.playlist.model.PostPlaylistRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    private final PlaylistDao playlistDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public PlaylistService(PlaylistDao playlistDao) {
        this.playlistDao=playlistDao;
    }


    public PostPlaylistRes createPlaylist(PostPlaylistReq postPlaylistReq) throws BaseException {
        try{
            int playlistIdx=playlistDao.createPlaylist(postPlaylistReq);
            return  new PostPlaylistRes(playlistIdx);
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void modifyPlaylist(PatchPlaylistReq patchPlaylistReq)throws BaseException{
        try{
            int res=playlistDao.modifyPlaylist(patchPlaylistReq);
            if(res==0){
                //실패

                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_VIDEOTITLE);

            }
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }


    public void deletePlaylist(PatchPlaylistReq patchPlaylistReq)throws BaseException{
        try{
            int res=playlistDao.deletePlaylist(patchPlaylistReq);
            if(res==0){
                //실패

                throw new BaseException(BaseResponseStatus.MODIFY_FAIL_VIDEOTITLE);

            }
        }catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
