package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement {
    private int adIdx;
    private String store;
    private String information;
    private String mainMenu;
    private int deliveryTips;
    private Double longitude;
    private Double latitude;
    private String request;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String status;
    private int userIdx;
    private String image;
    private String tid;

    public Advertisement(int adIdx, String store, String information, String mainMenu, int deliveryTips, String status, int userIdx, String tid) {
        this.adIdx = adIdx;
        this.store = store;
        this.information = information;
        this.mainMenu = mainMenu;
        this.deliveryTips = deliveryTips;
        this.status = status;
        this.userIdx = userIdx;
        this.tid = tid;
    }
}
