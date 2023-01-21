package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Advertisement {
    private int adIdx;
    private String store;
    private String information;
    private String mainMenu;
    private int deliveryTips;
    private String location;
    private String request;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String status;
    private int userIdx;
    private int fileIdx;

    public Advertisement(int adIdx, String store, String information, String mainMenu, int deliveryTips, String location, String status, int userIdx, int fileIdx) {
        this.adIdx = adIdx;
        this.store = store;
        this.information = information;
        this.mainMenu = mainMenu;
        this.deliveryTips = deliveryTips;
        this.location = location;
        this.status = status;
        this.userIdx = userIdx;
        this.fileIdx = fileIdx;
    }
}
