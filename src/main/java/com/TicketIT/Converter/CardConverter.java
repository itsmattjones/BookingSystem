package com.TicketIT.Converter;

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

        // New Encrypted Salt generated each time it's pushed to database.
        String newSalt = EncryptUtils.getSalt();

        builder.append("type", EncryptUtils.encrypt(card.getType(), newSalt));
        builder.append("holder", EncryptUtils.encrypt(card.getHolder(), newSalt));
        builder.append("number", EncryptUtils.encrypt(card.getNumber(), newSalt));
        builder.append("expiry", EncryptUtils.encrypt(card.getExpiry(), newSalt));
        builder.append("securityCode", EncryptUtils.encrypt(card.getSecurityCode(), newSalt));
        builder.append("slt", newSalt);

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

        if(doc.get("type") != null)
            card.setType(EncryptUtils.decrypt(doc.get("type").toString(), doc.get("slt").toString()));
        if(doc.get("holder") != null)
            card.setHolder(EncryptUtils.decrypt(doc.get("holder").toString(), doc.get("slt").toString()));
        if(doc.get("number") != null)
            card.setNumber(EncryptUtils.decrypt(doc.get("number").toString(), doc.get("slt").toString()));
        if(doc.get("expiry") != null)
            card.setExpiry(EncryptUtils.decrypt(doc.get("expiry").toString(), doc.get("slt").toString()));
        if(doc.get("securityCode") != null)
            card.setSecurityCode(EncryptUtils.decrypt(doc.get("securityCode").toString(), doc.get("slt").toString()));

        return card;
    }
}
