package com.proj.togedutch.dao;

import com.proj.togedutch.entity.LikeUsers;
import com.proj.togedutch.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LikeUsersDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // post 등록한 user_id 반환
    public int findUploader(int postIdx){
        String findUploaderQuery = "select User_user_id from Post where post_id = ?";
        int uploader = this.jdbcTemplate.queryForObject(findUploaderQuery, new Object[]{postIdx}, Integer.class);

        return uploader;
    }

    public int createLikePost(int userIdx, int postIdx, int Uploader_userIdx){
        String createLikePostQuery
                = "insert into LikeUsers(Post_post_id, Post_User_user_id, User_user_id) VALUES (?, ?, ?)";
        Object[] createLikePostParams = new Object[]{postIdx, Uploader_userIdx, userIdx};
        this.jdbcTemplate.update(createLikePostQuery, createLikePostParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public LikeUsers getLikePostById(int likeIdx){
        String getLikePostQuery = "select * from LikeUsers where like_id = ?";
        return this.jdbcTemplate.queryForObject(getLikePostQuery,
                (rs, rowNum) -> new LikeUsers(
                        rs.getInt("like_id"),
                        rs.getInt("Post_post_id"),
                        rs.getInt("Post_User_user_id"),
                        rs.getInt("User_user_id")
                ), likeIdx);
    }
}
