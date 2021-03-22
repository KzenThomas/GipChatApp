package ChatApp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ChatApp.Messages;

@Repository
public interface MessageRepo extends CrudRepository<Messages, String>{
	
	@Query(value = "Select * from Messages", nativeQuery = true)
	Iterable<Messages> getAllMessages();
	

}	