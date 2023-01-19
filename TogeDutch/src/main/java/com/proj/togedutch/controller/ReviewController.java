package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.Review;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.PostService;
import com.proj.togedutch.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AWSS3Service awsS3Service;

    @Autowired
    ReviewService reviewService;

    @Value("${cloud.aws.url}")
    private String url;

    @ResponseBody
    @PostMapping("/application/{applicationId}")
    public BaseResponse<Integer> createReview(@RequestPart Review review, @PathVariable("applicationId") int applicationId) {
        try {
            int createReview = reviewService.createReview(applicationId,review);
            return new BaseResponse<>(createReview);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //keyword-2
    @ResponseBody
    @GetMapping("/{postId}/{reviewId}")
    public BaseResponse<List<Review>> getTextReview(@PathVariable("postId") int postId, @PathVariable("reviewId") int reviewId) {
        try {
            List<Review> getTextReview = reviewService.getTextReview(reviewId,postId);
            return new BaseResponse<>(getTextReview);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }



}
