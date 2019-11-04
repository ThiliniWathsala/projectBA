package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class QuestionSearchRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public Collection<Question> getFromProjectid(String projectid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("projectid").regex(projectid))
        ), Question.class);
    }

    public Collection<Question> getFromQuestionid(String questionid) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("questionid").regex(questionid))
        ), Question.class);
    }
}
