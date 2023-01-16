package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.entity.LikeUsers;
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

    @PostMapping("/{postIdx}")
    public BaseResponse<LikeUsers> createLikePost(@PathVariable("userIdx") int userIdx,   )

}
