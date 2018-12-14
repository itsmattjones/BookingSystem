package com.TicketIT.Converter;

import java.util.List;
import com.TicketIT.Model.Booking;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class BookingConverter {

    /**
     * Converts a Booking object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param booking A Booking object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Booking booking) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (booking.getId() != null)
            builder = builder.append("_id", new ObjectId(booking.getId()));

        builder.append("customerId", booking.getCustomerId());
        builder.append("tickets", booking.getTickets());
        builder.append("invoiceId", booking.getInvoiceId());
        builder.append("sendTickets", booking.getSendTickets());

        return builder.get();
    }

    /**
     * Converts a DBObject into a Booking object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Booking object.
     */
    public static Booking ToBookingObject(DBObject doc) {
        Booking booking = new Booking();
        ObjectId id = (ObjectId) doc.get("_id");
        booking.setId(id.toString());

        if(doc.get("customerId") != null)
            booking.setCustomerId(doc.get("customerId").toString());
        if(doc.get("tickets") != null)
            booking.setTickets((List<String>) doc.get("tickets"));
        if(doc.get("invoiceId") != null)
            booking.setInvoiceId(doc.get("invoiceId").toString());
        if(doc.get("sendTickets") != null)
            booking.setSendTickets(Boolean.parseBoolean(doc.get("sendTickets").toString()));

        return booking;
    }
}
