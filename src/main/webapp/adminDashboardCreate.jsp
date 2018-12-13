<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Booking System - TicketIT</title>
        <meta name="description" content="A TicketIT Booking System, book your tickets here.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/adminDashboardCreateStyle.css" />
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
            <h1>Create a new event</h1><br>

            <!-- Form to create a new event. -->
            <form class="styledForm" action="adminCreate" method="POST">
                <table class="styledTable">
                    <tr>
                        <th><h2>Ticket</h2></th>
                        <th><h2>Default Ticket</h2></th>
                    </tr>
                    <tr>
                        <td><input type="text" name="eventTitle" placeholder="Event Title" required></td>
                        <td><input type="text" name="ticketName" placeholder="Ticket Name" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="eventDesc" placeholder="Event Description" required></td>
                        <td><input type="number" name="ticketPrice" placeholder="Ticket Price" value="1.00" min="1.00" step="0.01" required></td>
                    </tr>
                    <tr>
                        <td> </td>
                        <td><input type="number" name="ticketAvailable" placeholder="Number Available" value="1" min="1" required></td>
                    </tr>
                    <tr>
                        <td><input type="date" name="eventDate" placeholder="Event Date" required></td>
                    </tr>
                    <tr>
                        <td><input type="time" name="eventTime" placeholder="Event Time" required></td>
                        <td><button type="submit">Create</button></td>
                    </tr>
                </table>
            </form>
        </div>

        <div class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>

