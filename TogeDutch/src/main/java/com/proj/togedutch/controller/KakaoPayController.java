package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Advertisement;
import com.proj.togedutch.entity.KakaoApproveRes;
import com.proj.togedutch.entity.KakaoCancelRes;
import com.proj.togedutch.entity.KakaoReadyRes;
import com.proj.togedutch.service.AdService;
import com.proj.togedutch.service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            Advertisement newAd = adService.createAd(ad, userIdx, fileUrl, kakaoApproveRes.getTid());
            return new BaseResponse<>(newAd); // 광고 신청 현황 페이지로 redirect
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/cancel")
    public void kakaoPayCancel() throws BaseException {
        throw new BaseException(BaseResponseStatus.KAKAO_PAY_CANCLE); // 결제 취소화면 redirect
    }
    @GetMapping("/fail")
    public void kakaoPayFail() throws BaseException {
        throw new BaseException(BaseResponseStatus.KAKAO_PAY_FAIL); // 결제 실패화면 redirect
    }

    @PostMapping("/refund")
    public ResponseEntity kakaoPayRefund(@RequestParam("tid") String tid){
        KakaoCancelRes kakaoCancelRes = kakaoPayService.payCancel(tid);
        try {
            adService.deleteAd(tid);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getStatus(), HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(kakaoCancelRes, HttpStatus.OK);
    }
}
