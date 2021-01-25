<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Users</title>
</head>
<body>
<h1>List Users</h1>
<%
    Object error = request.getAttribute("error");
    if (Objects.isNull(error)) {
        List<String> userList = (List<String>) request.getAttribute("userList");
        out.println("<ul>");
        for (String name : userList) {
            out.println("<li>" + name + "</li>");
        }
        out.println("</ul>");
    } else {
        boolean isError = (boolean) error;
        if (isError) {
            out.println("Error: invalid name or password");
        }
    }
%>
</body>
</html>
