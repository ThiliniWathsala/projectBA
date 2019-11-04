package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.SBALeaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SBALeaderBoardSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<SBALeaderboard> gradesSBAId(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("employeeid").regex(id))
        ), SBALeaderboard.class);
    }
}
