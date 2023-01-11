package com.proj.togedutch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileProvider {
    private int fileIdx;
    private String originalName; // 원래 파일 이름
    private String savedName; // 파일명 중복 방지를 위해 java.util.UUID 활용한 고유 식별자
    private String savedPath; // 저장 경로
}
