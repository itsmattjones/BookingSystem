package com.TicketIT.UnitTests;

import com.TicketIT.DataAccessObject.*;
import com.TicketIT.Model.*;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.MongoClient;

import org.junit.*;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;

/*
* Tests that cover the normal customer
* website functionality.
*/
public class CustomerFunctionalityTests {

    // MongoDB Details
    private final String MONGO_HOST = "localhost";
    private final Integer MONGO_PORT = 27017;

    private final String TEST_EVENT_NAME = "_UnitTestTestEvent";
    private final String TEST_TICKET_NAME = "_UnitTestTicket";
    private final String TEST_CUSTOMER_NAME = "_UnitTestCustomer";
    private final String TEST_MEMBER_NAME = "_UnitTestMember";

    // Test objects created
    private HashMap<String, String> testObjects = new HashMap<>();

    @Before
    public void setup() {
        setBaseUrl("http://localhost:8080");
        addTestData();
    }

    @After
    public void shutdown() {
        removeTestData();
    }

    @Test
    public void TestHomePageHeader(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");
    }

    @Test
    public void TestHomePageFooter(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

    @Test
    public void TestHomePageContent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("content");
    }

    @Test
    public void TestHomePageDisplayEventSummary(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("eventSummary");
        assertTextPresent(TEST_EVENT_NAME);
        assertTextPresent("Test event for Unit Tests.");
        assertButtonPresent("_UnitTestTestEvent");
    }

