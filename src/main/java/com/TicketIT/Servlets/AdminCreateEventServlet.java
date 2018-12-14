package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Event;
import com.TicketIT.Model.Ticket;
import com.TicketIT.Utils.AdminUtils;
import com.mongodb.MongoClient;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AdminCreateEventServlet extends HttpServlet {

    /**
     * doPost function for the admin creation page servlet.
     * This handler will take event parameters and create a new event.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies())) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // Create new Event Object
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        Event event = eventDAO.CreateEvent(new Event());
        event.setTitle(request.getParameter("eventTitle"));
        event.setDescription(request.getParameter("eventDesc"));
        event.setDate(request.getParameter("eventDate"));
        event.setTime(request.getParameter("eventTime"));

        // Create new Ticket Object.
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        Ticket ticket = ticketDAO.CreateTicket(new Ticket());
        ticket.setName(request.getParameter("ticketName"));
        ticket.setPrice(Double.parseDouble(request.getParameter("ticketPrice")));
        ticket.setNumberAvailable(Integer.parseInt(request.getParameter("ticketAvailable")));
        ticket.setEventId(event.getId());

        eventDAO.UpdateEvent(event);
        ticketDAO.UpdateTicket(ticket);

        // Redirect back to admin dashboard.
        response.sendRedirect(request.getContextPath() + "/admin");
    }

    /**
     * doGet() function for the admin creation page servlet.
     * This handler will show the user the page, if they're allowed to see it.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies())) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        request.getRequestDispatcher("/adminDashboardCreate.jsp").forward(request, response);
    }
}
