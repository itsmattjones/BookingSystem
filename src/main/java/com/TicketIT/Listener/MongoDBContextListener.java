package com.TicketIT.Listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.UnknownHostException;
import com.mongodb.MongoClient;

@WebListener
public class MongoDBContextListener implements ServletContextListener {

    /**
     * Create the MongoClient instance when the web application
     * initialization process is starting.
     *
     * @param event Event to process.
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            ServletContext ctx = event.getServletContext();
            MongoClient mongo = new MongoClient(ctx.getInitParameter("MONGODB_HOST"), Integer.parseInt(ctx.getInitParameter("MONGODB_PORT")));
            System.out.println("MongoClient initialized successfully");
            event.getServletContext().setAttribute("MONGO_CLIENT", mongo);
        } catch (UnknownHostException e) {
            throw new RuntimeException("MongoClient initialization failed");
        }
    }

    /**
     * Close the MongoClient instance when the ServletContext
     * is about to be shut down.
     *
     * @param event Event to process.
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        MongoClient mongo = (MongoClient) event.getServletContext().getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");
    }
}
