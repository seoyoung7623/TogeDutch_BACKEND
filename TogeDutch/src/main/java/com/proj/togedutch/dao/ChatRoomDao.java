package com.proj.togedutch.dao;

import com.proj.togedutch.entity.ChatRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;


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
