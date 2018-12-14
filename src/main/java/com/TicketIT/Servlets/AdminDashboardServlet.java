package com.TicketIT.Servlets;

import com.TicketIT.DataAccessObject.MongoDBEventDAO;
import com.TicketIT.DataAccessObject.MongoDBMemberDAO;
import com.TicketIT.Model.Event;
import com.TicketIT.Utils.AdminUtils;
import com.mongodb.MongoClient;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminDashboardServlet extends HttpServlet {

    /**
     * doPost function for the admin dashboard page servlet.
     * This handler will process any actions from the admin page.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies())) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // If there's a action request, process it.
        if(request.getParameterMap().containsKey("action")){
            if(request.getParameter("action").equals("deleteEvent")) {
                MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
                eventDAO.DeleteEvent(eventDAO.GetEventById(request.getParameter("eventId")));
                response.sendRedirect(request.getContextPath() + "/admin");
            }
        }

        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    /**
     * doGet function for the admin dashboard page servlet.
     * This handler will show the user the dashboard page, if they're allowed to see it.
     * It will pass the eventList for displaying on the dashboard.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBMemberDAO memberDAO = new MongoDBMemberDAO(mongo);

        // Validate that the user is allowed to be here.
        if(!AdminUtils.IsMemberAllowedAccess(memberDAO, request.getCookies())) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // Get all events from database.
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        List<Event> events = eventDAO.GetAllEvents();

        request.setAttribute("eventList", events);
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }
}
