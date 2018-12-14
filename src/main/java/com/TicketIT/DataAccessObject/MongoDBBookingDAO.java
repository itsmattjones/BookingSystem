package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.BookingConverter;
import com.TicketIT.Model.Booking;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBBookingDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "booking";

    /**
     * Booking Data Access Object constructor.
     */
    public MongoDBBookingDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a booking in the database.
     *
     * @param booking A booking object to create in MongoDB.
     * @return The booking object created in MongoDB.
     */
    public Booking CreateBooking(Booking booking) {
        DBObject doc = BookingConverter.ToDatabaseObject(booking);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        booking.setId(id.toString());
        return booking;
    }

    /**
     * Deletes a booking in the database.
     *
     * @param booking The booking object to delete in the database.
     */
    public Boolean DeleteBooking(Booking booking) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(booking.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a booking in the database.
     *
     * @param booking The booking object to update in the database.
     */
    public Boolean UpdateBooking(Booking booking) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(booking.getId())).get();
        this.collection.update(query, BookingConverter.ToDatabaseObject(booking));
        return true;
    }

    /**
     * Gets the given booking from the database.
     *
     * @param booking The booking object to retrieve.
     * @return The booking object from MongoDB.
     */
    public Booking GetBooking(Booking booking) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(booking.getId())).get();
        DBObject data = this.collection.findOne(query);
        return BookingConverter.ToBookingObject(data);
    }

    /**
     * Gets the booking for the given booking Id from the database.
     *
     * @param id The booking Id of the booking object to retrieve.
     * @return The booking object from MongoDB.
     */
    public Booking GetBookingById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return BookingConverter.ToBookingObject(data);
    }

    /**
     * Gets all bookings from the database.
     *
     * @return All the bookings from MongoDB.
     */
    public List<Booking> GetAllBookings() {
        List<Booking> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Booking booking = BookingConverter.ToBookingObject(doc);
            data.add(booking);
        }

        return data;
    }
}
