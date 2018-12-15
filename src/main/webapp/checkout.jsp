<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>TicketIT - Checkout</title>
        <meta name="description" content="Booking checkout page.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/checkoutStyle.css" />
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
            <!-- Display event summary -->
            <div id="eventSummary" class="eventSummary" style="background-image: url(${chosenEvent.getImageBackground()})"/>
                <div id="titleAndDescription" style="width: 100%">
                    <p><b>${chosenEvent.getTitle()}</b><br>${chosenEvent.getDescription()}</p>
                </div>
            </div>
            <br>

            <!-- Payment Form -->
            <!-- If the customer is not logged in then display un-filled form -->
            <c:if test="${!cookie.containsKey('memberId')}">
                <form name="checkoutForm" class="styledForm" action="checkout" method="POST">
                    <input type="hidden" name="bookingId" value="${booking.getId()}">
                    <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                    <table name="checkoutTable" class="styledTable">
                        <tr>
                            <td><input type="text" name="name" placeholder="Full Name" required></td>
                            <td><input type="email" name="email" placeholder="Email" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="telephone" placeholder="Telephone"></td>
                            <td>
                                <select name="cardType">
                                    <option value="visaDebit">VISA Debit</option>
                                    <option value="visaCredit">VISA Credit</option>
                                    <option value="mastercard">Mastercard</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine1" placeholder="Address Line 1" required></td>
                            <td><input type="text" name="cardHolder" placeholder="Card Holder" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine2" placeholder="Address Line 2" required></td>
                            <td><input type="text" name="cardNumber" placeholder="Card Number" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine3" placeholder="Address Line 3" required></td>
                            <td><input type="date" name="cardExpiry" placeholder="Card Expiry" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCity" placeholder="City" required></td>
                            <td><input type="text" name="cardSecurityCode" placeholder="Card Security Code" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCounty" placeholder="County" required></td>
                            <td><input type="checkbox" name="sendTickets">Send tickets to address<br></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressPostcode" placeholder="Postcode" required></td>
                            <td>Due: ${invoice.getAmount()}</td>
                        </tr>
                        <tr>
                            <td> </td>
                            <td><button name="FinishAndPay" type="submit" style="text-align: center">Finish & Pay</button></td>
                        </tr>
                    </table>
                </form>
            </c:if>
            <!-- If the customer is logged in then display filled form -->
            <c:if test="${cookie.containsKey('memberId')}">
                <form name="checkoutForm" class="styledForm" action="checkout" method="POST">
                    <input type="hidden" name="bookingId" value="${booking.getId()}">
                    <input type="hidden" name="eventId" value="${chosenEvent.getId()}">
                    <table name="checkoutTable" class="styledTable">
                        <tr>
                            <td><input type="text" name="name" placeholder="Full Name" value="${member.getName()}" required></td>
                            <td><input type="email" name="email" placeholder="Email" value="${member.getEmail()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="telephone" placeholder="Telephone" value="${member.getTelephone()}"></td>
                            <td>
                                <select name="cardType">
                                    <option value="visaDebit">VISA Debit</option>
                                    <option value="visaCredit">VISA Credit</option>
                                    <option value="mastercard">Mastercard</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine1" placeholder="Address Line 1" value="${member.getAddress()[0]}" required></td>
                            <td><input type="text" name="cardHolder" placeholder="Card Holder" value="${memberCard.getHolder()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine2" placeholder="Address Line 2" value="${member.getAddress()[1]}" required></td>
                            <td><input type="text" name="cardNumber" placeholder="Card Number" value="${memberCard.getNumber()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressLine3" placeholder="Address Line 3" value="${member.getAddress()[2]}" required></td>
                            <td><input type="date" name="cardExpiry" placeholder="Card Expiry" value="${memberCard.getExpiry()}" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCity" placeholder="City" value="${member.getAddress()[3]}" required></td>
                            <td><input type="text" name="cardSecurityCode" placeholder="Card Security Code" required></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressCounty" placeholder="County" value="${member.getAddress()[4]}" required></td>
                            <td><input type="checkbox" name="sendTickets">Send tickets to address<br></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="addressPostcode" placeholder="Postcode" value="${member.getAddress()[5]}" required></td>
                            <td>Due: ${invoice.getAmount()}</td>
                        </tr>
                        <tr>
                            <td> </td>
                            <td><button name="FinishAndPay" type="submit" style="text-align: center">Finish & Pay</button></td>
                        </tr>
                    </table>
                </form>
            </c:if>

            <!-- Cancel checkout button -->
            <form name="cancelForm" class="styledForm" action="home" method="POST">
                <input type="hidden" name="bookingId" value="${booking.getId()}">
                <button name="cancelBooking" type="submit" style="text-align: center">Cancel</button>
            </form>

        </div>

        <!-- Footer at the bottom of the page -->
        <div id="footer" class="pageFooter">
            <br><span>Copyright 2018</span>
        </div>
    </body>
</html>
