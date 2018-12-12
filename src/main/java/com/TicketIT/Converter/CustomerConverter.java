package com.TicketIT.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.TicketIT.Model.Customer;
import com.TicketIT.Utils.EncryptUtils;
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

        // If no salt assume data corruption.
        if(customer.getEncryptSalt() != null) {
            String salt = customer.getEncryptSalt();
            try {
                builder.append("email", EncryptUtils.encrypt(customer.getEmail(), salt));
                builder.append("name", EncryptUtils.encrypt(customer.getName(), salt));

                List<String> encryptedList = new ArrayList<>();
                for(String address : customer.getAddress()){
                    encryptedList.add(EncryptUtils.encrypt(address, salt));
                }
                builder.append("address", encryptedList);

                builder.append("telephone", EncryptUtils.encrypt(customer.getTelephone(), salt));
                builder.append("encryptSalt", customer.getEncryptSalt());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

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

        // Decrypt email.
        if(doc.get("email") != null) {
            try {
                String email = EncryptUtils.decrypt(doc.get("email").toString(), doc.get("encryptSalt").toString());
                customer.setEmail(email);
            } catch(Exception ex) {
                ex.printStackTrace();
                customer.setEmail(doc.get("email").toString());
            }
        }

        // Decrypt name.
        if(doc.get("name") != null) {
            try {
                String name = EncryptUtils.decrypt(doc.get("name").toString(), doc.get("encryptSalt").toString());
                customer.setName(name);
            } catch(Exception ex) {
                ex.printStackTrace();
                customer.setName(doc.get("name").toString());
            }
        }

        // Decrypt address.
        if(doc.get("address") != null) {
            List<String> encryptedList = (List<String>) doc.get("address");
            try {
                List<String> decryptedList = new ArrayList<>();
                for(String encryptedTicket : encryptedList) {
                    decryptedList.add(EncryptUtils.decrypt(encryptedTicket, doc.get("encryptedSalt").toString()));
                }
                customer.setAddress(decryptedList);
            } catch(Exception ex) {
                ex.printStackTrace();
                customer.setAddress(encryptedList);
            }
        }

        // Decrypt telephone.
        if(doc.get("telephone") != null) {
            try {
                String telephone = EncryptUtils.decrypt(doc.get("telephone").toString(), doc.get("encryptSalt").toString());
                customer.setTelephone(telephone);
            } catch(Exception ex) {
                ex.printStackTrace();
                customer.setTelephone(doc.get("telephone").toString());
            }
        }

        if(doc.get("encryptSalt") != null)
            customer.setEncryptSalt(doc.get("encryptSalt").toString());

        return customer;
    }
}
