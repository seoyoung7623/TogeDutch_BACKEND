package com.proj.togedutch.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.secret.Secret;
import com.proj.togedutch.dao.UserDao;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.utils.AES128;
import com.proj.togedutch.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.proj.togedutch.config.BaseResponseStatus.*;

@Service
public class OAuthService {
    @Autowired
    UserDao userDao;

    @Autowired
    JwtService jwtService;


    public User kakaoLogin(String email) throws BaseException {
        User user;
        String password;

        try {
            user = userDao.getUserInfoByEmail(email);
        } catch(EmptyResultDataAccessException e1){
            System.out.println("결과가 없습니다.");
            throw new BaseException(FAILED_TO_KAKAO_LOGIN);
        } catch (BaseException e2) {
            throw new BaseException(DATABASE_ERROR);
        }

        System.out.println("login : " + user.getUserIdx());
        int userIdx = user.getUserIdx();
        String jwt = jwtService.createJwt(userIdx);
        user.setJwt(jwt);

        System.out.println("User객체 : " + user);
        return user;
    }
}
