package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.PostDao;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.*;

@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostDao postDao;

    public Post createPost(Post post, int userIdx, String fileUrl) throws BaseException {
        try {
            int postIdx = postDao.createPost(post, userIdx, fileUrl);
            Post createPost = postDao.getPostById(postIdx);
            return createPost;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Post> getAllPosts() throws BaseException {
        try{
            return postDao.getAllPosts();
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Post> getSortingPosts(String sort) throws BaseException{
        try{
            return postDao.getSortingPosts(sort);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public String getImageUrl(int postIdx) throws BaseException {
        try{
            return postDao.getImageUrl(postIdx);
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Post modifyPost(int postIdx, Post post, int userIdx, String fileUrl) throws BaseException {
        try{
            Post modifyPost = postDao.modifyPost(postIdx, post, userIdx, fileUrl);
            return modifyPost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int deletePost(int postIdx, Post post, int userIdx) throws BaseException {
        try{
            int deletePost = postDao.deletePost(postIdx, post, userIdx);
            return deletePost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public Post getPostByJoinUserId(int postIdx, int userIdx) throws BaseException {
        try{
            Post joinPost = postDao.getPostByJoinUserId(postIdx, userIdx);
            return joinPost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public Post getPostByUploadUserId(int postIdx, int userIdx) throws BaseException {
        try{
            Post UploadPost = postDao.getPostByUploadUserId(postIdx, userIdx);
            return UploadPost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public Post getPostByTitleUserId(String title) throws BaseException {
        try{
            Post TitlePost = postDao.getPostByTitleUserId(title);
            return TitlePost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public Post getPostByUserId(int postIdx, int userIdx) throws BaseException {
        try{
            Post getPost = postDao.getPostByUserId(postIdx, userIdx);
            return getPost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
