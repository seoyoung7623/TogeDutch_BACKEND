package com.proj.togedutch.dao;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.Declaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DeclarationDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public int createDeclaration(Declaration declaration, int chatRoomIdx) {
        String createDeclarationQuery
                = "insert into Declaration (content, ChatRoom_chatRoom_id) " +
                "VALUES (?, ?)";
        Object[] createDeclarationParams = new Object[]{declaration.getContent(), chatRoomIdx};
        this.jdbcTemplate.update(createDeclarationQuery, createDeclarationParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // notice_id 반환
    }

    public Declaration getDeclarationById(int declarationIdx){
        String getDeclarationQuery = "select * from Declaration where declaration_id = ?";
        return this.jdbcTemplate.queryForObject(getDeclarationQuery,
                (rs, rowNum) -> new Declaration(
                        rs.getInt("declaration_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getString("status"),
                        rs.getInt("ChatRoom_chatRoom_id")
                ), declarationIdx);
    }

    public List<Declaration> getAllDeclarations() throws BaseException {
        String getDeclarationQuery = "select * from Declaration order by created_at desc";

        return this.jdbcTemplate.query(getDeclarationQuery,
                (rs, rowNum) -> new Declaration(
                        rs.getInt("declaration_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getString("status"),
                        rs.getInt("ChatRoom_chatRoom_id")
                ));
    }

    public List<Declaration> getDeclarationByChatRoomId(int chatRoomIdx) throws BaseException {
        String getDeclarationQuery = "select * from Declaration where ChatRoom_chatRoom_id = ?";

        return this.jdbcTemplate.query(getDeclarationQuery,
                (rs, rowNum) -> new Declaration(
                        rs.getInt("declaration_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getString("status"),
                        rs.getInt("ChatRoom_chatRoom_id")
                ), chatRoomIdx);
    }
}
