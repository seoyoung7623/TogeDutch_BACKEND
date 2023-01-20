package com.proj.togedutch.controller;

import com.proj.togedutch.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    private OAuthService oAuthService;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoLogin(@RequestParam String code){
        String accessToken = oAuthService.getKakaoAccessToken(code);
        System.out.println(accessToken);        // accessToken 출력

        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);
        System.out.println("###access_Token#### : " + accessToken);
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        // UTF-8 설정해도 한글 깨짐
        System.out.println("###email#### : " + userInfo.get("email"));
    }

}