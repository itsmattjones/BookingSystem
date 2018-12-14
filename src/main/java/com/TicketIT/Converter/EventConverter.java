package com.TicketIT.Converter;

import com.TicketIT.Model.Event;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class EventConverter {

    /**
     * Converts a Event object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param event A Event object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Event event) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (event.getId() != null)
            builder = builder.append("_id", new ObjectId(event.getId()));

        builder.append("title", event.getTitle());
        builder.append("description", event.getDescription());
        builder.append("date", event.getDate());
        builder.append("time", event.getTime());
        return builder.get();
    }

    /**
     * Converts a DBObject into a Event object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Event object.
     */
    public static Event ToEventObject(DBObject doc) {
        Event event = new Event();
        ObjectId id = (ObjectId) doc.get("_id");
        event.setId(id.toString());

        if(doc.get("title") != null)
            event.setTitle(doc.get("title").toString());
        if(doc.get("description") != null)
            event.setDescription(doc.get("description").toString());
        if(doc.get("date") != null)
            event.setDate(doc.get("date").toString());
        if(doc.get("time") != null)
            event.setTime(doc.get("time").toString());

        return event;
    }

}
