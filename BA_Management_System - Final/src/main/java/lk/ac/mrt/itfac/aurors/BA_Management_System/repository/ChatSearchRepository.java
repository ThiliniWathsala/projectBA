package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ChatSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Chat> searchChats(String ownerid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("employee1").regex(ownerid),
                        Criteria.where("employee2").regex(ownerid))
        ), Chat.class);
    }

    public Collection<Chat> searchChatsFromID(String chatid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("chatid").regex(chatid))
        ), Chat.class);
    }

}
