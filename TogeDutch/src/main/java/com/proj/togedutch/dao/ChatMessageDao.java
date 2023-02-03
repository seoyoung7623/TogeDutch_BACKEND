package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.ChatLocation;
import com.proj.togedutch.entity.ChatMeetTime;
import com.proj.togedutch.entity.ChatMessage;
import com.proj.togedutch.entity.ChatPhoto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public void saveMessage(ChatMessage message){
        String roomIdName = Integer.toString(message.getChatRoom_id());
        String sql = "INSERT INTO Chat (`ChatRoom_chatRoom_id`, `User_user_id`, `content`) VALUES (?,?,?)";
        Object[] createMessageParams = new Object[]{message.getChatRoom_id(),message.getUserId(),message.getContent()};
        this.jdbcTemplate.update(sql, createMessageParams);
    }

    public int deleteChat(int chatRoomIdx) throws BaseException {
        String deleteChatQuery
                = "delete from Chat where ChatRoom_chatRoom_id = ?";
        Object[] deleteChatParams = new Object[]{chatRoomIdx};
        return this.jdbcTemplate.update(deleteChatQuery, deleteChatParams);
    }

    public int deleteChatPhoto(int chatRoomIdx) throws BaseException {
        String deleteChatPhotoQuery
                = "delete from ChatPhoto where ChatRoom_chatRoom_id = ?";
        Object[] deleteChatPhotoParams = new Object[]{chatRoomIdx};
        return this.jdbcTemplate.update(deleteChatPhotoQuery, deleteChatPhotoParams);
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

    public ChatPhoto getChatPhoto(int chatRoomId,int chatPhotoId){
        String sql = "select * from ChatPhoto where chatPhoto_id = ? and ChatRoom_chatRoom_id = ?";
        return this.jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ChatPhoto(
                        rs.getInt("chatPhoto_id"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getInt("User_user_id"),
                        rs.getString("image")
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

    public int createChatMeetTime(int chatRoom_id, int user, String time) {
        String chatMeetTimeQuery = "insert into ChatMeetTime (ChatRoom_chatRoom_id,User_user_id,meetTime) values (?,?,?)";
        Object[] MeetTimeParams = new Object[]{chatRoom_id,user,time};
        this.jdbcTemplate.update(chatMeetTimeQuery,MeetTimeParams);
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
                        rs.getString("meetTime")
                ),chatMeetTime_id,chatRoom_id);
    }

    public void putChatMeetTime(int chatRoom_id,int chatMeetTime_id,String time){
        String updateMeetTimeQuery = "update ChatMeetTime set meetTime=? where chatMeetTime_id = ? and ChatRoom_chatRoom_id = ?";
        Object[] putMeetTimeParams = new Object[]{time,chatMeetTime_id,chatRoom_id};
        this.jdbcTemplate.update(updateMeetTimeQuery,putMeetTimeParams);
    }

    public int createChatLocation(int chatRoom_id, int user, BigDecimal latitude, BigDecimal longitude) {
        String createChatLocationQuery = "insert into ChatLocation (ChatRoom_chatRoom_id,User_user_id, latitude, longitude) values (?,?,?,?)";
        Object[] createChatLocationParams = new Object[]{chatRoom_id,user,latitude, longitude};
        this.jdbcTemplate.update(createChatLocationQuery,createChatLocationParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public ChatLocation getChatLocation(int chatRoom_id, int chatLocationIdx) {
        String sql = "select * from ChatLocation where chatLocation_id = ? and ChatRoom_chatRoom_id = ?";
        return this.jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ChatLocation(
                        rs.getInt("chatLocationIdx"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getInt("User_user_id"),
                        rs.getBigDecimal("latitude"),
                        rs.getBigDecimal("longitude")
                ),chatLocationIdx, chatRoom_id);
    }

    public void putChatLocation(int chatRoom_id, int chatLocationIdx, BigDecimal latitude, BigDecimal longitude) {
        String updateCLQuery = "update ChatLocation set latitude=?, longitude=? where chatLocation_id = ? and ChatRoom_chatRoom_id = ?";
        Object[] putChatLocationParams = new Object[]{latitude,longitude,chatLocationIdx,chatRoom_id};
        this.jdbcTemplate.update(updateCLQuery,putChatLocationParams);
    }

}
