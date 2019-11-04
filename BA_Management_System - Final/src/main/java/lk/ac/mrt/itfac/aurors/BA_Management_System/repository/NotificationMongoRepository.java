package lk.ac.mrt.itfac.aurors.BA_Management_System.repository;

import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationMongoRepository extends CrudRepository<Notification, String>{
}
