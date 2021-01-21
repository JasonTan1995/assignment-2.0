package com.ouhk.comp311.util;

import com.ouhk.comp311.q1.Users;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbUtil {

    public static Connection getConnection() throws IOException {
        Properties prop = new Properties();
        InputStream fis = Users.class.getResourceAsStream("database.properties");
        try {
            prop.load(fis);
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String url = prop.getProperty("jdbc.url");
            String userName = prop.getProperty("jdbc.username");
            String password = prop.getProperty("jdbc.password");
            return DriverManager.getConnection(url, userName, password);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void close(Connection con, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
