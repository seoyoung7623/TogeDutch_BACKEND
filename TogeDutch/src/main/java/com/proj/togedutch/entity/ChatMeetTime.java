package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ChatMeetTime {
    private int chatMeetTimeId;
    private int chatRoomId;
    private int userId;
    private Timestamp meetTime;
}
