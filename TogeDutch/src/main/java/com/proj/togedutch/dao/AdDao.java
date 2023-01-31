package com.proj.togedutch.dao;

import com.proj.togedutch.entity.Advertisement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AdDao {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(rollbackFor = Exception.class)
    public int createAd(Advertisement ad, int userIdx, String fileUrl, String tid) {
        String createAdQuery
                = "insert into Advertisement (store, information, main_menu, delivery_tips, location, request, User_user_id, image, tid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createAdParams = new Object[]{ad.getStore(), ad.getInformation(), ad.getMainMenu(), ad.getDeliveryTips(), ad.getLocation(), ad.getRequest(), userIdx, fileUrl, tid};
        this.jdbcTemplate.update(createAdQuery, createAdParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // post_id 반환
    }

    public Advertisement getAdById(int adIdx){
        String getAdQuery = "select * from Advertisement where ad_id = ?";
        return this.jdbcTemplate.queryForObject(getAdQuery,
                (rs, rowNum) -> new Advertisement(
                        rs.getInt("ad_id"),
                        rs.getString("store"),
                        rs.getString("information"),
                        rs.getString("main_menu"),
                        rs.getInt("delivery_tips"),
                        rs.getString("location"),
                        rs.getString("request"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("status"),
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getString("tid")
                ), adIdx);
    }

    public List<Advertisement> getAdsByUserId(int userIdx) {
        String getAdsQuery = "select * from Advertisement where User_user_id = ?";
        return this.jdbcTemplate.query(getAdsQuery,
                (rs, rowNum) -> new Advertisement(
                        rs.getInt("ad_id"),
                        rs.getString("store"),
                        rs.getString("information"),
                        rs.getString("main_menu"),
                        rs.getInt("delivery_tips"),
                        rs.getString("location"),
                        rs.getString("request"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("status"),
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getString("tid")
                ), userIdx);
    }

    public Advertisement getRandomAd() {
        String getRandAd = "Select * From Advertisement Order by rand() Limit 1";
        return this.jdbcTemplate.queryForObject(getRandAd,
                (rs, rowNum) -> new Advertisement(
                        rs.getInt("ad_id"),
                        rs.getString("store"),
                        rs.getString("information"),
                        rs.getString("main_menu"),
                        rs.getInt("delivery_tips"),
                        rs.getString("location"),
                        rs.getString("request"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at"),
                        rs.getString("status"),
                        rs.getInt("User_user_id"),
                        rs.getString("image"),
                        rs.getString("tid")
                ));
    }

    public void deleteAd(String tid) {
        String deleteAdQuery = "delete from Advertisement where tid = ?";
        Object[] deleteAdParams = new Object[]{tid};
        this.jdbcTemplate.update(deleteAdQuery, deleteAdParams);
    }
}
