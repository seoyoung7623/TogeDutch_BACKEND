package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.secret.Secret;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.dao.UserDao;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.utils.AES128;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.proj.togedutch.config.BaseResponseStatus.*;

@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostDao postDao;

    public Post createPost(Post post, int userIdx, String fileUrl) throws BaseException {
        int postIdx = postDao.createPost(post, userIdx, fileUrl);
        Post createPost = postDao.getPostById(postIdx);
        return createPost;
        /*
        try {
            int postIdx = postDao.createPost(post, userIdx);
            Post createPost = postDao.getPostById(postIdx);
            return createPost;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

         */
    }
}
