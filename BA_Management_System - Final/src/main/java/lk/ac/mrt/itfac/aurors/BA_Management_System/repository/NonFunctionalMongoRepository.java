package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.NonFunctional;
import org.springframework.data.repository.CrudRepository;

public interface NonFunctionalMongoRepository extends CrudRepository<NonFunctional, String> {
}
