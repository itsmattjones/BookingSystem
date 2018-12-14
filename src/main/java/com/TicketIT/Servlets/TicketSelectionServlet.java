package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Ticket;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketSelectionServlet extends HttpServlet {

    /**
     * doPost function for the ticket selection page servlet.
     * This handler will display the ticket selection page customized
     * for the given event.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Ticket> tickets = ticketDAO.GetAllTickets();

        // Get all the tickets for the event.
        ArrayList<Ticket> eventTickets = new ArrayList<>();
        for(Ticket ticket : tickets){
            if(ticket.getEventId().equals(request.getParameter("eventId")))
                eventTickets.add(ticket);
        }

        request.setAttribute("chosenEvent", eventDAO.GetEventById(request.getParameter("eventId")));
        request.setAttribute("eventTicketsList", eventTickets);
        request.getRequestDispatcher("/ticketSelection.jsp").forward(request, response);
    }

    /**
     * doGet function for the ticket selection page servlet.
     * This handler will redirect the user to the home page, as it
     * requires an eventId.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath());
    }
}
