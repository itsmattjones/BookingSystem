<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>TicketIT - Admin Dashboard Create</title>
        <meta name="description" content="The creation page of the Administration panel.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/adminDashboardCreateStyle.css" />
    </head>

    <body>
        <!-- The header section of the page -->
        <div id="header" class="pageHeader">
            <!-- Displays the website logo.-->
            <img class="websiteLogo" src="images/logo.png" alt="TicketIT Logo">

            <!-- Displays login/logout and dashboard buttons for users -->
            <div class="headerUserAccount">
                <c:if test="${!cookie.containsKey('memberId')}">
                    <form action="login" method="GET" style="display:inline-block">
                        <table><tr><th><button type="submit" id="userLogin" style="float: right;">Login</button></th></tr></table>
                    </form>
                </c:if>
                <c:if test="${cookie.containsKey('memberId')}">
                    <form action="logout" method="POST" style="display:inline-block">
                        <table><tr><th><button type="submit" id="userLogout" style="float: right;">Logout</button></th></tr></table>
                    </form>
                    <form action="admin" method="GET" style="display:inline-block">
                        <table><tr><th><button type="submit" id="userDashboard" style="float: right;">Dashboard</button></th></tr></table>
                    </form>
                </c:if>
            </div>
        </div>

        <div id="content" class="pageContent">
            <h1>Create a new event</h1><br>

            <!-- Form to create a new event. -->
            <form class="styledForm" action="adminCreate" method="POST">
                <table class="styledTable">
                    <tr>
                        <th><h2>Event</h2></th>
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

        <!-- Footer at the bottom of the page -->
        <div id="footer" class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>

