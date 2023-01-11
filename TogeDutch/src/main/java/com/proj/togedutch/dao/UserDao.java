package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery, int.class, checkEmailParams);
    }
    @Transactional(rollbackFor = Exception.class)
    public int createUser(User user) {
        String createUserQuery = "insert into User (keyword_id, name, role, email, password, phone, location, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createUserParams = new Object[]{user.getKeywordIdx(), user.getName(), user.getRole(), user.getEmail(), user.getPassword(), user.getPhone(), user.getLocation(), user.getStatus()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }
    @Transactional(rollbackFor = Exception.class)
    public int createKeyword(Keyword keyword) {
        String createKeywordQuery = "insert into Keyword (word1, word2, word3, word4, word5, word6) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createKeywordParams = new Object[]{keyword.getWord1(), keyword.getWord2(), keyword.getWord3(),
                keyword.getWord4(), keyword.getWord5(), keyword.getWord6()};
        this.jdbcTemplate.update(createKeywordQuery, createKeywordParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public User getUser(int userIdx) {
        String getUserQuery = "select * from User where user_id = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("user_id"),
                        rs.getInt("Keyword_keyword_id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("location"),
                        rs.getString("status")),
                        getUserParams
                );
    }
    public Keyword getKeyword(int keywordIdx) {
        String getKeywordQuery = "select * from Keyword where keyword_id = ?";
        int getKeywordParams = keywordIdx;
        return this.jdbcTemplate.queryForObject(getKeywordQuery,
                (rs, rowNum) -> new Keyword(
                        rs.getInt("keyword_id"),
                        rs.getString("word1"),
                        rs.getString("word2"),
                        rs.getString("word3"),
                        rs.getString("word4"),
                        rs.getString("word5"),
                        rs.getString("word6")),
                getKeywordParams);
    }
}
