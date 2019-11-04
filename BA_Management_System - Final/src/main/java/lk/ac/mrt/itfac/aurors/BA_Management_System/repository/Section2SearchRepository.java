package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Section2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class Section2SearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Section2> getFromSRSid(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("SRSid").regex(id))
        ), Section2.class);
    }
}
