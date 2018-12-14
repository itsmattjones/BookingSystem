package com.TicketIT.Converter;

import com.TicketIT.Model.Invoice;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class InvoiceConverter {

    /**
     * Converts a Invoice object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param invoice A Invoice object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Invoice invoice) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (invoice.getId() != null)
            builder = builder.append("_id", new ObjectId(invoice.getId()));

        builder.append("amount", invoice.getAmount());
        builder.append("cardId", invoice.getCardId());
        builder.append("paid", invoice.getPaid());
        return builder.get();
    }

    /**
     * Converts a DBObject into a Invoice object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Invoice object.
     */
    public static Invoice ToInvoiceObject(DBObject doc) {
        Invoice invoice = new Invoice();
        ObjectId id = (ObjectId) doc.get("_id");
        invoice.setId(id.toString());

        if(doc.get("amount") != null)
            invoice.setAmount(Double.parseDouble(doc.get("amount").toString()));
        if(doc.get("cardId") != null)
            invoice.setCardId(doc.get("cardId").toString());
        if(doc.get("paid") != null)
            invoice.setPaid(Boolean.parseBoolean(doc.get("paid").toString()));

        return invoice;
    }
}
