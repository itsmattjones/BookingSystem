package com.TicketIT.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import com.TicketIT.Converter.CardConverter;
import com.TicketIT.Model.Card;
import com.TicketIT.Utils.EncryptUtils;
import com.mongodb.*;
import org.bson.types.ObjectId;

public class MongoDBCardDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "card";

    /**
     * Card Data Access Object constructor.
     */
    public MongoDBCardDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a Card in the database.
     *
     * @param card A card object to create in MongoDB.
     * @return The card object created in MongoDB.
     */
    public Card CreateCard(Card card) {
        card.setEncryptSalt(EncryptUtils.getSalt());
        DBObject doc = CardConverter.ToDatabaseObject(card);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        card.setId(id.toString());
        return card;
    }

    /**
     * Deletes a card in the database.
     *
     * @param card The card object to delete in the database.
     */
    public Boolean DeleteCard(Card card) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(card.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a card in the database.
     *
     * @param card The card object to update in the database.
     */
    public Boolean UpdateCard(Card card) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(card.getId())).get();
        this.collection.update(query, CardConverter.ToDatabaseObject(card));
        return true;
    }

    /**
     * Gets the given card from the database.
     *
     * @param card The card object to retrieve.
     * @return The card object from MongoDB.
     */
    public Card GetCard(Card card) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(card.getId())).get();
        DBObject data = this.collection.findOne(query);
        return CardConverter.ToCardObject(data);
    }

    /**
     * Gets the card for the given card Id from the database.
     *
     * @param id The card Id of the card object to retrieve.
     * @return The card object from MongoDB.
     */
    public Card GetCardById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return CardConverter.ToCardObject(data);
    }

    /**
     * Get the card for the given card number from the database.
     *
     * @param cardNumber The cards number.
     * @return The card object from MongoDB.
     */
    public Card GetCardByNumber(String cardNumber) {
        DBObject query = BasicDBObjectBuilder.start().append("number", cardNumber).get();
        DBObject data = this.collection.findOne(query);
        return CardConverter.ToCardObject(data);
    }

    /**
     * Checks if a card exists with the given card number.
     *
     * @param cardNumber The cards number.
     * @return True/False whether it exists.
     */
    public Boolean DoesCardExist(String cardNumber) {
        DBObject query = BasicDBObjectBuilder.start().append("number", cardNumber).get();
        DBObject data = this.collection.findOne(query);
        if(data != null)
            return true;
        return false;
    }

    /**
     * Gets all cards from the database.
     *
     * @return All the cards from MongoDB.
     */
    public List<Card> GetAllCards() {
        List<Card> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Card card = CardConverter.ToCardObject(doc);
            data.add(card);
        }

        return data;
    }

}
