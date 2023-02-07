package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryRequest {
    private String category;
    private Double latitude;
    private Double longitude;
}
