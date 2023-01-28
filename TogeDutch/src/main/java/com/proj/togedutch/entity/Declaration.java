package com.proj.togedutch.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Declaration {
    int declarationIdx;
    String content;
    Timestamp created_at;
    String status;
    int chatRoomIdx;
}
