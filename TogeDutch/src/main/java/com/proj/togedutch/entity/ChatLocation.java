package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ChatLocation {
    private int chatLocationIdx;
    private int chatRoomId;
    private int userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Timestamp createdAt;
}
