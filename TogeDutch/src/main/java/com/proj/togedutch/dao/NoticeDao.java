/*package com.proj.togedutch.dao;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;


@Repository
public class NoticeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public int createNotice(Notice notice) {
        String createKeywordQuery = "insert into Notice (notice_id, title, content, created_at, updated_at, status) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createNoticeParams = new Object[]{notice.notice_id(), notice.title(), notice.content(),
                notice.created_at(), notice.updated_at(), notice.status()};
        this.jdbcTemplate.update(createKeywordQuery, createNoticeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }




}
*/