package com.TicketIT.DataAccessObject;

import com.TicketIT.Converter.MemberConverter;
import com.TicketIT.Model.Member;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDBMemberDAO {

    private DBCollection collection;
    private final String DatabaseName = "ticketit";
    private final String CollectionName = "member";

    /**
     * Member Data Access Object constructor.
     */
    public MongoDBMemberDAO(MongoClient mongo){
        this.collection = mongo.getDB(DatabaseName).getCollection(CollectionName);
    }

    /**
     * Creates a Member in the database.
     *
     * @param member A member object to create in MongoDB.
     * @return The member object created in MongoDB.
     */
    public Member CreateMember(Member member) {
        DBObject doc = MemberConverter.ToDatabaseObject(member);
        this.collection.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        member.setId(id.toString());
        return member;
    }

    /**
     * Deletes a member in the database.
     *
     * @param member The member object to delete in the database.
     */
    public Boolean DeleteMember(Member member) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(member.getId())).get();
        this.collection.remove(query);
        return true;
    }

    /**
     * Updates a member in the database.
     *
     * @param member The member object to update in the database.
     */
    public Boolean UpdateMember(Member member) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(member.getId())).get();
        this.collection.update(query, MemberConverter.ToDatabaseObject(member));
        return true;
    }

    /**
     * Gets the given member from the database.
     *
     * @param member The member object to retrieve.
     * @return The member object from MongoDB.
     */
    public Member GetMember(Member member) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(member.getId())).get();
        DBObject data = this.collection.findOne(query);
        return MemberConverter.ToMemberObject(data);
    }

    /**
     * Gets the member for the given member Id from the database.
     *
     * @param id The member Id of the member object to retrieve.
     * @return The member object from MongoDB.
     */
    public Member GetMemberById(String id) {
        DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(id)).get();
        DBObject data = this.collection.findOne(query);
        return MemberConverter.ToMemberObject(data);
    }

    /**
     * Gets the member for the given email from the database.
     *
     * @param email The email of the member object to retrieve.
     * @return The member object from MongoDB.
     */
    public Member GetMemberByEmail(String email) {
        DBObject query = BasicDBObjectBuilder.start().append("email", email).get();
        DBObject data = this.collection.findOne(query);
        return MemberConverter.ToMemberObject(data);
    }

    /**
     * Checks if a member exists with the given email.
     *
     * @return True/False whether exists.
     */
    public Boolean DoesMemberExist(String email) {
        DBObject query = BasicDBObjectBuilder.start().append("email", email).get();
        DBObject data = this.collection.findOne(query);
        if(data != null)
            return true;
        return false;
    }

    /**
     * Gets all members from the database.
     *
     * @return All the members from MongoDB.
     */
    public List<Member> GetAllMembers() {
        List<Member> data = new ArrayList<>();
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Member Member = MemberConverter.ToMemberObject(doc);
            data.add(Member);
        }

        return data;
    }
}
