package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ProjectSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Project> projectDetailsId(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("projectid").regex(id))
        ), Project.class);
    }

    public Collection<Project> current() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("status").regex("created"),
                        Criteria.where("status").regex("Approved by PM"),
                        Criteria.where("status").regex("Rejected by SBA"),
                        Criteria.where("status").regex("Approved by SBA"),
                        Criteria.where("status").regex("Submitted to SBA"))
        ), Project.class);
    }

    public Collection<Project> ended() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("status").regex("completed"))
        ), Project.class);
    }

    public Collection<Project> projectList(String empid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("pm").regex(empid),
                        Criteria.where("jba1").regex(empid),
                        Criteria.where("jba2").regex(empid),
                        Criteria.where("dev1").regex(empid),
                        Criteria.where("dev2").regex(empid),
                        Criteria.where("dev3").regex(empid),
                        Criteria.where("dev4").regex(empid),
                        Criteria.where("lba").regex(empid),
                        Criteria.where("dev5").regex(empid))
        ), Project.class);
    }
}
