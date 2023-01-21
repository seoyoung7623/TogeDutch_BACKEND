package com.proj.togedutch.dao;
import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.entity.Application;
import com.proj.togedutch.entity.Keyword;
import com.proj.togedutch.entity.Notice;
import com.proj.togedutch.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;


@Repository
public class NoticeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 공지사항 생성
    @Transactional(rollbackFor = Exception.class)
    public int createNotice(Notice notice) {
        String createNoticeQuery
                = "insert into Notice (title, content) " +
                "VALUES (?, ?)";
        Object[] createNoticeParams = new Object[]{notice.getTitle(), notice.getContent()};
        this.jdbcTemplate.update(createNoticeQuery, createNoticeParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // notice_id 반환
    }

    // 특정 공지사항 조회
    public Notice getNoticeById(int noticeIdx){
        String getNoticeQuery = "select * from Notice where notice_id = ?";
        return this.jdbcTemplate.queryForObject(getNoticeQuery,
                (rs, rowNum) -> new Notice(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ), noticeIdx);
    }

    // 전체 공지사항 조회 (최신순)
    public List<Notice> getAllNotices(String sort){
        String getNoticeQuery = "select * from Notice order by created_at desc;";
        return this.jdbcTemplate.query(getNoticeQuery,
                (rs, rowNum) -> new Notice(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                ));
    }

    // 공지사항 삭제
    @Transactional(rollbackFor = Exception.class)
    public int deleteNotice(int noticeIdx) {
        String deleteNoticeQuery
                = "delete from Notice where notice_id = ?";
        Object[] deleteNoticeParams = new Object[]{noticeIdx};
        return this.jdbcTemplate.update(deleteNoticeQuery, deleteNoticeParams);
    }

    // 공지사항 수정
    @Transactional(rollbackFor = Exception.class)
    public Notice modifyNotice(int noticeIdx, Notice notice) throws BaseException {
        String modifyPostQuery = "update Notice " +
                "set title = ?, content = ?, updated_at = ?" +
                "where notice_id = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

        Object[] modifyPostParams = new Object[]{notice.getTitle(), notice.getContent(), sdf.format(timeStamp),noticeIdx};

        if (this.jdbcTemplate.update(modifyPostQuery, modifyPostParams) == 1)
            return getNoticeById(noticeIdx);
        else {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
