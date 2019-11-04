package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectMongoRepository extends CrudRepository<Project, String>{
}
