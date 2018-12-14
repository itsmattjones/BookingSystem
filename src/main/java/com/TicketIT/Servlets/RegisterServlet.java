package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.*;
import com.TicketIT.Model.*;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);
        MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);

        // Populate Member object.
        Member member = memberDAO.CreateMember(new Member());
        member.setEmail(request.getParameter("email").toLowerCase());
        member.setPassword(request.getParameter("password"));
        member.setName(request.getParameter("name"));
        member.setTelephone(request.getParameter("telephone"));
        List<String> memberAddress = new ArrayList<>();
        memberAddress.add(request.getParameter("addressLine1"));
        memberAddress.add(request.getParameter("addressLine2"));
        memberAddress.add(request.getParameter("addressLine3"));
        memberAddress.add(request.getParameter("addressCity"));
        memberAddress.add(request.getParameter("addressCounty"));
        memberAddress.add(request.getParameter("addressPostcode"));
        member.setAddress(memberAddress);

        // Create card object if it doesn't exist already.
        if(!cardDAO.DoesCardExist(request.getParameter("cardNumber"))) {
            Card card = cardDAO.CreateCard(new Card());
            card.setType(request.getParameter("cardType"));
            card.setHolder(request.getParameter("cardHolder"));
            card.setNumber(request.getParameter("cardNumber"));
            card.setSecurityCode(request.getParameter("cardSecurityCode"));
            cardDAO.UpdateCard(card);
            member.setCardId(cardDAO.GetCard(card).getId());
        } else {
            member.setCardId(cardDAO.GetCardByNumber(request.getParameter("cardNumber")).getId());
        }

        // First member of site is Admin.
        if(memberDAO.GetAllMembers().size() == 1)
            member.setIsAdmin(true);

        // Update member object in database.
        memberDAO.UpdateMember(member);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
