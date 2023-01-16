package com.proj.togedutch.service;

import com.proj.togedutch.dao.LikeUsersDao;
import com.proj.togedutch.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeUsersService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LikeUsersDao likeUsersDao;

    @Autowired
    JwtService jwtService;
}
