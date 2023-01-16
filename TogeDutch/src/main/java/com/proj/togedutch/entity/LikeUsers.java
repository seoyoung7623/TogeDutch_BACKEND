package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeUsers {
    private int like_id;

    private int post_id;

    private int user_id;
}
