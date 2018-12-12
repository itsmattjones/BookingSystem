package com.TicketIT.Converter;

import com.TicketIT.Model.Member;
import com.TicketIT.Utils.EncryptUtils;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.ArrayList;
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

        // If no salt assume data corruption.
        if(member.getEncryptSalt() != null) {
            String salt = member.getEncryptSalt();
            try {
                builder.append("email", EncryptUtils.encrypt(member.getEmail(), salt));
                builder.append("password", EncryptUtils.encrypt(member.getPassword(), salt));
                builder.append("name", EncryptUtils.encrypt(member.getName(), salt));

                List<String> encryptedList = new ArrayList<>();
                for(String address : member.getAddress()){
                    encryptedList.add(EncryptUtils.encrypt(address, salt));
                }
                builder.append("address", encryptedList);

                builder.append("telephone", EncryptUtils.encrypt(member.getTelephone(), salt));
                builder.append("cardId", EncryptUtils.encrypt(member.getCardId(), salt));
                builder.append("isAdmin", EncryptUtils.encrypt(member.getIsAdmin().toString(), salt));
                builder.append("encryptSalt", member.getEncryptSalt());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

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

        // Decrypt email.
        if(doc.get("email") != null) {
            try {
                String email = EncryptUtils.decrypt(doc.get("email").toString(), doc.get("encryptSalt").toString());
                member.setEmail(email);
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setEmail(doc.get("email").toString());
            }
        }

        // Decrypt password.
        if(doc.get("password") != null) {
            try {
                String pass = EncryptUtils.decrypt(doc.get("password").toString(), doc.get("encryptSalt").toString());
                member.setPassword(pass);
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setPassword(doc.get("password").toString());
            }
        }

        // Decrypt name.
        if(doc.get("name") != null) {
            try {
                String name = EncryptUtils.decrypt(doc.get("name").toString(), doc.get("encryptSalt").toString());
                member.setName(name);
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setName(doc.get("name").toString());
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
                member.setAddress(decryptedList);
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setAddress(encryptedList);
            }
        }

        // Decrypt telephone.
        if(doc.get("telephone") != null) {
            try {
                String telephone = EncryptUtils.decrypt(doc.get("telephone").toString(), doc.get("encryptSalt").toString());
                member.setTelephone(telephone);
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setTelephone(doc.get("telephone").toString());
            }
        }

        // Decrypt cardId.
        if(doc.get("cardId") != null) {
            try {
                String cardId = EncryptUtils.decrypt(doc.get("cardId").toString(), doc.get("encryptSalt").toString());
                member.setCardId(cardId);
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setCardId(doc.get("cardId").toString());
            }
        }

        // Decrypt isAdmin.
        if(doc.get("isAdmin") != null) {
            try {
                String isAdmin = EncryptUtils.decrypt(doc.get("isAdmin").toString(), doc.get("encryptSalt").toString());
                member.setIsAdmin(Boolean.parseBoolean(isAdmin));
            } catch(Exception ex) {
                ex.printStackTrace();
                member.setIsAdmin(Boolean.parseBoolean(doc.get("isAdmin").toString()));
            }
        }

        if(doc.get("encryptSalt") != null)
            member.setEncryptSalt(doc.get("encryptSalt").toString());

        return member;
    }
}
