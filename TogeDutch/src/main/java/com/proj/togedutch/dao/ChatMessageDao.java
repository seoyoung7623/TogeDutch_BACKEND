package com.proj.togedutch.dao;

import com.proj.togedutch.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        String sql = "INSERT INTO Chat (`ChatRoom_chatRoom_id`, `User_user_id`, `created_at`, `content`) VALUES (?,?,?,?)";
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
        Object[] createMessageParams = new Object[]{message.getRoomId(),message.getWriter(),datetime,message.getContent()};
        this.jdbcTemplate.update(sql, createMessageParams);
    }


}
