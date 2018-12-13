package com.TicketIT.UnitTests;

import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class VisualElemenetsTests {

    @Before
    public void setup() {
        setBaseUrl("http://localhost:8080");
    }

    @Test
    public void testHomePageLayout(){
        beginAt("home");

        // General
        assertTitleEquals("Booking System - TicketIT");

        // Header
        assertElementPresent("header");
        assertImagePresent("images/logo.png", "TicketIT Logo");
        assertButtonPresent("userLogin");

        // Content
        assertElementPresent("content");

        // Footer
        assertElementPresent("footer");
        assertTextInElement("footer", "Copyright 2018");
    }

}