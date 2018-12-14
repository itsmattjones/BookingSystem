package com.TicketIT.UnitTests;

import com.TicketIT.DataAccessObject.*;
import com.TicketIT.Model.*;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.*;
import org.junit.runner.Description;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

/**
* Tests to cover the normal customer
* website functionality.
*/
public class CustomerFunctionalityTests {

    private UnitTestHelper testHelper;

    /**
     * Setup the testing environment.
     */
    @Before
    public void setup() {
        testHelper = new UnitTestHelper();
        setBaseUrl("http://localhost:8080");
        testHelper.CreateTestEventAndTicket();
    }

    /**
     * Remove all objects created
     * during the test.
     */
    @After
    public void shutdown() {
        testHelper.RemoveTestData();
    }

    /**
     * Tests that the homepage header displays
     * the correct information.
     */
    @Test
    public void TestHomePageHeader(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");
    }

    /**
     * Tests that the homepage footer displays
     * the correct information.
     */
    @Test
    public void TestHomePageFooter(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

    /**
     * Tests that the homepage content contains
     * the content container.
     */
    @Test
    public void TestHomePageContent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("content");
    }

    /**
     * Tests that event summaries are displayed
     * on the home page.
     */
    @Test
    public void TestHomePageDisplayEventSummary(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertElementPresent("eventSummary");
        assertTextPresent(testHelper.TEST_EVENT_NAME);
        assertTextPresent("Test event for Unit Tests.");
        assertButtonPresent("_UnitTestTestEvent");
    }

    /**
     * Tests that selecting the purchase button takes
     * you to the ticket selection page.
     */
    @Test
    public void TestSelectPurchaseEvent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        assertButtonPresent(testHelper.TEST_EVENT_NAME);
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");
    }

    /**
     * Tests that the header on the ticket selection
     * page contains the correct information.
     */
    @Test
    public void TestTicketSelectionHeader(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");

        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");

        // Cancel the booking.
        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the content section of the
     * ticket selection page contains the content
     * container.
     */
    @Test
    public void TestTicketSelectionContent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");

        assertElementPresent("content");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the Footer of the ticket
     * selection page contains the correct
     * information.
     */
    @Test
    public void TestTicketSelectionFooter(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");

        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the event summary of the
     * chosen event is displayed correctly on the
     * ticket selection page.
     */
    @Test
    public void TestTicketSelectionEventSummary(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");

        assertElementPresent("eventSummary");
        assertElementPresent("titleAndDescription");
        assertTextPresent(testHelper.TEST_EVENT_NAME);
        assertTextPresent("Test event for Unit Tests.");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the ticket selection page contains
     * a form displaying the ticket(s) for the event.
     */
    @Test
    public void TestTicketSelectionFormWithTestEvent(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");

        // Check form elements.
        assertFormPresent("ticketSelectionForm");
        setWorkingForm("ticketSelectionForm");
        assertTablePresent("ticketSelectionTable");
        assertTextInTable("ticketSelectionTable", "Name");
        assertTextInTable("ticketSelectionTable", "Price");
        assertTextInTable("ticketSelectionTable", "Number Available");
        assertTextInTable("ticketSelectionTable", "Amount To Buy");
        assertTextInTable("ticketSelectionTable", testHelper.TEST_TICKET_NAME);
        assertTextInTable("ticketSelectionTable", "10.99");
        assertTextInTable("ticketSelectionTable", "100");
        assertButtonPresent("checkoutButton");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that you can cancel a booking from the
     * ticket selection page.
     */
    @Test
    public void TestTicketSelectionCancelBooking(){
        beginAt("home");
        assertTitleEquals("TicketIT - Home");
        submit(testHelper.TEST_EVENT_NAME);
        assertTitleEquals("TicketIT - Ticket Selection");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the header on the checkout page
     * contains the relevant information.
     */
    @Test
    public void TestCheckoutPageHeader(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the content section of the
     * checkout page contains the content
     * container.
     */
    @Test
    public void TestCheckoutPageContent(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        assertElementPresent("content");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Checks that the footer of the checkout
     * page contains the relevant information.
     */
    @Test
    public void TestCheckoutPageFooter(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Checks that the event summary of the
     * chosen event is displayed correctly on the
     * checkout page.
     */
    @Test
    public void TestCheckoutPageEventSummary(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        assertElementPresent("eventSummary");
        assertElementPresent("titleAndDescription");
        assertTextPresent(testHelper.TEST_EVENT_NAME);
        assertTextPresent("Test event for Unit Tests.");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that the checkout page contains the
     * customer details input form, and has the
     * correct fields.
     */
    @Test
    public void TestCheckoutPageDetailsForm(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        // Check the form elements.
        assertFormPresent("checkoutForm");
        setWorkingForm("checkoutForm");
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

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");
        assertTitleEquals("TicketIT - Home");
    }

    /**
     * Tests that you can complete a booking by
     * selecting the finish and pay button on the
     * checkout page.
     */
    @Test
    public void TestCheckoutPageFinishAndPay(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        // Fill in the form.
        setTextField("name", testHelper.TEST_CUSTOMER_NAME);
        setTextField("email", "test@test.com");
        setTextField("telephone", "0123456789");
        setTextField("addressLine1", "somewhere");
        setTextField("cardHolder", "Test User");
        setTextField("addressLine2", "Somewhere");
        setTextField("cardNumber", "0123456789");
        setTextField("addressLine3", "Somewhere");
        setTextField("addressCity", "Somewhere");
        setTextField("addressCountry", "Somewhere");
        setTextField("cardExpiry", "01/01");

        submit("FinishAndPay");

        assertTitleEquals("TicketIT - Purchase");
        assertTextPresent("Thank You");
        assertTextPresent("Your booking has been successful. You will get an email confirmation shortly.");
    }

    /**
     * Tests that you can cancel a booking
     * from the checkout page.
     */
    @Test
    public void TestCheckoutPageCancelBooking(){
        beginAt("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=0");
        assertTitleEquals("TicketIT - Checkout");

        assertFormPresent("cancelForm");
        setWorkingForm("cancelForm");
        assertButtonPresentWithText("Cancel");
        submit("cancelBooking");

        assertTitleEquals("TicketIT - Home");
    }
}
