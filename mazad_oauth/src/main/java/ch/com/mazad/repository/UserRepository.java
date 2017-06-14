package ch.com.mazad.repository;

import ch.com.mazad.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{

    Optional<User> findOneByEmail(String email);


}
