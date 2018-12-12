package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.InvoiceConverter;
import com.TicketIT.Model.Invoice;
import com.TicketIT.Utils.EncryptUtils;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBInvoiceDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "invoice";

    /**
     * Invoice Data Access Object constructor.
     */
    public MongoDBInvoiceDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a Invoice in the database.
     *
     * @param invoice A invoice object to create in MongoDB.
     * @return The invoice object created in MongoDB.
     */
    public Invoice CreateInvoice(Invoice invoice) {
        invoice.setEncryptSalt(EncryptUtils.getSalt());
        DBObject doc = InvoiceConverter.ToDatabaseObject(invoice);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        invoice.setId(id.toString());
        return invoice;
    }

    /**
     * Deletes a invoice in the database.
     *
     * @param invoice The invoice object to delete in the database.
     */
    public Boolean DeleteInvoice(Invoice invoice) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(invoice.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a invoice in the database.
     *
     * @param invoice The invoice object to update in the database.
     */
    public Boolean UpdateInvoice(Invoice invoice) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(invoice.getId())).get();
        this.collection.update(query, InvoiceConverter.ToDatabaseObject(invoice));
        return true;
    }

    /**
     * Gets the given invoice from the database.
     *
     * @param invoice The invoice object to retrieve.
     * @return The invoice object from MongoDB.
     */
    public Invoice GetInvoice(Invoice invoice) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(invoice.getId())).get();
        DBObject data = this.collection.findOne(query);
        return InvoiceConverter.ToInvoiceObject(data);
    }

    /**
     * Gets the invoice for the given invoice Id from the database.
     *
     * @param id The invoice Id of the invoice object to retrieve.
     * @return The invoice object from MongoDB.
     */
    public Invoice GetInvoiceById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return InvoiceConverter.ToInvoiceObject(data);
    }

    /**
     * Gets all invoices from the database.
     *
     * @return All the invoices from MongoDB.
     */
    public List<Invoice> GetAllInvoices() {
        List<Invoice> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Invoice invoice = InvoiceConverter.ToInvoiceObject(doc);
            data.add(invoice);
        }

        return data;
    }
}
