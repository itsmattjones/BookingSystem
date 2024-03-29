<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>TicketIT - Register</title>
        <meta name="description" content="Register on the TicketIT website.">
        <link rel="stylesheet" type="text/css" href="styles/globalStyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/registerStyle.css" />
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
            <br><br>
            <h1>Registration Form</h1>

            <!-- Registration form -->
            <form name="registrationForm" class="styledForm" action="register" method="POST">
                <table name="registrationTable" class="styledTable">
                    <tr>
                        <td><input type="text" name="name" placeholder="Full Name" required></td>
                        <td><input type="email" name="email" placeholder="Email" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="telephone" placeholder="Telephone"></td>
                        <td><input type="password" name="password" placeholder="Password" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressLine1" placeholder="Address Line 1" required></td>
                        <td>
                            <select name="cardType">
                                <option value="visaDebit">VISA Debit</option>
                                <option value="visaCredit">VISA Credit</option>
                                <option value="mastercard">Mastercard</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressLine2" placeholder="Address Line 2" required></td>
                        <td><input type="text" name="cardHolder" placeholder="Card Holder" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressLine3" placeholder="Address Line 3" ></td>
                        <td><input type="text" name="cardNumber" placeholder="Card Number" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressCity" placeholder="City" required></td>
                        <td><input type="date" name="cardExpiry" placeholder="Card Expiry" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressCounty" placeholder="County" required></td>
                        <td><input type="text" name="cardSecurityCode" placeholder="Card Security Code" required></td>
                    </tr>
                    <tr>
                        <td><input type="text" name="addressPostcode" placeholder="Postcode" required></td>
                        <td><button name="submitRegistration" id="submitRegistration" type="submit" style="text-align: center">Register</button></td>
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
