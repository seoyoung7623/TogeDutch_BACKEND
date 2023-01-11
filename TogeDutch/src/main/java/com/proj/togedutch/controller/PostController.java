package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.PostService;
import com.proj.togedutch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.proj.togedutch.utils.ValidationRegex.isRegexEmail;

@Controller
@RequestMapping("/post")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostService postService;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<Post> createPost(@RequestParam int user, @RequestBody Post post){
        if (post.getTitle() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TITLE);
        }
        if (post.getUrl() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_URL);
        }
        if (Integer.valueOf(post.getDelivery_tips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TIP);
        }
        if (Integer.valueOf(post.getMinimum()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_MINIMUM);
        }
        if (Integer.valueOf(post.getNum_of_recruits()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_RECRUIT);
        }
        if (post.getLocation() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_LOCATION);
        }

        try {
            Post newPost = postService.createPost(post, user);
            return new BaseResponse<>(newPost);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
