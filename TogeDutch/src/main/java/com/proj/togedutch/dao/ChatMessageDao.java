package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class ChatMessageDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Resource(name = "redisTemplate")
//    private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
//    @Resource(name = "redisTemplate")
//    private HashOperations<String, String, String> hashOpsEnterInfo;
//    @Resource(name = "redisTemplate")
//    private ValueOperations<String, String> valueOps;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 채팅 전체 조회
    public List<ChatMessage> findAllChatByRoomId(String roomId){
        String sql = "SELECT chat_id,ChatRoom_chatRoom_id,User_user_id,created_at,content from Chat where ChatRoom_chatRoom_id = ?";

        return this.jdbcTemplate.query(sql,(rs,rowNum) -> new ChatMessage(
                rs.getInt("chat_id"),
                rs.getInt("ChatRoom_chatRoom_id"),
                rs.getInt("User_user_id"),
                rs.getTimestamp("created_at"),
                rs.getString("content")
        ),roomId);
    }

    public String userName(int userId){
        String sql = "SELECT name from User where user_id = ?";

        return this.jdbcTemplate.queryForObject(sql,String.class,userId); //String.class
    }

    public void save(ChatMessage message){
        Date currentTime = new Date();
        String roomIdName = Integer.toString(message.getChatRoom_id());
        String sql = "INSERT INTO Chat (`ChatRoom_chatRoom_id`, `User_user_id`, `created_at`, `content`) VALUES (?,?,?,?)";
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
        Object[] createMessageParams = new Object[]{roomIdName,message.getWriter(),datetime,message.getContent()};
        this.jdbcTemplate.update(sql, createMessageParams);
    }

    public List<ChatMessage> getChatMessages(int chatRoom_id) throws BaseException {
        String getMessagesQuery = "select Chat.*,User.name from Chat join User on Chat.User_user_id = User.user_id where ChatRoom_chatRoom_id = ?";

        return this.jdbcTemplate.query(getMessagesQuery,(rs, rowNum) -> new ChatMessage(
                rs.getInt("User_user_id"),
                rs.getTimestamp("created_at"),
                rs.getString("content"),
                rs.getString("name")
        ),chatRoom_id);
    }

    public String getImageUrl(int chatPhotoId){
        String getImageUrlQuery = "select image from ChatPhoto where chatPhoto_id = ?";
        return this.jdbcTemplate.queryForObject(getImageUrlQuery, String.class, chatPhotoId);
    }

    public ChatPhoto getChatPhoto(int chatRoomId,int chatPhotoId){
        String sql = "select * from ChatPhoto where chatPhoto_id = ? and ChatRoom_chatRoom_id = ?";
        return this.jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ChatPhoto(
                rs.getInt("chatPhoto_id"),
                rs.getInt("ChatRoom_chatRoom_id"),
                rs.getInt("User_user_id"),
                rs.getString("image"),
                rs.getTimestamp("created_at")
        ),chatPhotoId,chatRoomId);
    }


    public int createChatPhoto(int chatRoomId,int userId,String file) {
        Date currentTime = new Date();
        String ChatPhotoQuery = "insert into ChatPhoto (ChatRoom_chatRoom_id,User_user_id,image,created_at) Values (?,?,?,?)";

        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
        Object[] createChatPhoto = new Object[]{chatRoomId,userId,file,datetime};
        this.jdbcTemplate.update(ChatPhotoQuery, createChatPhoto);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int createChatMeetTime(int chatRoom_id, int user, Timestamp time) {
        String chatMeetTimeQuery = "insert into ChatMeetTime (ChatRoom_chatRoom_id,User_user_id,meetTime) values (?,?,?)";
        Object[] MeetTimeParams = new Object[]{chatRoom_id,user,time};
        this.jdbcTemplate.update(chatMeetTimeQuery,chatMeetTimeQuery);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public ChatMeetTime getChatMeetTime(int chatRoom_id, int chatMeetTime_id) {
        String sql = "select * from ChatMeetTime where chatMeetTime_id = ? and ChatRoom_chatRoom_id = ?";
        return this.jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ChatMeetTime(
                        rs.getInt("chatMeetTime_id"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getInt("User_user_id"),
                        rs.getTimestamp("meetTime")
                ),chatMeetTime_id,chatRoom_id);
    }
}
