package com.proj.togedutch.controller;

import com.proj.togedutch.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        String accessToken=oAuthService.getKakaoAccessToken(code);
        System.out.println(accessToken);        // accessToken 출력
        // accessToken으로 로그인할지 / 회원가입할지 분리해서 웅앵
        // 이메일은 필수로 받기
    }
}
