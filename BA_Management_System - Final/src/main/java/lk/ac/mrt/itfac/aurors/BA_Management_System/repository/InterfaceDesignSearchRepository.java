package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.InterfaceDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class InterfaceDesignSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<InterfaceDesign> getFromSRSId(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("SRSid").regex(id))
        ), InterfaceDesign.class);
    }
}
