package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Application;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Review;
import com.proj.togedutch.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class ReviewDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public int createReview(int applicationId, Review review) {

        String getApplicationQuery = "select * from Application where application_id = ?";
        int getApplicationParams = applicationId;
        Application application = this.jdbcTemplate.queryForObject(getApplicationQuery,
                (rs, rowNum) -> new Application(
                        rs.getInt("application_id"),
                        rs.getString("status"),
                        rs.getInt("Post_post_id"),
                        rs.getInt("User_user_id"),
                        rs.getInt("ChatRoom_chatRoom_id")),
                getApplicationParams
        );

        logger.info(String.valueOf(application.getStatus()));
        System.out.println(application.getUser_id());

        String InsertReviewQuery = "insert into Review (emotion_status, content, Application_application_Id, Application_Post_post_id, Application_Post_User_user_id) VALUES (?, ?, ?, ?, ?) ";
        System.out.println(review.getEmotionStatus());
        System.out.println(review.getContent());

        Object[] createReviewParams = new Object[]{review.getEmotionStatus(), review.getContent(), applicationId, application.getPost_id(), application.getUser_id()};
        return this.jdbcTemplate.update(InsertReviewQuery, createReviewParams);

    }
    public List<Review> getTextReview(int postId) {
        String getTextReviewQuery = "select * from Review where Application_Post_post_id = ? ";
        return this.jdbcTemplate.query(getTextReviewQuery,
                (rs, rowNum) -> new Review(
                        rs.getInt("review_id"),
                        rs.getInt("emotion_status"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("Application_application_id"),
                        rs.getInt("Application_Post_post_id"),
                        rs.getInt("Application_Post_User_user_id")),
                postId);
    }

}
