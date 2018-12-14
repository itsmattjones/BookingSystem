package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.CustomerConverter;
import com.TicketIT.Model.Customer;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBCustomerDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "customer";

    /**
     * Customer Data Access Object constructor.
     */
    public MongoDBCustomerDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a customer in the database.
     *
     * @param customer A customer object to create in MongoDB.
     * @return The customer object created in MongoDB.
     */
    public Customer CreateCustomer(Customer customer) {
        DBObject doc = CustomerConverter.ToDatabaseObject(customer);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        customer.setId(id.toString());
        return customer;
    }

    /**
     * Deletes a customer in the database.
     *
     * @param customer The customer object to delete in the database.
     */
    public Boolean DeleteCustomer(Customer customer) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(customer.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a customer in the database.
     *
     * @param customer The customer object to update in the database.
     */
    public Boolean UpdateCustomer(Customer customer) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(customer.getId())).get();
        this.collection.update(query, CustomerConverter.ToDatabaseObject(customer));
        return true;
    }

    /**
     * Gets the given customer from the database.
     *
     * @param customer The customer object to retrieve.
     * @return The customer object from MongoDB.
     */
    public Customer GetCustomer(Customer customer) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(customer.getId())).get();
        DBObject data = this.collection.findOne(query);
        return CustomerConverter.ToCustomerObject(data);
    }

    /**
     * Gets the customer for the given customer Id from the database.
     *
     * @param id The customer Id of the customer object to retrieve.
     * @return The customer object from MongoDB.
     */
    public Customer GetCustomerById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return CustomerConverter.ToCustomerObject(data);
    }

    /**
     * Gets all customers from the database.
     *
     * @return All the customers from MongoDB.
     */
    public List<Customer> GetAllCustomers() {
        List<Customer> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Customer customer = CustomerConverter.ToCustomerObject(doc);
            data.add(customer);
        }

        return data;
    }
}
