package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.*;
import com.TicketIT.Model.*;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
        MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);
        MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
        MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);

        Booking booking = bookingDAO.GetBookingById(request.getParameter("bookingId"));
        Invoice invoice = invoiceDAO.GetInvoiceById(booking.getInvoiceId());

        // Populate Customer object.
        Customer customer = customerDAO.CreateCustomer(new Customer());
        customer.setEmail(request.getParameter("email"));
        customer.setName(request.getParameter("name"));
        customer.setTelephone(request.getParameter("telephone"));
        List<String> customerAddress = new ArrayList<>();
        customerAddress.add(request.getParameter("addressLine1"));
        customerAddress.add(request.getParameter("addressLine2"));
        customerAddress.add(request.getParameter("addressLine3"));
        customerAddress.add(request.getParameter("addressCity"));
        customerAddress.add(request.getParameter("addressCounty"));
        customerAddress.add(request.getParameter("addressPostcode"));
        customer.setAddress(customerAddress);

        // Create card if it doesn't exist already.
        if(!cardDAO.DoesCardExist(request.getParameter("cardNumber"))) {
            Card card = cardDAO.CreateCard(new Card());
            card.setType(request.getParameter("cardType"));
            card.setHolder(request.getParameter("cardHolder"));
            card.setNumber(request.getParameter("cardNumber"));
            card.setExpiry(request.getParameter("cardExpiry"));
            card.setSecurityCode(request.getParameter("cardSecurityCode"));
            cardDAO.UpdateCard(card);
            invoice.setCardId(cardDAO.GetCard(card).getId());
        } else {
            invoice.setCardId(cardDAO.GetCardByNumber(request.getParameter("cardNumber")).getId());
        }

        // Update Booking and Invoice.
        booking.setCustomerId(customer.getId());
        booking.setSendTickets(Boolean.parseBoolean(request.getParameter("sendTickets")));
        invoice.setPaid(true);

        // Update objects in the database.
        customerDAO.UpdateCustomer(customer);
        bookingDAO.UpdateBooking(booking);
        invoiceDAO.UpdateInvoice(invoice);

        request.setAttribute("chosenEvent", eventDAO.GetEventById(request.getParameter("eventId")));
        request.getRequestDispatcher("/purchase.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // The request must be to book an event.
        if(request.getParameter("eventId") == null) {
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
        MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
        MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);
        MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);

        // Create new Booking & Invoice and store in database.
        Booking booking = bookingDAO.CreateBooking(new Booking());
        Invoice invoice = invoiceDAO.CreateInvoice(new Invoice());
        invoice.setAmount(0.0); // Set default amount.

        // Adds the selected ticket ids to a list.
        ArrayList<String> chosenTicketIds = new ArrayList<>();
        for(Ticket ticket : ticketDAO.GetAllTickets()){
            if(!ticket.getEventId().equals(request.getParameter("eventId")))
                continue;

            int amountToBuy = Integer.parseInt(request.getParameter(ticket.getId()));
            if(amountToBuy > 0){
                for(int i = 0; i < amountToBuy; i++){
                    chosenTicketIds.add(ticket.getId());
                    ticket.setNumberAvailable(ticket.getNumberAvailable() - 1);
                    invoice.setAmount(invoice.getAmount() + ticket.getPrice());
                }
                ticketDAO.UpdateTicket(ticket);
            }
        }

        // Set tickets and invoice ids for booking.
        booking.setTickets(chosenTicketIds);
        booking.setInvoiceId(invoice.getId());

        // Update Booking and Invoice in database.
        invoiceDAO.UpdateInvoice(invoice);
        bookingDAO.UpdateBooking(booking);

        // Variables to auto-fill the checkout form if the user's registered.
        if(!request.getParameter("memberId").equals("0")){
            request.setAttribute("member", memberDAO.GetMemberById(request.getParameter("memberId")));
            request.setAttribute("memberCard", cardDAO.GetCardById(memberDAO.GetMemberById(request.getParameter("memberId")).getCardId()));
        }

        request.setAttribute("booking", bookingDAO.GetBooking(booking));
        request.setAttribute("invoice", invoiceDAO.GetInvoice(invoice));
        request.setAttribute("chosenEvent", eventDAO.GetEventById(request.getParameter("eventId")));
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }
}
