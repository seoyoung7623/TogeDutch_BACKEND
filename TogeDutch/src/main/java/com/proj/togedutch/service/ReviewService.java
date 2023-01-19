package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.dao.ReviewDao;
import com.proj.togedutch.dao.UserDao;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

public class ReviewService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ReviewDao reviewDao;

    public Review createReview(int applicationId,Review review) throws BaseException {
        try {

            Review createReview = reviewDao.createReview(applicationId,review);
            return createReview;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //키워드 조회(키워드 id)
    public Review getTextReview(int reviewId,int postId) throws BaseException{
        try {
            return reviewDao.getTextReview(reviewId,postId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
