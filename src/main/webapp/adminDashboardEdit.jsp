<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Booking System - TicketIT</title>
        <meta name="description" content="A TicketIT Booking System, book your tickets here.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/adminDashboardEditStyle.css" />
    </head>

    <body>
        <div class="pageHeader">
            <img class="websiteLogo" src="images/logo.png" alt="TicketIT Logo">
            <div class="headerUserAccount">
                <c:if test="${!cookie.containsKey('memberId')}">
                    <form action="login" method="GET" style="display:inline-block">
                        <table><tr><th><button type="submit" style="float: right;">Login</button></th></tr></table>
                    </form>
                </c:if>
                <c:if test="${cookie.containsKey('memberId')}">
                    <form action="logout" method="POST" style="display:inline-block">
                        <table><tr><th><button type="submit" style="float: right;">Logout</button></th></tr></table>
                    </form>
                    <form action="admin" method="GET" style="display:inline-block">
                        <table><tr><th><button type="submit" style="float: right;">Dashboard</button></th></tr></table>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="pageContent">
            <h1>Edit event</h1><br>
            <h2>Event Details</h2>
            <!-- Editing of details of the event. -->
            <form class="editEventForm" action="adminEdit" method="POST">
                <input type="hidden" name="action" value="editEvent">
                <input type="hidden" name="eventId" value="${eventId}">
                <table class="editEventTable">
                    <tr>
                        <td><input type="text" name="eventTitle" placeholder="Event Title"></td>
                        <td><input type="date" name="eventDate" placeholder="Event Date"></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="eventDesc" placeholder="Event Description"></td>
                        <td><input type="time" name="eventTime" placeholder="Event Time"></td>
                    </tr>
                    <tr>
                        <td> </td>
                        <td><button type="submit" style="text-align: center">Save</button></td>
                    </tr>
                </table>
            </form>
            <br></br>
            <h2>Edit Event Tickets</h2>
            <!-- Create a new ticket for the event -->
            <form class="informationForm" action="adminEdit" method="POST">
                <input type="hidden" name="action" value="createTicket">
                <input type="hidden" name="eventId" value="${eventId}">
                <table class="informationTable">
                    <tr>
                        <td>Ticket Name</td>
                        <td>Ticket Price</td>
                        <td>Number Available</td>
                        <td> </td>
                    </tr>
                    <tr>
                        <td><input type="text" name="ticketName" placeholder="Name" required></td>
                        <td><input type="number" name="ticketPrice" placeholder="Price" min="1.00" value="1.00" step="0.01" required></td>
                        <td><input type="number" name="ticketAvailable" placeholder="Number Available" min="1"  value="1" required></td>
                        <td><button type="submit" style="text-align: center">Add</button></td>
                    </tr>
                </table>
            </form>
            <!-- List tickets for the event with option to delete. -->
            <c:forEach items="${eventTickets}" var="ticket">
                <form class="informationForm" action="adminEdit" method="POST">
                    <input type="hidden" name="action" value="deleteTicket">
                    <input type="hidden" name="eventId" value="${eventId}">
                    <input type="hidden" name="ticketId" value="${ticket.getId()}">
                    <table class="informationTable">
                        <tr>
                            <td><input type="text" name="ticketName" value="${ticket.getName()}" readonly="readonly"></td>
                            <td><input type="number" name="ticketPrice" value="${ticket.getPrice()}" readonly="readonly"></td>
                            <td><input type="number" name="ticketAvailable" value="${ticket.getNumberAvailable()}" readonly="readonly"></td>
                            <td><button type="submit" style="text-align: center">Delete</button></td>
                        </tr>
                    </table>
                </form>
            </c:forEach>

        </div>

        <div class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>

