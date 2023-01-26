package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.LikeUsers;
import com.proj.togedutch.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.ion.Decimal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

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

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
    public int deleteLikePost(int userIdx, int postIdx) throws BaseException {
        String deleteLikePostQuery = "delete from LikeUsers where User_user_id = ? and Post_post_id = ?";
        Object[] deleteLikePostParams = new Object[]{userIdx, postIdx};

        if (this.jdbcTemplate.update(deleteLikePostQuery, deleteLikePostParams) == 1)
            return 1;
        else
            throw new BaseException(DATABASE_ERROR);
    }


    // userIdx가 누른 관심 목록 리스트로 반환
    public List<Post> getLikePosts(int userIdx) throws BaseException {
        String getLikePostsQuery = "select * from Post where post_id IN (select Post_post_id from LikeUsers where User_user_id = ?)";

        // LikeUsers의 userIdx와 같은 user_id를 가진 놈의 post_id를 가진 Post를 List를 반환
        return this.jdbcTemplate.query(getLikePostsQuery,
                (rs, rowNum) -> new Post(
                        rs.getInt("post_id"),
                        rs.getString("title"),
                        rs.getString("url"),
                        rs.getInt("delivery_tips"),
                        rs.getInt("minimum"),
                        rs.getTimestamp("order_time"),
                        rs.getInt("num_of_recruits"),
                        rs.getInt("recruited_num"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
                ), userIdx);
    }
}
