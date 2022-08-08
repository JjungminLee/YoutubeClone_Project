package com.example.demo.src.playlist;

import com.example.demo.config.*;
import com.example.demo.src.playlist.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/playlist")
public class PlaylistController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PlaylistProvider playlistProvider;

    @Autowired
    private final PlaylistService playlistService;


    public PlaylistController(PlaylistProvider playlistProvider,PlaylistService playlistService){
        this.playlistProvider=playlistProvider;
        this.playlistService=playlistService;
    }

    //[GET] playlistName으로 플레이리스트들 조회
    @ResponseBody
    @GetMapping("")

    public BaseResponse<List<GetPlaylistRes>> getPlaylistByName(@RequestParam String playlistName){
        try{
            List<GetPlaylistRes> getPlaylistRes =playlistProvider.getPlaylistByName(playlistName);
            return new BaseResponse<>(getPlaylistRes);

        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //[GET] 채널 이름으로 플레이리스트 조회

    @ResponseBody
    @GetMapping("/channel")
    public BaseResponse<List<GetPlaylistByChannelRes>> getPlaylistByChannel(@RequestParam String channelName){
        try{
            List<GetPlaylistByChannelRes> getPlaylistByChannelRes =playlistProvider.getPlaylistByChannel(channelName);
            return new BaseResponse<>(getPlaylistByChannelRes);

        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/like/{userID}")
    public BaseResponse<List<GetPlaylistRes>>getLikeVideo(@PathVariable("userID") int userID){
        try{
            List<GetPlaylistRes>getplaylistRes=playlistProvider.getLikeVideo(userID);
            return new BaseResponse<>(getplaylistRes);

        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    //[POST] 재생목록 등록

    @ResponseBody
    @PostMapping("/new")
    public CreatePlaylistResponse<PostPlaylistRes>createPlaylist(@RequestBody PostPlaylistReq postPlaylistReq){
        //형식적 validation 검사

        if(postPlaylistReq.getPlaylistName()==null){
            return new CreatePlaylistResponse<>(BaseResponseStatus.POST_PLAYLIST_EMPTY_NAME);

        }

        if(postPlaylistReq.getUserID()==null){
            return new CreatePlaylistResponse<>(BaseResponseStatus.POST_PLAYLIST_EMPTY_USER_ID);
        }

        try{
            PostPlaylistRes postPlaylistRes=playlistService.createPlaylist(postPlaylistReq);
            return new CreatePlaylistResponse<PostPlaylistRes>(postPlaylistRes); //괄호안에는 result에 해당하는게 들어감
            // 꺽쇠는 타입지정 PostPlaylistRes 타입의 객체가 괄호에 들어가서 result와 연결
        }catch (BaseException exception) {
            return new CreatePlaylistResponse<PostPlaylistRes>((exception.getStatus()));
        }
    }
    
    
    //[PATCH] 재생목록 수정
    @ResponseBody
    @PatchMapping("/{playlistID}")

    public PlaylistModifyResponse<String> modifyPlaylist(@PathVariable("playlistID") int playlistID, @RequestBody PatchPlaylistReq patchPlaylistReq){

        try{

            patchPlaylistReq.setID(playlistID);
            playlistService.modifyPlaylist(patchPlaylistReq);
            String res="재생목록이 수정되었습니다.";
            return new PlaylistModifyResponse<>(res);

        }catch(BaseException exception){
            return new PlaylistModifyResponse<>(exception.getStatus());

        }

    }

    
    
    //[PATCH] 재생목록 삭제
    @ResponseBody
    @PatchMapping("/d/{playlistID}")
    public PlaylistModifyResponse<String> deletePlaylist(@PathVariable("playlistID") int playlistID, @RequestBody PatchPlaylistReq patchPlaylistReq) {

        try {

            patchPlaylistReq.setID(playlistID);
            playlistService.deletePlaylist(patchPlaylistReq);
            String res = "재생목록이 삭제되었습니다.";
            return new PlaylistModifyResponse<>(res);

        } catch (BaseException exception) {
            return new PlaylistModifyResponse<>(exception.getStatus());

        }
    }








    }
