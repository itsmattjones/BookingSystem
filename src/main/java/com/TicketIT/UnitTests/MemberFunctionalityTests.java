package com.TicketIT.UnitTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

/**
 * Tests to cover the member
 * website functionality.
 */
public class MemberFunctionalityTests {

    private UnitTestHelper testHelper;

    /**
     * Setup the testing environment.
     */
    @Before
    public void setup() {
        testHelper = new UnitTestHelper();
        setBaseUrl("http://localhost:8080");
        testHelper.CreateTestEventAndTicket();
        testHelper.CreateTestMemberAndCard();
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
     * Tests the header on the login page
     * contains relevant information.
     */
    @Test
    public void TestLoginPageHeader(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");
    }

    /**
     * Test the content on the login page
     * contains relevant information.
     */
    @Test
    public void TestLoginPageContent(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");
        assertElementPresent("content");
    }

    /**
     * Tests the footer on the login page
     * contains relevant information.
     */
    @Test
    public void TestLoginPageFooter(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

    /**
     * Tests the login form exists and contains
     * the relevant elements.
     */
    @Test
    public void TestLoginPageLoginForm(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertTablePresent("loginTable");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        assertButtonPresent("loginButton");
    }

    /**
     * Tests that user login is validated, and
     * disallows incorrect user and password entries.
     */
    @Test
    public void TestLoginPageIncorrectUserPass(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        setTextField("userEmail", "incorrect@email.com");
        setTextField("userPassword", "incorrectPass");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTextPresent("User or password incorrect");
    }

    /**
     * Tests that user login is validated, and
     * disallows incorrect passwords.
     */
    @Test
    public void TestLoginPageIncorrectPass(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        setTextField("userEmail", "unittest@test.com");
        setTextField("userPassword", "incorrectPass");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTextPresent("User or password incorrect");
    }

    /**
     * Tests that user login is validated, and
     * disallows incorrect user names.
     */
    @Test
    public void TestLoginPageIncorrectUser(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        setTextField("userEmail", "usernot@found.com");
        setTextField("userPassword", "password");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTextPresent("User or password incorrect");
    }

    /**
     * Tests that the user is able to login to an
     * existing account.
     */
    @Test
    public void TestLoginPageSuccessfulLogin(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        setTextField("userEmail", "unittest@test.com");
        setTextField("userPassword", "password");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTitleEquals("TicketIT - Home");
        assertButtonPresent("userLogout");
        assertButtonPresent("userDashboard");
    }

    /**
     * Tests the register form exists on the home
     * page and contains the correct elements.
     */
    @Test
    public void TestLoginPageRegisterForm(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("registerForm");
        setWorkingForm("registerForm");
        assertTablePresent("registerTable");
        assertButtonPresent("registerButton");
    }

    /**
     * Tests the register button takes you to
     * the registration page.
     */
    @Test
    public void TestLoginPageRegisterButton(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("registerForm");
        setWorkingForm("registerForm");
        assertTablePresent("registerTable");
        assertButtonPresent("registerButton");
        submit("registerButton");

        assertTitleEquals("TicketIT - Register");
    }

    /**
     * Tests the header on the registration page
     * contains the relevant information.
     */
    @Test
    public void TestRegisterPageHeader(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");
    }

    /**
     * Tests the content section on the registration
     * page contains the relevant elements.
     */
    @Test
    public void TestRegisterPageContent(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");
        assertElementPresent("content");
    }

    /**
     * Tests the footer on the registration page
     * contains the relevant information.
     */
    @Test
    public void TestRegisterPageFooter(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

    /**
     * Tests the registration page contains the
     * registration form with all elements.
     */
    @Test
    public void TestRegisterPageRegistrationForm(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");

        assertFormPresent("registrationForm");
        setWorkingForm("registrationForm");
        assertTablePresent("registrationTable");

        // Check form elements.
        assertFormElementPresent("name");
        assertFormElementPresent("email");
        assertFormElementPresent("telephone");
        assertFormElementPresent("password");
        assertFormElementPresent("addressLine1");
        assertFormElementPresent("addressLine2");
        assertFormElementPresent("addressLine3");
        assertFormElementPresent("addressCity");
        assertFormElementPresent("addressCounty");
        assertFormElementPresent("addressPostcode");
        assertFormElementPresent("cardType");
        assertFormElementPresent("cardHolder");
        assertFormElementPresent("cardNumber");
        assertFormElementPresent("cardSecurityCode");
        assertFormElementPresent("cardExpiry");
        assertButtonPresent("submitRegistration");
    }

    /**
     * Tests that you cannot register when missing
     * required fields of the registration form.
     */
    @Test
    public void TestRegisterPageRequiredFieldsMissed(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");

        assertFormPresent("registrationForm");
        setWorkingForm("registrationForm");
        assertTablePresent("registrationTable");

        assertFormElementPresent("name");
        assertFormElementPresent("email");
        assertFormElementPresent("telephone");
        assertFormElementPresent("password");
        assertFormElementPresent("addressLine1");
        assertFormElementPresent("addressLine2");
        assertFormElementPresent("addressLine3");
        assertFormElementPresent("addressCity");
        assertFormElementPresent("addressCounty");
        assertFormElementPresent("addressPostcode");
        assertFormElementPresent("cardType");
        assertFormElementPresent("cardHolder");
        assertFormElementPresent("cardNumber");
        assertFormElementPresent("cardSecurityCode");
        assertFormElementPresent("cardExpiry");

        setTextField("telephone", "0123456789");

        assertButtonPresent("submitRegistration");
        submit("submitRegistration");

        assertTitleEquals("TicketIT - Login");
    }

    /**
     * Tests that you can successfully register by
     * using the registration form.
     */
    @Test
    public void TestRegisterPageSuccessfulRegistration(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");

        assertFormPresent("registrationForm");
        setWorkingForm("registrationForm");
        assertTablePresent("registrationTable");

        assertFormElementPresent("name");
        assertFormElementPresent("email");
        assertFormElementPresent("telephone");
        assertFormElementPresent("password");
        assertFormElementPresent("addressLine1");
        assertFormElementPresent("addressLine2");
        assertFormElementPresent("addressLine3");
        assertFormElementPresent("addressCity");
        assertFormElementPresent("addressCounty");
        assertFormElementPresent("addressPostcode");
        assertFormElementPresent("cardType");
        assertFormElementPresent("cardHolder");
        assertFormElementPresent("cardNumber");
        assertFormElementPresent("cardSecurityCode");
        assertFormElementPresent("cardExpiry");

        // Fill in the registration form.
        setTextField("name", testHelper.TEST_MEMBER_NAME);
        setTextField("email", "register@user.com");
        setTextField("telephone", "0123456789");
        setTextField("password", "password");
        setTextField("addressLine1", "somewhere");
        setTextField("addressLine2", "somewhere");
        setTextField("addressLine3", "somewhere");
        setTextField("addressCity", "somewhere");
        setTextField("addressCounty", "somewhere");
        setTextField("addressPostcode", "somewhere");
        setTextField("cardHolder", testHelper.TEST_MEMBER_NAME);
        setTextField("cardNumber", "0132456457456");
        setTextField("cardSecurityCode", "3242");

        assertButtonPresent("submitRegistration");
        submit("submitRegistration");

        assertTitleEquals("TicketIT - Login");
    }

    /**
     * Tests that you can login after successfully
     * registering using the registration form.
     */
    @Test
    public void TestRegisterPageLoginNewUser(){
        beginAt("register");
        assertTitleEquals("TicketIT - Register");

        assertFormPresent("registrationForm");
        setWorkingForm("registrationForm");
        assertTablePresent("registrationTable");

        assertFormElementPresent("name");
        assertFormElementPresent("email");
        assertFormElementPresent("telephone");
        assertFormElementPresent("password");
        assertFormElementPresent("addressLine1");
        assertFormElementPresent("addressLine2");
        assertFormElementPresent("addressLine3");
        assertFormElementPresent("addressCity");
        assertFormElementPresent("addressCounty");
        assertFormElementPresent("addressPostcode");
        assertFormElementPresent("cardType");
        assertFormElementPresent("cardHolder");
        assertFormElementPresent("cardNumber");
        assertFormElementPresent("cardSecurityCode");
        assertFormElementPresent("cardExpiry");

        setTextField("name", testHelper.TEST_MEMBER_NAME);
        setTextField("email", "register@user.com");
        setTextField("telephone", "0123456789");
        setTextField("password", "password");
        setTextField("addressLine1", "somewhere");
        setTextField("addressLine2", "somewhere");
        setTextField("addressLine3", "somewhere");
        setTextField("addressCity", "somewhere");
        setTextField("addressCounty", "somewhere");
        setTextField("addressPostcode", "somewhere");
        setTextField("cardHolder", testHelper.TEST_MEMBER_NAME);
        setTextField("cardNumber", "0132456457456");
        setTextField("cardSecurityCode", "3242");
        setTextField("cardExpiry", "01/01");

        assertButtonPresent("submitRegistration");
        submit("submitRegistration");

        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        setTextField("userEmail", "register@user.com");
        setTextField("userPassword", "password");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTitleEquals("TicketIT - Home");
        assertButtonPresent("userLogout");
        assertButtonPresent("userDashboard");
    }

    /**
     * Tests that the logged in user buttons show
     * on the ticket selection page when logged in.
     */
    @Test
    public void TestTicketSelectionLoggedIn(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        setTextField("userEmail", "unittest@test.com");
        setTextField("userPassword", "password");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTitleEquals("TicketIT - Home");
        assertButtonPresent("userLogout");
        assertButtonPresent("userDashboard");

        assertButtonPresent(testHelper.TEST_EVENT_NAME);
        submit(testHelper.TEST_EVENT_NAME);

        assertTitleEquals("TicketIT - Ticket Selection");
        assertButtonPresent("userLogout");
        assertButtonPresent("userDashboard");
    }

    /**
     * Tests that fields auto-fill on the checkout
     * page when logged in.
     */
    @Test
    public void TestCheckoutLoggedInAutoFillForm(){
        beginAt("login");
        assertTitleEquals("TicketIT - Login");

        assertFormPresent("loginForm");
        setWorkingForm("loginForm");
        assertFormElementPresent("userEmail");
        assertFormElementPresent("userPassword");
        setTextField("userEmail", "unittest@test.com");
        setTextField("userPassword", "password");
        assertButtonPresent("loginButton");
        submit("loginButton");

        assertTitleEquals("TicketIT - Home");
        assertButtonPresent("userLogout");
        assertButtonPresent("userDashboard");

        gotoPage("checkout?" + testHelper.testObjects.get("ticket") + "=1&eventId="
                + testHelper.testObjects.get("event") + "&memberId=" + testHelper.testObjects.get("member"));

        assertTextFieldEquals("name", testHelper.TEST_MEMBER_NAME);
        assertTextFieldEquals("email", "unittest@test.com");
        assertTextFieldEquals("telephone", "0123456789");
        assertTextFieldEquals("addressLine1", "Somewhere");
        assertTextFieldEquals("addressLine2", "Somewhere");
        assertTextFieldEquals("addressLine3", "Somewhere");
        assertTextFieldEquals("addressCity", "Somewhere");
        assertTextFieldEquals("addressCounty", "Somewhere");
        assertTextFieldEquals("addressPostcode", "Somewhere");
        assertTextFieldEquals("cardHolder", testHelper.TEST_MEMBER_NAME);
        assertTextFieldEquals("cardNumber", "012345678234");
        assertTextFieldEquals("cardExpiry", "01/01");
    }
}
