package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PostDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 공고 생성 -> 채팅방 생성
    @Transactional(rollbackFor = Exception.class)
    public int createPost(Post post, int userIdx, String fileUrl) {
        String createPostQuery
                = "insert into Post (title, url, delivery_tips, minimum, order_time, num_of_recruits, User_user_id, image, latitude, longitude, category) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createPostParams = new Object[]{post.getTitle(), post.getUrl(), post.getDelivery_tips(), post.getMinimum(), post.getOrder_time(), post.getNum_of_recruits(), userIdx, fileUrl, post.getLatitude(), post.getLongitude(), post.getCategory()};
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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
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
                "set title = ?, url = ?, delivery_tips = ?, minimum = ?, order_time = ?, num_of_recruits = ?, User_user_id = ?, image = ?, updated_at = ? , latitude = ?, longitude = ?, category = ?" +
                "where post_id = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

        Object[] modifyPostParams = new Object[]{post.getTitle(), post.getUrl(), post.getDelivery_tips(), post.getMinimum(), post.getOrder_time(), post.getNum_of_recruits(), userIdx, fileUrl, sdf.format(timeStamp), post.getLatitude(), post.getLongitude(), post.getCategory(), postIdx};

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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
                ), postIdx, userIdx);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deletePost(int postIdx, int userIdx) {
        String deletePostQuery
                = "delete from Post WHERE post_id = ? and User_user_id = ?";
        Object[] deletePostParams = new Object[]{postIdx,userIdx};
        return this.jdbcTemplate.update(deletePostQuery, deletePostParams);

    }
    public List<Post> getPostByJoinUserId(int userIdx) throws BaseException {
        String getPostQuery = "select * From Post where post_id In( select Post_post_id from Application where User_user_id = ? )";

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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
                ), userIdx);
    }
    public List<Post> getPostByUploadUserId(int userIdx) throws BaseException {
        String getPostQuery = "select * from Post where User_user_id = ? ";

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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
                ), userIdx);
    }
    public List<Post> getPostByTitleUserId(String title) throws BaseException {
        String getPostQuery = "select * from Post where title = ? ";

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
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getString("category")
                ), title);
    }

    @Transactional(rollbackFor = Exception.class)
    public Post insertChatRoom(int postIdx, int chatRoomIdx) throws BaseException {
        String modifyPostQuery = "update Post set ChatRoom_chatRoom_id = ? where post_id = ?";

        Object[] modifyPostParams = new Object[]{chatRoomIdx, postIdx};

        if (this.jdbcTemplate.update(modifyPostQuery, modifyPostParams) == 1)
            return getPostById(postIdx);
        else {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public int modifyPostByChatRoomId(int chatRoomIdx) throws BaseException {
        String modifyPostQuery = "update Post set ChatRoom_chatRoom_id=null where ChatRoom_chatRoom_id = ?";
        Object[] modifyPostParams = new Object[]{chatRoomIdx};

        return this.jdbcTemplate.update(modifyPostQuery, modifyPostParams);
    }

    @Transactional(rollbackFor = Exception.class)
    public Post modifyPostStatus(int postIdx) throws BaseException {
        String modifyPostQuery = "update Post set status=\"시간만료\" where post_id = ? and order_time < current_timestamp and num_of_recruits != recruited_num";

        Object[] modifyPostParams = new Object[]{ postIdx };

        if (this.jdbcTemplate.update(modifyPostQuery, modifyPostParams) == 1)
            return getPostById(postIdx);
        else {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
