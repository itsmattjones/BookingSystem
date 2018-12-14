package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBBookingDAO;
import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBInvoiceDAO;
import com.TicketIT.DataAccessObject.MongoDBTicketDAO;
import com.TicketIT.Model.Booking;
import com.TicketIT.Model.Ticket;
import com.mongodb.MongoClient;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    /**
     * doPost function for the home page servlet.
     * This handler will cancel a booking if a bookingId is provided, and then
     * show the user the home page.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get all events from database.
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);

        // If a bookingId is provided with POST request, cancel it.
        if(request.getParameter("bookingId") != null){
            // Get required DAOs
            MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
            MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
            MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);

            // Make the tickets within the booking available.
            Booking booking = bookingDAO.GetBookingById(request.getParameter("bookingId"));
            for(String ticketId : booking.getTickets()){
                Ticket ticket = ticketDAO.GetTicketById(ticketId);
                ticket.setNumberAvailable(ticket.getNumberAvailable() + 1);
                ticketDAO.UpdateTicket(ticket);
            }

            // Delete the booking & invoice.
            invoiceDAO.DeleteInvoice(invoiceDAO.GetInvoiceById(bookingDAO.GetBookingById(request.getParameter("bookingId")).getInvoiceId()));
            bookingDAO.DeleteBooking(bookingDAO.GetBookingById(request.getParameter("bookingId")));
        }

        request.setAttribute("eventList", eventDAO.GetAllEvents());
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    /**
     * doGet function for the home page servlet.
     * This handler will show the user the home page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get all events from database.
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);

        request.setAttribute("eventList", eventDAO.GetAllEvents());
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
