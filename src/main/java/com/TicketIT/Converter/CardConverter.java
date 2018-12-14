package com.TicketIT.Converter;

import com.TicketIT.Model.Card;
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

        builder.append("type", card.getType());
        builder.append("holder", card.getHolder());
        builder.append("number", card.getNumber());
        builder.append("expiry", card.getExpiry());
        builder.append("securityCode", card.getSecurityCode());
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
            card.setType(doc.get("type").toString());
        if(doc.get("holder") != null)
            card.setHolder(doc.get("holder").toString());
        if(doc.get("number") != null)
            card.setNumber(doc.get("number").toString());
        if(doc.get("expiry") != null)
            card.setExpiry(doc.get("expiry").toString());
        if(doc.get("securityCode") != null)
            card.setSecurityCode(doc.get("securityCode").toString());

        return card;
    }
}
