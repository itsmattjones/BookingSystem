package com.TicketIT.Converter;

import java.util.UUID;
import com.TicketIT.Model.Card;
import com.TicketIT.Utils.EncryptUtils;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class CardConverter {

    /**
     * Converts a Card object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param card A Card object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Card card) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (card.getId() != null)
            builder = builder.append("_id", new ObjectId(card.getId()));

        // If no salt assume data corruption.
        if(card.getEncryptSalt() != null) {
            String salt = card.getEncryptSalt();
            try {
                builder.append("type", EncryptUtils.encrypt(card.getType(), salt));
                builder.append("holder", EncryptUtils.encrypt(card.getHolder(), salt));
                builder.append("number", EncryptUtils.encrypt(card.getNumber(), salt));
                builder.append("expiry", EncryptUtils.encrypt(card.getExpiry(), salt));
                builder.append("securityCode", EncryptUtils.encrypt(card.getSecurityCode(), salt));
                builder.append("encryptSalt", card.getEncryptSalt());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return builder.get();
    }

    /**
     * Converts a DBObject into a Card object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Card object.
     */
    public static Card ToCardObject(DBObject doc) {
        Card card = new Card();
        ObjectId id = (ObjectId) doc.get("_id");
        card.setId(id.toString());

        // Decrypt type.
        if(doc.get("type") != null) {
            try {
                String type = EncryptUtils.decrypt(doc.get("type").toString(), doc.get("encryptSalt").toString());
                card.setType(type);
            } catch(Exception ex) {
                ex.printStackTrace();
                card.setType(doc.get("type").toString());
            }
        }

        // Decrypt holder.
        if(doc.get("holder") != null) {
            try {
                String holder = EncryptUtils.decrypt(doc.get("holder").toString(), doc.get("encryptSalt").toString());
                card.setHolder(holder);
            } catch(Exception ex) {
                ex.printStackTrace();
                card.setHolder(doc.get("holder").toString());
            }
        }

        // Decrypt number.
        if(doc.get("number") != null) {
            try {
                String number = EncryptUtils.decrypt(doc.get("number").toString(), doc.get("encryptSalt").toString());
                card.setNumber(number);
            } catch(Exception ex) {
                ex.printStackTrace();
                card.setNumber(doc.get("number").toString());
            }
        }

        // Decrypt expiry.
        if(doc.get("expiry") != null) {
            try {
                String expiry = EncryptUtils.decrypt(doc.get("expiry").toString(), doc.get("encryptSalt").toString());
                card.setExpiry(expiry);
            } catch(Exception ex) {
                ex.printStackTrace();
                card.setExpiry(doc.get("expiry").toString());
            }
        }

        // Decrypt expiry.
        if(doc.get("securityCode") != null) {
            try {
                String securityCode = EncryptUtils.decrypt(doc.get("securityCode").toString(), doc.get("encryptSalt").toString());
                card.setSecurityCode(securityCode);
            } catch(Exception ex) {
                ex.printStackTrace();
                card.setSecurityCode(doc.get("securityCode").toString());
            }
        }

        if(doc.get("encryptSalt") != null)
            card.setEncryptSalt(doc.get("encryptSalt").toString());

        return card;
    }
}
