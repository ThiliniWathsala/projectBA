package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionMongoRepository extends CrudRepository<Question, String>{
}
