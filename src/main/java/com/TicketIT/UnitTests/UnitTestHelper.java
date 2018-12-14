package com.TicketIT.UnitTests;

import com.TicketIT.DataAccessObject.*;
import com.TicketIT.Model.*;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnitTestHelper {

    // Connection Details for MongoDB.
    public final String MONGO_HOST = "localhost";
    public final Integer MONGO_PORT = 27017;

    // Naming for objects within unit tests.
    public final String TEST_EVENT_NAME = "_UnitTestTestEvent";
    public final String TEST_TICKET_NAME = "_UnitTestTicket";
    public final String TEST_CUSTOMER_NAME = "_UnitTestCustomer";
    public final String TEST_MEMBER_NAME = "_UnitTestMember";

    // objectIds of test objects (for reference within tests).
    public HashMap<String, String> testObjects = new HashMap<>();

    /**
     * Create Test Event and Ticket objects for tests.
     */
    public void CreateTestEventAndTicket() {
        try {
            MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);

            // Create Event and Ticket Object.
            MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
            Event event = eventDAO.CreateEvent(new Event());
            event.setTitle(TEST_EVENT_NAME);
            event.setDescription("Test event for Unit Tests.");
            event.setDate("01/01/2000");
            event.setTime("12:00");

            // Create Ticket Object.
            MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
            Ticket ticket = ticketDAO.CreateTicket(new Ticket());
            ticket.setName(TEST_TICKET_NAME);
            ticket.setPrice(10.99);
            ticket.setNumberAvailable(100);
            ticket.setEventId(event.getId());

            // Update objects in database.
            eventDAO.UpdateEvent(event);
            ticketDAO.UpdateTicket(ticket);

            // Add test objects to hashmap.
            testObjects.put("event", event.getId());
            testObjects.put("ticket", ticket.getId());

            mongo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create Test Member and Card objects for tests.
     */
    public void CreateTestMemberAndCard() {
        try {
            MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);

            // Create Card Object.
            MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);
            Card card = cardDAO.CreateCard(new Card());
            card.setNumber("012345678234");
            card.setType("VISA");
            card.setSecurityCode("123");
            card.setHolder(TEST_MEMBER_NAME);
            card.setExpiry("01/01");

            // Create member object.
            MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);
            Member member = memberDAO.CreateMember(new Member());
            member.setName(TEST_MEMBER_NAME);
            member.setEmail("unittest@test.com");
            member.setPassword("password");
            member.setTelephone("0123456789");
            List<String> addressList = new ArrayList<>();
            for(int i = 0; i < 6; i++)
                addressList.add("Somewhere");
            member.setAddress(addressList);
            member.setCardId(card.getId());

            // Update objects in database.
            cardDAO.UpdateCard(card);
            memberDAO.UpdateMember(member);

            // Add test objects to hashmap.
            testObjects.put("card", card.getId());
            testObjects.put("member", member.getId());

            mongo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function for deleting Unit Test objects in the database after
     * the unit tests have been executed.
     */
    public void RemoveTestData() {
        try {
            MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);

            // Create new instances of the required data access objects.
            MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
            MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
            MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);
            MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
            MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
            MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);
            MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

            // Delete Unit Test Ticket Objects.
            if(ticketDAO.GetAllTickets() != null && ticketDAO.GetAllTickets().size() > 1) {
                for (Ticket ticket : ticketDAO.GetAllTickets()) {
                    if (ticket.getName().equals(TEST_TICKET_NAME))
                        ticketDAO.DeleteTicket(ticket);
                }
            }

            // Delete Unit Test Event Objects.
            if(eventDAO.GetAllEvents() != null && eventDAO.GetAllEvents().size() > 1) {
                for (Event event : eventDAO.GetAllEvents()) {
                    if (event.getTitle().equals(TEST_EVENT_NAME))
                        eventDAO.DeleteEvent(event);
                }
            }

            // Delete Unit Test Member & Member Card Objects.
            if(memberDAO.GetAllMembers() != null && memberDAO.GetAllMembers().size() > 1) {
                for (Member member : memberDAO.GetAllMembers()) {
                    if (member.getName().equals(TEST_MEMBER_NAME)) {
                        for (Card card : cardDAO.GetAllCards()) {
                            if (card.getId().equals(member.getCardId()))
                                cardDAO.DeleteCard(card);
                        }
                        memberDAO.DeleteMember(member);
                    }
                }
            }

            // Delete Unit Test Customers, with their Bookings->Invoice->Card
            if(customerDAO.GetAllCustomers() != null && customerDAO.GetAllCustomers().size() > 0) {
                for (Customer customer : customerDAO.GetAllCustomers()) {
                    if (customer.getName().equals(TEST_CUSTOMER_NAME)) {
                        List<Booking> customerBookings = new ArrayList<>();

                        // Get the customers bookings
                        if (bookingDAO.GetAllBookings().size() > 0) {
                            for (Booking booking : bookingDAO.GetAllBookings()) {
                                if (booking.getCustomerId().equals(customer.getId()))
                                    customerBookings.add(booking);
                            }

                            // Delete booking and invoices with their linked cards.
                            for (Booking booking : customerBookings) {
                                Invoice invoice = invoiceDAO.GetInvoiceById(booking.getInvoiceId());
                                cardDAO.DeleteCard(cardDAO.GetCardById(invoice.getCardId()));
                                invoiceDAO.DeleteInvoice(invoice);
                                bookingDAO.DeleteBooking(booking);
                            }

                            // Delete the customer
                            customerDAO.DeleteCustomer(customer);
                        }
                    }
                }
            }

            mongo.close();
            testObjects.clear();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
