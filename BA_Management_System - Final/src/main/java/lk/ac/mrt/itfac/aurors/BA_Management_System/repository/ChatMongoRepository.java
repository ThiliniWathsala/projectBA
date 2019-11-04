package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatMongoRepository extends CrudRepository<Chat, String>{
}
