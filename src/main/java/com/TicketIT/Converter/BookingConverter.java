package com.TicketIT.Converter;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Model.Booking;
import com.TicketIT.Model.Ticket;
import com.TicketIT.Utils.EncryptUtils;
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

        // If no salt assume data corruption.
        if(booking.getEncryptSalt() != null) {
            String salt = booking.getEncryptSalt();
            try {
                builder.append("customerId", EncryptUtils.encrypt(booking.getCustomerId(), salt));

                List<String> encryptedList = new ArrayList<>();
                for(String ticketId : booking.getTickets()){
                    encryptedList.add(EncryptUtils.encrypt(ticketId, salt));
                }
                builder.append("tickets", encryptedList);

                builder.append("invoiceId", EncryptUtils.encrypt(booking.getInvoiceId(), salt));
                builder.append("sendTickets", EncryptUtils.encrypt(booking.getSendTickets().toString(), salt));
                builder.append("encryptSalt", booking.getEncryptSalt());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

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

        // Decrypt customerId.
        if(doc.get("customerId") != null) {
            try {
                String customerId = EncryptUtils.decrypt(doc.get("customerId").toString(), doc.get("encryptSalt").toString());
                booking.setCustomerId(customerId);
            } catch(Exception ex) {
                ex.printStackTrace();
                booking.setCustomerId(doc.get("customerId").toString());
            }
        }

        // Decrypt tickets.
        if(doc.get("tickets") != null) {
            List<String> encryptedList = (List<String>) doc.get("tickets");
            try {
                List<String> decryptedList = new ArrayList<>();
                for(String encryptedTicket : encryptedList) {
                    decryptedList.add(EncryptUtils.decrypt(encryptedTicket, doc.get("encryptedSalt").toString()));
                }
                booking.setTickets(decryptedList);
            } catch(Exception ex) {
                ex.printStackTrace();
                booking.setTickets(encryptedList);
            }
        }

        // Decrypt invoiceId.
        if(doc.get("invoiceId") != null) {
            try {
                String invoiceId = EncryptUtils.decrypt(doc.get("invoiceId").toString(), doc.get("encryptSalt").toString());
                booking.setInvoiceId(invoiceId);
            } catch(Exception ex) {
                ex.printStackTrace();
                booking.setInvoiceId(doc.get("invoiceId").toString());
            }
        }

        // Decrypt sendTickets.
        if(doc.get("sendTickets") != null) {
            try {
                String sendTickets = EncryptUtils.decrypt(doc.get("sendTickets").toString(), doc.get("encryptSalt").toString());
                booking.setSendTickets(Boolean.parseBoolean(sendTickets));
            } catch(Exception ex) {
                ex.printStackTrace();
                booking.setSendTickets(Boolean.parseBoolean(doc.get("sendTickets").toString()));
            }
        }

        if(doc.get("encryptSalt") != null)
            booking.setEncryptSalt(doc.get("encryptSalt").toString());

        return booking;
    }
}
