<%--
  Created by IntelliJ IDEA.
  User: Jayson
  Date: 2021/1/7
  Time: 10:22 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>List Users</title>
</head>
<body>
<h1>List Users</h1>
<form action="/assignment2/users" method="POST">
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" /></td>
        </tr>
    </table>
    <input type="submit" />
</form>
</body>
</html>
