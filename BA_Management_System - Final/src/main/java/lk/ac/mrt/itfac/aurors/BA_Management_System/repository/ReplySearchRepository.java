package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ReplySearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Reply> getFromAnswerId(String aid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("answerid").regex(aid))
        ), Reply.class);
    }
}
