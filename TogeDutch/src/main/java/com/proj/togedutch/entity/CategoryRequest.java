package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryRequest {
    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private String category5;
    private String category6;
    private Double latitude;
    private Double longitude;
}
