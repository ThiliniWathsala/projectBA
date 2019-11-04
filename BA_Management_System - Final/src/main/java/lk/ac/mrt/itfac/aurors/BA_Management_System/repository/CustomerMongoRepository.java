package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerMongoRepository extends CrudRepository<Customer, String>{
}
