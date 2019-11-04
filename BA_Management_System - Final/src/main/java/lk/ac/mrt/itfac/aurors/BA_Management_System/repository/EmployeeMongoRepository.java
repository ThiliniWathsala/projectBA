package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeMongoRepository extends CrudRepository<Employee, String> {


}
