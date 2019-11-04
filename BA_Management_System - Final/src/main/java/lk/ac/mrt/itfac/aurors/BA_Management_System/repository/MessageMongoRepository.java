package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageMongoRepository extends CrudRepository<Message, String>{
}
