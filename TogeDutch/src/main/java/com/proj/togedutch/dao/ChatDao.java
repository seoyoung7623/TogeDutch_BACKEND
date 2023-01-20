package com.proj.togedutch.dao;

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

@Repository
public class ChatDao {
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

    //ChatRoomDao
    public int createChatRoom(){
        String createChatRoomQuery = "insert into ChatRoom (chatRoom_id, created_at) values (DEFAULT, DEFAULT)";
        this.jdbcTemplate.update(createChatRoomQuery);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // p
    }

    public ChatRoom getChatRoomById(int chatRoomIdx) {
        String getChatRoomQuery = "select * from ChatRoom where chatRoom_id = ?";
        return this.jdbcTemplate.queryForObject(getChatRoomQuery,
                (rs, rowNum) -> new ChatRoom(
                        rs.getInt("chatRoom_id"),
                        rs.getTimestamp("created_at")
                ), chatRoomIdx);
    }
}
