package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Review;
import com.proj.togedutch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

public class ReviewDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public Review createReview(int applicationId,Review review) {
        String getUserQuery = "insert into Review (title, url, delivery_tips, minimum, order_time, num_of_recruits, User_user_id, image, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int getUserParams = applicationId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new Review(
                        rs.getInt("user_id"),
                        rs.getInt("Keyword_keyword_id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("status"),
                        rs.getString("image"),
                        rs.getString("jwt")),
                getUserParams
        );
    }
    public Review getTextReview(int reviewId,int postId) {
        String getKeywordQuery = "select * from Keyword where keyword_id = ?";
        int getKeywordParams = keywordIdx;
        return this.jdbcTemplate.queryForObject(getKeywordQuery,
                (rs, rowNum) -> new Keyword(
                        rs.getInt("keyword_id"),
                        rs.getString("word1"),
                        rs.getString("word2"),
                        rs.getString("word3"),
                        rs.getString("word4"),
                        rs.getString("word5"),
                        rs.getString("word6")),
                getKeywordParams);
    }

}
