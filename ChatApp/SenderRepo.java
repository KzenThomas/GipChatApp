package ChatApp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ChatApp.Sender;

@Repository
public interface SenderRepo extends CrudRepository<Sender, String>{
	
	@Query(value = "Select * from Sender", nativeQuery = true)
	Iterable<Sender> getAllSender();

}	