package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeUsers {
    private int likeIdx;

    private int postIdx;

    // 공고 업로드 한 유저의 id
    private int Post_User_userIdx;

    // 공고를 관심 목록에 담은 유저의 id
    private int Like_userIdx;
}
