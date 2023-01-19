package com.proj.togedutch.dao;

import com.proj.togedutch.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatDao {

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

    //채팅 방

    //save
    public int saveMessage(ChatMessage message){
        String saveChatQuery = "INSERT INTO Chat (ChatRoom_chatRoom_id, User_user_id,created_at,content) VALUES (?,?,NOW(),?);";
        Object[] saveChatParams = new Object[]{message.getChatRoom_id(),message.getUser_id(),message.getContent()}; //ChatRoom,userID 외래키 관계설정해야됨
        this.jdbcTemplate.update(saveChatQuery,saveChatParams);
        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값 채팅내용
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public ChatMessage insertMessage(ChatMessage mes){
        String saveChatQuery = "INSERT INTO Chat (ChatRoom_chatRoom_id, User_user_id,created_at,content) VALUES (?,?,NOW(),?);";
        Object[] saveChatParams = new Object[]{mes.getChatRoom_id(),mes.getUser_id(),mes.getContent()};
        this.jdbcTemplate.update(saveChatQuery,saveChatParams);
        return mes;
    }

    public boolean checkChatRoomId(int user_id,int chatRoom_id){ //데이터의 갯수가 1개인지
        String sql = "select count(*) as count from mydb.Chat where ChatRoom_chatRoom_id = ? and User_user_id = ?";
        return jdbcTemplate.queryForList(sql,chatRoom_id,user_id).get(0).get("count").toString().equals("1");
    }

    public List<Integer> getRevUser(int user_id,int chatRoom_id){
        if(!checkChatRoomId(user_id,chatRoom_id))
            return new ArrayList<Integer>(); //중복제거후 유저 아이디 가져오기
        String sql = "SELECT DISTINCT User_user_id FROM mydb.Chat WHERE ChatRoom_chatRoom_id = ?";
        return jdbcTemplate.query(sql,new Object[] {chatRoom_id},(rs,rowNum)-> new Integer(
                String.valueOf(rs.getBytes("user_id"))));
    }

    //채팅

}
