<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Booking System - TicketIT</title>
        <meta name="description" content="A TicketIT Booking System, book your tickets here.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/adminDashboardStyle.css" />
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
            <h1>Events</h1>
            <form class="styledForm" action="adminCreate" method="GET">
                <button id="createEventButton" type="submit">Create New</button>
            </form>

            <!-- List events and have option to edit. -->
            <c:forEach items="${eventList}" var="event">
                <div>
                    <table class="styledTable" style="width: 80%;">
                        <tr>
                            <!-- List the event name -->
                            <td><span>${event.getTitle()}</Span></td>

                            <!-- Option to edit the event -->
                            <td style="width: 90px">
                                <form class="styledForm" action="adminEdit" method="GET">
                                    <input type="hidden" name="eventId" value="${event.getId()}">
                                    <button id="editEventButton" type="submit">Edit</button>
                                </form>
                            </td>

                            <!-- Option to delete the event -->
                            <td style="width: 90px;">
                                <form class="styledForm" action="admin" method="POST">
                                    <input type="hidden" name="eventId" value="${event.getId()}">
                                    <input type="hidden" name="action" value="deleteEvent">
                                    <button id="deleteEventButton" type="submit">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </table>
                </div>
            </c:forEach>
        </div>

        <div class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>

