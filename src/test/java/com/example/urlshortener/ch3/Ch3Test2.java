package com.example.urlshortener.ch3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

// 3.2
@SpringBootTest
class Ch3Test2 {

    @Autowired
    private DataSource datasource;

    // Datasource 확인
    @Test
    public void giveDatasourceAvailableWhenAccessDetailsThenExpectDetails() throws SQLException {
        assertThat(datasource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
        assertThat(datasource.getConnection().getMetaData().getDatabaseProductName()).isEqualTo("H2");
    }

    // sql.init.data-locations 가 data.sql로 설정되어야 함
    @Test
    public void whenCountAllCoursesThenExpectFiveCourses() throws SQLException {
        ResultSet rs = null;
        int noOfShortenedUrls = 0;

        try(PreparedStatement ps = datasource.getConnection().prepareStatement("SELECT COUNT(1) FROM shortened_url")) {
            rs = ps.executeQuery();
            if(rs.next()) {
                noOfShortenedUrls = rs.getInt(1);
            }
            assertThat(noOfShortenedUrls).isEqualTo(5);
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
    }
}
