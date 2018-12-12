package com.TicketIT.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.TicketIT.Model.Invoice;
import com.TicketIT.Utils.EncryptUtils;
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

        // If no salt assume data corruption.
        if(invoice.getEncryptSalt() != null) {
            String salt = invoice.getEncryptSalt();
            try {
                builder.append("amount", EncryptUtils.encrypt(invoice.getAmount().toString(), salt));
                builder.append("cardId", EncryptUtils.encrypt(invoice.getCardId(), salt));
                builder.append("paid", EncryptUtils.encrypt(invoice.getPaid().toString(), salt));
                builder.append("encryptSalt", invoice.getEncryptSalt());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }


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

        // Decrypt amount.
        if(doc.get("amount") != null) {
            try {
                String amount = EncryptUtils.decrypt(doc.get("amount").toString(), doc.get("encryptSalt").toString());
                invoice.setAmount(Double.parseDouble(amount));
            } catch(Exception ex) {
                ex.printStackTrace();
                invoice.setAmount(Double.parseDouble(doc.get("amount").toString()));
            }
        }

        // Decrypt cardId.
        if(doc.get("cardId") != null) {
            try {
                String cardId = EncryptUtils.decrypt(doc.get("cardId").toString(), doc.get("encryptSalt").toString());
                invoice.setCardId(cardId);
            } catch(Exception ex) {
                ex.printStackTrace();
                invoice.setCardId(doc.get("cardId").toString());
            }
        }

        // Decrypt paid.
        if(doc.get("paid") != null) {
            try {
                String paid = EncryptUtils.decrypt(doc.get("paid").toString(), doc.get("encryptSalt").toString());
                invoice.setPaid(Boolean.parseBoolean(paid));
            } catch(Exception ex) {
                ex.printStackTrace();
                invoice.setPaid(Boolean.parseBoolean(doc.get("paid").toString()));
            }
        }

        if(doc.get("encryptSalt") != null)
            invoice.setEncryptSalt(doc.get("encryptSalt").toString());

        return invoice;
    }
}
