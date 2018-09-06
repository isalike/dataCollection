package com.isalike.hkstockcollect.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class StockDAO {
    /*@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public HashMap<String,String> selectByUsernameAndPassword(Connection conn,String username, String password,String domain) throws SQLException {
        HashMap<String,String> aMap = new HashMap<String,String>();
        String sql= "SELECT * FROM user WHERE username = ? AND password = ? AND domain LIKE ?;";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, username);
        pStmt.setString(2, password);
        pStmt.setString(3, "%%"+domain(domain)+"%%");
        ResultSet rs = pStmt.executeQuery();
        if(rs.next()) {
            aMap.put("rowId", rs.getLong("rowId")+"");
            aMap.put("username", rs.getString("username"));
            aMap.put("password", rs.getString("password"));
            aMap.put("role", rs.getString("role"));
            aMap.put("uuidSession", rs.getString("uuidSession"));
            aMap.put("domain", rs.getString("domain"));
        }
        rs.close();

        return aMap;
    }
*/
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int insertDaily(Connection conn, String symbol, String recordDt, String closeValue, String lastValue, String dayHighValue, String dayLowValue) throws SQLException {
        String sql= "INSERT INTO daily_record(symbol,recordDt,closeValue,lastValue,dayHighValue,dayLowValue) VALUES (?,?,?,?,?,?) ;";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, symbol);
        pStmt.setString(2, recordDt);
        pStmt.setString(3, closeValue);
        pStmt.setString(4, lastValue);
        pStmt.setString(5, dayHighValue);
        pStmt.setString(6, dayLowValue);
        int result = pStmt.executeUpdate();

        return result;
    }
}
