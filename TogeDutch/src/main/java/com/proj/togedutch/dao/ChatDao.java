package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Chat;
import com.proj.togedutch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ChatDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //채팅 메세지 생성
    public int createMessage(Chat chat, int userIdx){ //ChatRoom chatroom 생성시 추가 필요
        String createChatQuery = "insert into Chat (content, user_id) Values (?,?)";
        Object[] createChatParams = new Object[]{chat.getContent(), userIdx};
        this.jdbcTemplate.update(createChatQuery, createChatParams);
        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // post_id 반환
    }

    //채팅 전체 조회 최신순
    public Chat getChat(int userIdx){
        String getUserQuery = "select * from Chat where chat_id = ?";
        int getCha
    }

    public User getChat(int userIdx) {
        String getUserQuery = "select * from User where user_id = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new Chat(
                        rs.getString("content"),
                        rs.getInt("ChatRoom_chatRoom_id"),
                        rs.getTimestamp("created_at")),
                getUserParams
        );
    }

}
