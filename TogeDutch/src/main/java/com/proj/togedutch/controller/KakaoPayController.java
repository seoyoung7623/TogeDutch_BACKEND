package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Advertisement;
import com.proj.togedutch.entity.KakaoApproveRes;
import com.proj.togedutch.entity.KakaoReadyRes;
import com.proj.togedutch.service.AdService;
import com.proj.togedutch.service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("payment")
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;
    private final AdService adService;
    private Advertisement ad;
    private int userIdx;
    private String fileUrl;

    KakaoPayController(KakaoPayService kakaoPayService, AdService adService){
        this.kakaoPayService = kakaoPayService;
        this.adService = adService;
    }

    @GetMapping("")
    public void kakaoPayGet(){
    }

    @GetMapping("/ready")
    public KakaoReadyRes kakaoPayReady(Advertisement ad, int userIdx, String fileUrl) {
        //log.info("주문가격:"+totalAmount);
        this.ad = ad;
        this.userIdx = userIdx;
        this.fileUrl = fileUrl;
        KakaoReadyRes kakaoReadyRes = kakaoPayService.payReady();
        // 카카오 결제 준비 - 결제 요청 service 실행
        return kakaoReadyRes;
    }
    @GetMapping("/success")
    public BaseResponse<Advertisement> kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) throws IOException {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        KakaoApproveRes kakaoApproveRes = kakaoPayService.payApprove(pg_token);
        try{
            Advertisement newAd = adService.createAd(ad, userIdx, fileUrl);
            return new BaseResponse<>(newAd);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @GetMapping("/cancel")
    public void kakaoPayCancel() throws BaseException {
        throw  new BaseException(BaseResponseStatus.POST_AD_EMPTY_MAINMENU);
    }
    @GetMapping("/fail")
    public void kakaoPayFail() throws BaseException {
        throw  new BaseException(BaseResponseStatus.POST_AD_EMPTY_MAINMENU);
    }
}
