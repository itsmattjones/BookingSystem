package com.TicketIT.Converter;

import com.TicketIT.Model.Member;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.List;

public class MemberConverter {

    /**
     * Converts a Member object into a DBObject, so
     * it can be stored in the MongoDB database cleanly.
     *
     * @param member A Member object to convert.
     * @return The constructed DBObject for MongoDB.
     */
    public static DBObject ToDatabaseObject(Member member) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();

        if (member.getId() != null)
            builder = builder.append("_id", new ObjectId(member.getId()));

        builder.append("email", member.getEmail());
        builder.append("password", member.getPassword());
        builder.append("name", member.getName());
        builder.append("address", member.getAddress());
        builder.append("telephone", member.getTelephone());
        builder.append("cardId", member.getCardId());
        builder.append("isAdmin", member.getIsAdmin());
        return builder.get();
    }

    /**
     * Converts a DBObject into a Member object, so
     * it can be used within the program.
     *
     * @param doc A MongoDB DBObject document.
     * @return The constructed Member object.
     */
    public static Member ToMemberObject(DBObject doc) {
        Member member = new Member();
        ObjectId id = (ObjectId) doc.get("_id");
        member.setId(id.toString());

        if(doc.get("email") != null)
            member.setEmail(doc.get("email").toString());
        if(doc.get("password") != null)
            member.setPassword(doc.get("password").toString());
        if(doc.get("name") != null)
            member.setName(doc.get("name").toString());
        if(doc.get("address") != null)
            member.setAddress((List<String>) doc.get("address"));
        if(doc.get("telephone") != null)
            member.setTelephone(doc.get("telephone").toString());
        if(doc.get("cardId") != null)
            member.setCardId(doc.get("cardId").toString());
        if(doc.get("isAdmin") != null)
            member.setIsAdmin(Boolean.parseBoolean(doc.get("isAdmin").toString()));

        return member;
    }
}
