package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.OAuthService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OAuthService oAuthService;

    // 카카오 로그인
    @PostMapping("/kakao")
    public BaseResponse<User> kakaoLogin(@RequestParam String email) throws Exception {
        try{
            /*
            1. 카카오 계정에 등록된 이메일이 DB 회원 이메일과 동일 : 카카오 로그인 성공
            -> return User 객체

            2. 카카오 로그인 실패 : 회원 가입 진행 필요
            -> return null
             */
            User newUser = oAuthService.kakaoLogin(email);
            return new BaseResponse<>(newUser);
        } catch (BaseException e) {
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }

}