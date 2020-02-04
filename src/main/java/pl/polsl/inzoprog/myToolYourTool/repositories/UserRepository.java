package pl.polsl.inzoprog.myToolYourTool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByName(String name);

    List<User> findAll();
}
