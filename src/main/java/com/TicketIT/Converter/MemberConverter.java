package com.TicketIT.Converter;

import com.TicketIT.Model.Member;
import com.TicketIT.Utils.EncryptUtils;
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

        // New Encrypted salt generated each time it's pushed to database.
        String newSalt = EncryptUtils.getSalt();

        builder.append("email", member.getEmail());

        // Ecnrypts the members password.
        builder.append("password", EncryptUtils.encrypt(member.getPassword(), newSalt));

        builder.append("name", member.getName());
        builder.append("address", member.getAddress());
        builder.append("telephone", member.getTelephone());
        builder.append("cardId", member.getCardId());
        builder.append("isAdmin", member.getIsAdmin());
        builder.append("slt", newSalt);

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

        // Decrypts the members password.
        if(doc.get("password") != null)
            member.setPassword(EncryptUtils.decrypt(doc.get("password").toString(), doc.get("slt").toString()));

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
