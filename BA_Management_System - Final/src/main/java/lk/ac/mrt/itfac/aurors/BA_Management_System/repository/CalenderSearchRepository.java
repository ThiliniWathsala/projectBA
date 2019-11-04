package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Calender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CalenderSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Calender> jbaList(String empid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("ba").regex(empid))
        ), Calender.class);
    }

    public Collection<Calender> leaveList(String empid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("pm").regex(empid))
        ), Calender.class);
    }

    public Collection<Calender> searchID(String leaveid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("leaveid").regex(leaveid))
        ), Calender.class);
    }
}
