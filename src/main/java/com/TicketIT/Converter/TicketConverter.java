package com.TicketIT.Converter;

import com.TicketIT.Model.Ticket;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class TicketConverter {

    /**
     * Converts a Ticket object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param ticket A Ticket object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Ticket ticket) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (ticket.getId() != null)
            builder = builder.append("_id", new ObjectId(ticket.getId()));

        builder.append("eventId", ticket.getEventId());
        builder.append("name", ticket.getName());
        builder.append("price", ticket.getPrice());
        builder.append("numberAvailable", ticket.getNumberAvailable());
        return builder.get();
    }

    /**
     * Converts a DBObject into a Ticket object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Ticket object.
     */
    public static Ticket ToTicketObject(DBObject doc) {
        Ticket ticket = new Ticket();
        ObjectId id = (ObjectId) doc.get("_id");
        ticket.setId(id.toString());

        if(doc.get("eventId") != null)
            ticket.setEventId(doc.get("eventId").toString());
        if(doc.get("name") != null)
            ticket.setName(doc.get("name").toString());
        if(doc.get("price") != null)
            ticket.setPrice(Double.parseDouble(doc.get("price").toString()));
        if(doc.get("numberAvailable") != null)
            ticket.setNumberAvailable(Integer.parseInt(doc.get("numberAvailable").toString()));

        return ticket;
    }

}
