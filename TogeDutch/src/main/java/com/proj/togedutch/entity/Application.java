package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    private int application_id;
    private String status;
    private int post_id;
    private int user_id;
    private int chatRoom_id;
}