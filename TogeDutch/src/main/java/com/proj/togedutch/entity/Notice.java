package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class Notice {
    private int notice_id;
    private String title;
    private String content;
    private Timestamp created_at;
    private Timestamp updated_at;
}
