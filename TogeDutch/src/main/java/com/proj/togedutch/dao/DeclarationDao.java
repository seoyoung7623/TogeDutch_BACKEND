package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Declaration;
import com.proj.togedutch.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

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
                = "insert into Declaration (content, status, ChatRoom_chatRoom_id) " +
                "VALUES (?, ?, ?)";
        Object[] createDeclarationParams = new Object[]{declaration.getContent(), declaration.getStatus(), chatRoomIdx};
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
}
