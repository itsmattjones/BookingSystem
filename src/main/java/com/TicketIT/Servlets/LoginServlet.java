package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import com.TicketIT.Model.Member;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    /**
     * doPost function for the login page servlet.
     * This handler will process the login form for when the user
     * enters their details.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate user login.
        if(memberDAO.DoesMemberExist(request.getParameter("userEmail").toLowerCase())) {
            Member member = memberDAO.GetMemberByEmail(request.getParameter("userEmail"));
            if(member.getPassword().equals(request.getParameter("userPassword"))){
                // Create cookie if login successful.
                Cookie cookie = new Cookie("memberId", member.getId());
                cookie.setMaxAge(900);
                response.addCookie(cookie);
                response.sendRedirect(request.getContextPath());
                return;
            }
        }

        // Alert if unsuccessful.
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('User or password incorrect');");
        response.getWriter().println("location='login.jsp';");
        response.getWriter().println("</script>");
    }

    /**
     * doGet function for the login page servlet.
     * This handler will show the user the login page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
