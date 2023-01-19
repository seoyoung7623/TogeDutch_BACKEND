package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Review {
    private int reviewId;
    private String emotionStatus;
    private String content;
    private Timestamp createdAt;
    private int applicationId;
    private int postId;
    private int userId;


}
