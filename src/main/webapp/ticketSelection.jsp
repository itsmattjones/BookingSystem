<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Booking System - TicketIT</title>
        <meta name="description" content="A TicketIT Booking System, book your tickets here.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/ticketSelectionStyle.css" />
    </head>

    <body>
        <div id="header" class="pageHeader">
            <img class="websiteLogo" src="images/logo.png" alt="TicketIT Logo">
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
            <!-- Display event summary -->
            <div class="eventSummary" style="background-image: url(${chosenEvent.getImageBackground()})"/>
                <div id="titleAndDescription" style="width: 100%">
                    <p><b>${chosenEvent.getTitle()}</b><br>${chosenEvent.getDescription()}</p>
                </div>
            </div>
            <br>

            <!-- Display tickets available for event and allow user selection. -->
            <h1>Available Tickets</h1>
            <form class="styledForm" action="checkout" method="GET">
                <table class="styledTable">
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Number Available</th>
                        <th>Amount To Buy</th>
                    </tr>
                    <c:forEach items="${eventTicketsList}" var="ticket">
                        <tr>
                            <td>${ticket.getName()}</td>
                            <td>${ticket.getPrice()}</td>
                            <td>${ticket.getNumberAvailable()}</td>
                            <td><input type="number" name="${ticket.getId()}" value="0" min="0" max="${ticket.getNumberAvailable()}"></td>
                        </tr>
                    </c:forEach>
                </table>

                <!-- Add hidden ids for use on the checkout page. -->
                <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                <c:if test="${!cookie.containsKey('memberId')}">
                    <input type="hidden" name="memberId" value="0">
                </c:if>
                <c:if test="${cookie.containsKey('memberId')}">
                    <input type="hidden" name="memberId" value="${cookie['memberId'].getValue()}">
                </c:if>

                <br><button type="submit" style="float: right;">Checkout</button>
            </form><br>

            <!-- Cancel  button -->
            <form class="styledForm" action="home" method="POST">
                 <button type="submit" style="margin-top: -30px">Cancel</button>
            </form>
        </div>

        <div id="footer" class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>

