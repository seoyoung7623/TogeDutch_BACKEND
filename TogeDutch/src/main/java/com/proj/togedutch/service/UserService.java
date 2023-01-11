package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.config.secret.Secret;
import com.proj.togedutch.dao.UserDao;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.utils.AES128;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.proj.togedutch.config.BaseResponseStatus.*;


@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserDao userDao;
    @Autowired
    JwtService jwtService;

    //user 만들기
    public User createUser(User user) throws BaseException{
        if (checkEmail(user.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        String pwd;
        try {
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int userIdx = userDao.createUser(user);
            String jwt = jwtService.createJwt(userIdx);
            User createUser = getUser(userIdx);
            createUser.setJwt(jwt);
            return createUser;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkEmail(String email) {
        return userDao.checkEmail(email);
    }
    //user 조회(id)
    public User getUser(int userIdx) throws BaseException{
        try {
            User user = userDao.getUser(userIdx);
            return user;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //키워드 생성
    public Keyword createKeyword(Keyword keyword) throws BaseException {
        try {
            int keywordIdx = userDao.createKeyword(keyword);
            Keyword createKeyword = getKeyword(keywordIdx);
            return createKeyword;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //키워드 조회(키워드 id)
    public Keyword getKeyword(int keywordIdx) throws BaseException{
        try {
            return userDao.getKeyword(keywordIdx);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
