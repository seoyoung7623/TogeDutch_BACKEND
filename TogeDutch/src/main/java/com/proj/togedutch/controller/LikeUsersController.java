package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.LikeUsers;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.service.LikeUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/{userIdx}/likePost")
public class LikeUsersController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LikeUsersService likeUsersService;

    // 공고 관심목록에 등록 (userIdx = 공고를 관심목록에 담은 Like_user_id)
    @PostMapping("/{postIdx}")
    public BaseResponse<LikeUsers> createLikePost(@PathVariable("userIdx") int userIdx, @PathVariable("postIdx") int postIdx) throws BaseException {
        try {
            logger.info("createLikePost Start");
            LikeUsers likePost = likeUsersService.createLikePost(userIdx, postIdx);
            logger.info("createLikePost Success");
            return new BaseResponse<>(likePost);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
