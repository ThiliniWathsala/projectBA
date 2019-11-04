package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.SRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SRSSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<SRS> getFromProjectid(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("projectid").regex(id))
        ), SRS.class);
    }

    public Collection<SRS> getFromSRSid(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("SRSid").regex(id))
        ), SRS.class);
    }
}
