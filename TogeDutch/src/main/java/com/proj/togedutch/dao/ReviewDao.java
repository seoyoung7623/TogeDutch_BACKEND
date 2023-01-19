package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Review;
import com.proj.togedutch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

public class ReviewDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public int createReview(int applicationId, Review review) {
        String InsertReviewQuery = "insert into Review (title, url, delivery_tips, minimum, order_time, num_of_recruits, User_user_id, image, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createReviewParams = new Object[]{review.getEmotionStatus(), review.getContent(), applicationId, review.getPostId(), review.getUserId()};
        return this.jdbcTemplate.update(InsertReviewQuery, createReviewParams);

    }
    public List<Review> getTextReview(int reviewId, int postId) {
        String getTextReviewQuery = "select * from Review where review_id = ?";
        int getreviewParams = reviewId;
        return this.jdbcTemplate.query(getTextReviewQuery,
                (rs, rowNum) -> new Review(
                        rs.getInt("review_id"),
                        rs.getInt("emotion_status"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("Application_application_id"),
                        rs.getInt("Application_Post_post_id"),
                        rs.getInt("Application_Post_User_user_id")),
                getreviewParams);
    }

}