    @Test
    public void TestSelectPurchaseEvent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertButtonPresent(TEST_EVENT_NAME);
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
    }

    @Test
    public void TestTicketSelectionHeader(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");
    }

    @Test
    public void TestTicketSelectionContent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
        assertElementPresent("content");
    }

    @Test
    public void TestTicketSelectionFooter(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

    @Test
    public void TestTicketSelectionEventSummary(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
        assertElementPresent("eventSummary");
        assertElementPresent("titleAndDescription");
        assertTextPresent(TEST_EVENT_NAME);
        assertTextPresent("Test event for Unit Tests.");
    }

    @Test
    public void TestTicketSelectionFormWithTestEvent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
        assertFormPresent("ticketSelectionForm");
        assertTablePresent("ticketSelectionTable");
        assertTextInTable("ticketSelectionTable", "Name");
        assertTextInTable("ticketSelectionTable", "Price");
        assertTextInTable("ticketSelectionTable", "Number Available");
        assertTextInTable("ticketSelectionTable", "Amount To Buy");
        assertTextInTable("ticketSelectionTable", TEST_TICKET_NAME);
        assertTextInTable("ticketSelectionTable", "10.99");
        assertTextInTable("ticketSelectionTable", "100");
        assertButtonPresent("checkoutButton");
    }

    @Test
    public void TestTicketSelectionCancelBooking(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    @Test
    public void TestCheckoutPageHeader(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");
    }

    @Test
    public void TestCheckoutPageContent(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");
        assertElementPresent("content");
    }

    @Test
    public void TestCheckoutPageFooter(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

    @Test
    public void TestCheckoutPageEventSummary(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");
        assertElementPresent("eventSummary");
        assertElementPresent("titleAndDescription");
        assertTextPresent(TEST_EVENT_NAME);
        assertTextPresent("Test event for Unit Tests.");
    }

    @Test
    public void TestCheckoutPageDetailsForm(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");
        assertFormPresent("checkoutForm");
        assertTablePresent("checkoutTable");
        assertFormElementPresent("name");
        assertFormElementPresent("email");
        assertFormElementPresent("telephone");
        assertFormElementPresent("cardType");
        assertFormElementPresent("addressLine1");
        assertFormElementPresent("cardHolder");
        assertFormElementPresent("addressLine2");
        assertFormElementPresent("cardNumber");
        assertFormElementPresent("addressLine3");
        assertFormElementPresent("cardSecurityCode");
        assertFormElementPresent("addressCity");
        assertFormElementPresent("sendTickets");
        assertFormElementPresent("addressCountry");
        assertFormElementPresent("addressPostcode");
        assertButtonPresentWithText("Finish & Pay");
    }

    @Test
    public void TestCheckoutPageFinishAndPay(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        setTextField("name", TEST_CUSTOMER_NAME);
        setTextField("email", "test@test.com");
        setTextField("telephone", "0123456789");
        setTextField("addressLine1", "somewhere");
        setTextField("cardHolder", "Test User");
        setTextField("addressLine2", "Somewhere");
        setTextField("cardNumber", "0123456789");
        setTextField("addressLine3", "Somewhere");
        setTextField("addressCity", "Somewhere");
        setTextField("addressCountry", "Somewhere");

        submit("FinishAndPay");

        assertTitleEquals("TicketIT - Purchase");
        assertTextPresent("Thank You");
        assertTextPresent("Your booking has been successful. You will get an email confirmation shortly.");
    }

    @Test
    public void TestCheckoutPageCancelBooking(){
        beginAt("checkout?" + testObjects.get("ticket") + "=1&eventId="
                + testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");
    }



    /*
    *  Test Helper Methods
    *  // TODO - NULL POINTER CHECKING!!!
    */

    private void addTestData() {
        try {
            createTestEventAndTicket();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Mega Function for delete Unit Test objects in MongoDB.
     */
    private void removeTestData() {
        try {
            MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
            MongoDBTicketDAO ticketDAO = new MongoDBTicketDAO(mongo);
            MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
            MongoDBCardDAO cardDAO = new MongoDBCardDAO(mongo);
            MongoDBCustomerDAO customerDAO = new MongoDBCustomerDAO(mongo);
            MongoDBBookingDAO bookingDAO = new MongoDBBookingDAO(mongo);
            MongoDBInvoiceDAO invoiceDAO = new MongoDBInvoiceDAO(mongo);
            MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

            // Delete Unit Test Ticket Objects.
            for(Ticket ticket : ticketDAO.GetAllTickets()){
                if(ticket.getName().equals(TEST_TICKET_NAME))
                    ticketDAO.DeleteTicket(ticket);
            }

            // Delete Unit Test Event Objects.
            for(Event event : eventDAO.GetAllEvents()){
                if(event.getTitle().equals(TEST_EVENT_NAME))
                    eventDAO.DeleteEvent(event);
            }

            // Delete Unit Test Member & Member Card Objects.
            for(Member member : memberDAO.GetAllMembers()){
                if(member.getName().equals(TEST_MEMBER_NAME)){
                    for(Card card : cardDAO.GetAllCards()) {
                        if (card.getId().equals(member.getCardId()))
                            cardDAO.DeleteCard(card);
                    }
                    memberDAO.DeleteMember(member);
                }
            }

            // Mega Delete Loop.
            // Delete Unit Test Customers, with their Bookings->Invoice->Card
            for(Customer customer : customerDAO.GetAllCustomers()){
                if(customer.getName().equals(TEST_CUSTOMER_NAME)){
                    for(Booking booking : bookingDAO.GetAllBookings()){
                        if(booking.getCustomerId().equals(customer.getId())) {
                            for(Invoice invoice : invoiceDAO.GetAllInvoices()){
                                if(invoice.getId().equals(booking.getInvoiceId())){
                                    for(Card card : cardDAO.GetAllCards()){
                                        if(invoice.getCardId().equals(card.getId()))
                                            cardDAO.DeleteCard(card);
                                    }
                                    invoiceDAO.DeleteInvoice(invoice);
                                }
                            }
                            bookingDAO.DeleteBooking(booking);
                        }
                    }
                    customerDAO.DeleteCustomer(customer);
                }
            }

            mongo.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create Test Event and Ticket objects for
     * this classes tests.
     */
    private void createTestEventAndTicket() {
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
}
