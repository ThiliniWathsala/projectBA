package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerMongoRepository extends CrudRepository<Answer, String> {
}
