package com.example.demo.src.playlist.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
@NoArgsConstructor

public class PatchPlaylistReq {

    private Integer ID;
    private String playlistName;
    private String status;
}
