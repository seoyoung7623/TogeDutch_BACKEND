package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    private int post_id;
    private String title;
    private String url;
    private int delivery_tips;
    private int minimum;
    private Timestamp order_time;
    private int num_of_recruits;
    private int recruited_num;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String location;
    private int user_id;
    private int file_id;
}
