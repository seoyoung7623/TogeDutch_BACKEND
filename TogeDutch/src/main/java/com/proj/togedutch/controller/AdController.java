package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Advertisement;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.AdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AdService adService;
    @Autowired
    AWSS3Service awsS3Service;

    @Value("${cloud.aws.url}")
    private String url;

    // 광고 생성
    @PostMapping("/{userIdx}")
    public BaseResponse<Advertisement> createAd(@PathVariable("userIdx") int userIdx, @RequestPart Advertisement ad,
                                                @RequestPart MultipartFile file) throws IOException{
        if (ad.getStore() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_STORE);
        }
        if (ad.getInformation() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_INFORMATION);
        }
        if (ad.getMainMenu() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_MAINMENU);
        }
        if (Integer.valueOf(ad.getDeliveryTips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_TIP);
        }
        if (ad.getLocation() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_LOCATION);
        }
        String fileUrl = null;
        if(!file.isEmpty())
            fileUrl = url + awsS3Service.uploadAdFile(file, ad, userIdx);

        try {
            Advertisement newAd = adService.createAd(ad, userIdx, fileUrl);
            return new BaseResponse<>(newAd);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }
    //광고 전체 조회
    @GetMapping("")
    public BaseResponse<List<Advertisement>> getAllAds(@RequestParam int userIdx) throws BaseException {
        try {
            List<Advertisement> getAds = adService.getAdsByUserId(userIdx);
            return new BaseResponse<>(getAds);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //광고 조회(랜덤 하나)
    @GetMapping("/random")
    public BaseResponse<Advertisement> getRandomAd() throws BaseException {
        try {
            Advertisement randomAd = adService.getRandomAd();
            return new BaseResponse<>(randomAd);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //광고 상세 조회
    @GetMapping("{adIdx}")
    public BaseResponse<Advertisement> getAdById(@PathVariable("adIdx") int adIdx) throws BaseException {
        try {
            Advertisement getAd = adService.getAdById(adIdx);
            return new BaseResponse<>(getAd);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

