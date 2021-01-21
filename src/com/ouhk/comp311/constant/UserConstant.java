package com.ouhk.comp311.constant;

public class UserConstant {

    public static final String ADD_USER = "-add";
    public static final String CHECK_USER = "-check";
    public static final String ADD_USER_SQL = "INSERT INTO A2Users (Name, Password) VALUES (?, ?)";
    public static final String CHECK_USER_SQL = "SELECT COUNT(*) FROM A2Users WHERE Name = ? AND Password = ?";
    public static final String GET_ALL_USER_SQL = "SELECT Name FROM A2Users";
}
