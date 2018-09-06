package com.isalike.hkstockcollect.dao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class MariaDbMasterFactory {
    @Autowired
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    public Connection getCrm2Conn() {
        return DataSourceUtils.getConnection(masterDataSource);
    }

    public DataSource getDataSource() {
        return masterDataSource;
    }

    public Connection getConn() {
        try {
            return masterDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
