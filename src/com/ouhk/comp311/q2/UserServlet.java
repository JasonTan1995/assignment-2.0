package com.ouhk.comp311.q2;

import com.ouhk.comp311.q1.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name").trim();
        String password = request.getParameter("password").trim();

        if ("".equals(name) || name == null) {
            request.setAttribute("error", true);
        }

        if ("".equals(name) || name == null) {
            request.setAttribute("error", true);
        }

        boolean isCorrect = Users.checkUser(name, password);

        if (isCorrect) {
            List<String> allUser = Users.getAllUser();
            request.setAttribute("userList", allUser);
        } else {
            request.setAttribute("error", true);
        }
        request.getRequestDispatcher("users.jsp").forward(request, response);


    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
