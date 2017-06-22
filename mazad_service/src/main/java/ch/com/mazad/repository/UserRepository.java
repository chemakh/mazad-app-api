package ch.com.mazad.repository;

import ch.com.mazad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created by Chemakh on 22/06/2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByReference(String ref);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByResetPasswordKey(String key);

    Optional<User> findOneByEmailKey(String key);

    @Query(value = "select us.reference from user us where us.email=?1",nativeQuery = true)
    Optional<String> getUserReferenceByEmail(String email);

    @Query(value = "select us.id from user us where b.reference=?1", nativeQuery = true)
    Optional<Long> findIdByReference(String ref);

}
