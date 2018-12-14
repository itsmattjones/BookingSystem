package com.TicketIT.Converter;

import java.util.List;
import com.TicketIT.Model.Customer;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class CustomerConverter {

    /**
     * Converts a Customer object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param customer A customer object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Customer customer) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (customer.getId() != null)
            builder = builder.append("_id", new ObjectId(customer.getId()));

        builder.append("email", customer.getEmail());
        builder.append("name", customer.getName());
        builder.append("address", customer.getAddress());
        builder.append("telephone", customer.getTelephone());
        return builder.get();
    }

    /**
     * Converts a DBObject into a Customer object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Customer object.
     */
    public static Customer ToCustomerObject(DBObject doc) {
        Customer customer = new Customer();
        ObjectId id = (ObjectId) doc.get("_id");
        customer.setId(id.toString());

        if(doc.get("email") != null)
            customer.setEmail(doc.get("email").toString());
        if(doc.get("name") != null)
            customer.setName(doc.get("name").toString());
        if(doc.get("address") != null)
            customer.setAddress((List<String>) doc.get("address"));
        if(doc.get("telephone") != null)
            customer.setTelephone(doc.get("telephone").toString());

        return customer;
    }
}
