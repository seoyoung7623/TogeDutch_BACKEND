package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.dao.LikeUsersDao;
import com.proj.togedutch.entity.LikeUsers;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class LikeUsersService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LikeUsersDao likeUsersDao;

    @Autowired
    JwtService jwtService;

    public LikeUsers createLikePost(int userIdx, int postIdx) throws BaseException {
        try {
            int Uploader_user_id = likeUsersDao.findUploader(postIdx);
            logger.info("Uploader_id : " + Uploader_user_id);

            int likeIdx = likeUsersDao.createLikePost(userIdx, postIdx, Uploader_user_id);
            LikeUsers likeUsers = likeUsersDao.getLikePostById(likeIdx);
            return likeUsers;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
