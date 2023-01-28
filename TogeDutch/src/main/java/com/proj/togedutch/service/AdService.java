package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.AdDao;
import com.proj.togedutch.entity.Advertisement;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AdService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdDao adDao;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @Autowired //readme 참고
    public AdService(AdDao adDao, JwtService jwtService) {
        this.adDao = adDao;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    public Advertisement createAd(Advertisement ad, int userIdx, String fileUrl, String tid) throws BaseException {
        try {
            int adIdx = adDao.createAd(ad, userIdx, fileUrl, tid);
            return adDao.getAdById(adIdx);
        }catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Advertisement> getAdsByUserId(int userIdx) throws BaseException {
        try{
            List<Advertisement> adsByUserId = adDao.getAdsByUserId(userIdx);
            return adsByUserId;
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Advertisement getRandomAd() throws BaseException {
        try {
            Advertisement randomAd = adDao.getRandomAd();
            return randomAd;
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Advertisement getAdById(int adIdx) throws BaseException {
        try {
            Advertisement getAd = adDao.getAdById(adIdx);
            return getAd;
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
