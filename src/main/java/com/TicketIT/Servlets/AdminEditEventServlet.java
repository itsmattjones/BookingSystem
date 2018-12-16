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
import java.util.ArrayList;
import java.util.List;

public class AdminEditEventServlet extends HttpServlet {

    /**
     * doPost function for the admin edit event page servlet.
     * This handler will take the edits made to the event on the page, and
     * update the Database accordingly.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies())) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // An eventId must be supplied in order to edit it.
        if(!request.getParameterMap().containsKey("eventId")) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        if (request.getParameterMap().containsKey("action")) {
            if (request.getParameter("action").equals("editEvent")) {
                // Update the event in the database.
                MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
                Event event = eventDAO.GetEventById(request.getParameter("eventId"));
                event.setTitle(request.getParameter("eventTitle"));
                event.setDescription(request.getParameter("eventDesc"));
                event.setLocation(request.getParameter("eventLocation"));
                event.setDate(request.getParameter("eventDate"));
                event.setTime(request.getParameter("eventTime"));
                eventDAO.UpdateEvent(event);
            } else if (request.getParameter("action").equals("createTicket")) {
                // Create a new ticket in database.
                Ticket ticket = ticketDAO.CreateTicket(new Ticket());
                ticket.setName(request.getParameter("ticketName"));
                ticket.setPrice(Double.parseDouble(request.getParameter("ticketPrice")));
                ticket.setNumberAvailable(Integer.parseInt(request.getParameter("ticketAvailable")));
                ticket.setEventId(request.getParameter("eventId"));
                ticketDAO.UpdateTicket(ticket);
            } else if (request.getParameter("action").equals("deleteTicket")) {
                // Delete the chosen ticket from database.
                Ticket ticket = ticketDAO.GetTicketById(request.getParameter("ticketId"));
                ticketDAO.DeleteTicket(ticket);
            }
        }

        // Retrieve a list of tickets for the event.
        List<Ticket> eventTickets = new ArrayList<>();
        for(Ticket ticket : ticketDAO.GetAllTickets()){
            if(ticket.getEventId().equals(request.getParameter("eventId")))
                eventTickets.add(ticket);
        }

        // After the action has been handled, return to edit page.
        request.setAttribute("eventId", request.getParameter("eventId"));
        request.setAttribute("eventTickets", eventTickets);
        request.getRequestDispatcher("/adminDashboardEdit.jsp").forward(request, response);
    }

    /**
     * doGet function for the admin edit event page servlet.
     * This handler will show the user the dashboard page, if they're allowed to see it.
     * customized for the given event for editing.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies())) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // If an eventId has not been supplied, it cannot be edited.
        if(!request.getParameterMap().containsKey("eventId")) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // Retrieve a list of tickets for the event.
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        List<Ticket> eventTickets = new ArrayList<>();
        for(Ticket ticket : ticketDAO.GetAllTickets()){
            if(ticket.getEventId().equals(request.getParameter("eventId")))
                eventTickets.add(ticket);
        }

        request.setAttribute("eventId", request.getParameter("eventId"));
        request.setAttribute("eventTickets", eventTickets);
        request.getRequestDispatcher("/adminDashboardEdit.jsp").forward(request, response);
    }
}
