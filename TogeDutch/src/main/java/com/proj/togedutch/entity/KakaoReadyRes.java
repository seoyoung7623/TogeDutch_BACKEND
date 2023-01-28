package com.proj.togedutch.entity;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoReadyRes {
    private String tid;
    private String next_redirect_pc_url;
    private String next_redirect_mobile_url;
    private String partner_order_id;
    private Date created_at;
}
