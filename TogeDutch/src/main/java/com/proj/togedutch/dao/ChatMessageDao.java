package com.proj.togedutch.dao;

import com.proj.togedutch.dto.ChatMessageDetailDto;
import com.proj.togedutch.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ChatMessageDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ChatMessageDetailDto> findAllChatByRoomId(String roomId){
        String sql = "SELECT * from Chat where ChatRoom_chatRoom_id = ?";

        return this.jdbcTemplate.query(sql,(rs,rowNum) -> new ChatMessageDetailDto(
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


}
