package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class EmployeeSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Employee> searchUserEmail(String text) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("email").regex(text))
        ), Employee.class);
    }

    public Collection<Employee> searchUserId(String id) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("employeeid").regex(id))
        ), Employee.class);
    }

    public Collection<Employee> jbaList() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("type").regex("JBA"))
        ), Employee.class);
    }

    public Collection<Employee> sbaList() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("type").regex("SBA"))
        ), Employee.class);
    }

    public Collection<Employee> devList() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("type").regex("Dev"))
        ), Employee.class);
    }

    public Collection<Employee> pmList() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("type").regex("PM"))
        ), Employee.class);
    }

    public Collection<Employee> getAdmin() {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("type").regex("admin"))
        ), Employee.class);
    }

    public Collection<Employee> searchEmp(String name) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("fname").regex(name,"i"),
                            Criteria.where("lname").regex(name,"i"))
        ), Employee.class);
    }
}
