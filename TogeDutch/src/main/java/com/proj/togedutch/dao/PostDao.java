package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;


@Repository
public class PostDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public int createPost(Post post, int userIdx, String fileUrl) {
        String createPostQuery
                = "insert into Post (title, url, delivery_tips, minimum, order_time, num_of_recruits, location, User_user_id, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createPostParams = new Object[]{post.getTitle(), post.getUrl(), post.getDelivery_tips(), post.getMinimum(), post.getOrder_time(), post.getNum_of_recruits(), post.getLocation(), userIdx, fileUrl};
        this.jdbcTemplate.update(createPostQuery, createPostParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // post_id 반환
    }

    public Post getPostById(int postIdx){
        String getPostQuery = "select * from Post where post_id = ?";
        return this.jdbcTemplate.queryForObject(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ), postIdx);
    }

    // 공고 전체 조회
    public List<Post> getAllPosts(){
        String getPostQuery = "select * from Post";
        return this.jdbcTemplate.query(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ));
    }

    // 공고 전체 조회 (최신순 / 주문 임박)
    public List<Post> getSortingPosts(String sort){
        String getPostQuery;

        if(sort.equals("latest"))   // 최신순
            getPostQuery = "select * from Post order by created_at desc";
        else                        // 주문 임박
            getPostQuery = "select * from Post where order_time between now() and DATE_ADD(NOW(), INTERVAL 10 MINUTE) order by order_time asc";

        return this.jdbcTemplate.query(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ));
    }

    public String getImageUrl(int postIdx){
        String getImageUrlQuery = "select image from Post where post_id = ?";
        return this.jdbcTemplate.queryForObject(getImageUrlQuery, String.class, postIdx);
    }

    // 공고 수정
    @Transactional(rollbackFor = Exception.class)
    public Post modifyPost(int postIdx, Post post, int userIdx, String fileUrl) throws BaseException {
        String modifyPostQuery = "update Post " +
                "set title = ?, url = ?, delivery_tips = ?, minimum = ?, order_time = ?, num_of_recruits = ?, location = ?, User_user_id = ?, image = ?, updated_at = ? " +
                "where post_id = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

        Object[] modifyPostParams = new Object[]{post.getTitle(), post.getUrl(), post.getDelivery_tips(), post.getMinimum(), post.getOrder_time(), post.getNum_of_recruits(), post.getLocation(), userIdx, fileUrl, sdf.format(timeStamp), postIdx};

        if (this.jdbcTemplate.update(modifyPostQuery, modifyPostParams) == 1)
            return getPostById(postIdx);
        else {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Post getPostByUserId(int postIdx, int userIdx) throws BaseException {
        String getPostQuery = "select * from Post where post_id = ? and User_user_id = ?";

        return this.jdbcTemplate.queryForObject(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ), postIdx, userIdx);
    }

    public int deletePost(int postIdx, Post post, int userIdx) {
        String deletePostQuery
                = "delete from Post WHERE post_id = ? and User_user_id = ?";
        Object[] deletePostParams = new Object[]{postIdx,userIdx};
        return this.jdbcTemplate.update(deletePostQuery, deletePostParams);

    }
    public Post getPostByJoinUserId(int postIdx, int userIdx) throws BaseException {
        String getPostQuery = "select * from Application where post_id = ? and User_user_id = ?";

        return this.jdbcTemplate.queryForObject(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ), postIdx, userIdx);
    }
    public Post getPostByUploadUserId(int postIdx, int userIdx) throws BaseException {
        String getPostQuery = "select * from Post where post_id = ? and User_user_id = ?";

        return this.jdbcTemplate.queryForObject(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ), postIdx, userIdx);
    }
    public Post getPostByTitleUserId(String title) throws BaseException {
        String getPostQuery = "select * from Post where title = ? ";

        return this.jdbcTemplate.queryForObject(getPostQuery,
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
                        rs.getString("location"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
                ), title);
    }

}
