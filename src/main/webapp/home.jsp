<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>TicketIT - Home</title>
        <meta name="description" content="A TicketIT Booking System, book your tickets here.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
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
            <!-- Dynamically add summary element for each event available -->
            <c:forEach items="${eventList}" var="event">
                <div id="eventSummary" class="eventSummary"/>
                    <!-- Displays event title and description -->
                    <div id="titleAndDescription" style="background-image: url(${event.getImageBackground()})">
                        <p><b>${event.getTitle()}</b><br>${event.getDescription()}</p>
                    </div>

                    <!-- Purchase a ticket for the given event-->
                    <div id="priceAndBuy">
                        <form action="ticketSelection" method="POST">
                            <input type="hidden" name="eventId" value="${event.getId()}">
                            <button name="${event.getTitle()}" id="${event.getTitle()}" type="submit">Purchase</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Footer at the bottom of the page -->
        <div id="footer" class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>
