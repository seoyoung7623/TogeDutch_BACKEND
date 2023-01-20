package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.secret.Secret;
import com.proj.togedutch.dao.AdDao;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.dao.UserDao;
import com.proj.togedutch.entity.Advertisement;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.utils.AES128;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.proj.togedutch.config.BaseResponseStatus.*;

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

    public Advertisement createAd(Advertisement ad, int userIdx, String fileUrl) throws BaseException {
        try {
            int adIdx = adDao.createAd(ad, userIdx, fileUrl);
            return adDao.getAdById(adIdx);
        }catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
