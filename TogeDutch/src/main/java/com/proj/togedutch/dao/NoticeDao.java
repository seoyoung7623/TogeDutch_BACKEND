package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

public class NoticeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public NoticeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    //공지사항 전체조회(최신순)
    public List<Notice> getSortingNotice(String sort){
        String getNoticeQuery=null;

        if(sort.equals("latest"))   // 최신순
            getNoticeQuery = "select * from Notice order by created_at desc";

        return this.jdbcTemplate.query(getNoticeQuery,
                (rs, rowNum) -> new Notice(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("status")
                ));
    }


    //특정 공지사항 조회
    public Notice getNotice(int noticeIdx) {
        String getNoticeQuery = "select * from User where user_id = ?";
        int getNoticeParams = noticeIdx;
        return this.jdbcTemplate.queryForObject(getNoticeQuery,
                (rs, rowNum) -> new Notice(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("status"),
                        getNoticeParams
        );
    }





}
