package com.ouhk.comp311.q1;

import com.ouhk.comp311.util.DbUtil;
import com.ouhk.comp311.util.MD5Util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static com.ouhk.comp311.constant.UserConstant.*;

public class Users {

    public static void main(String[] args) throws SQLException, IOException {
        String action = args[0];
        String userName = args[1];
        String password = args[2];
        switch (action) {
            case ADD_USER:
                addUser(userName, password);
                break;
            case CHECK_USER:
                checkUser(userName, password);
                break;
            default:
                System.out.println("Error Action, Please Input Correct Parameter");
        }
    }

    public static boolean addUser(String userName, String password) {
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = DbUtil.getConnection();
            pstm = connection.prepareStatement(ADD_USER_SQL);
            pstm.setString(1, userName);
            pstm.setString(2, MD5Util.md5Encrypt(password));
            int i = pstm.executeUpdate();
            if (i > 0) {
                System.out.println("User " + userName + " added");
            } else {
                System.out.println("User " + userName + " add failed");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        DbUtil.close(connection, pstm, null);
        return false;
    }

    public static boolean checkUser(String userName, String password) {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        try {
            connection = DbUtil.getConnection();
            pstm = connection.prepareStatement(CHECK_USER_SQL);
            pstm.setString(1, userName);
            pstm.setString(2, MD5Util.md5Encrypt(password));
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    System.out.println("Authenticating user " + userName + ": YES");
                } else {
                    System.out.println("Authenticating user " + userName + ": NO");
                }
            }
            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(connection, pstm, resultSet);
        }

        return false;
    }

    public static List<String> getAllUser() {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        List<String> userNameList = new ArrayList<>();
        try {
            connection = DbUtil.getConnection();
            pstm = connection.prepareStatement(CHECK_USER_SQL);
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("Name");
                userNameList.add(userName);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(connection, pstm, resultSet);
        }

        return userNameList;
    }
}
