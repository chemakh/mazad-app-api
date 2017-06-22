package ch.com.mazad.repository;

import ch.com.mazad.domain.Bid;
import ch.com.mazad.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created by Chemakh on 22/06/2017.
 */
public interface PhotoRepository extends JpaRepository<Photo,Long> {

    Optional<Photo> findOneByReference(String ref);

    @Query(value = "select b.id from photo b where b.reference=?1",nativeQuery = true)
    Optional<Long> findIdByReference(String ref);
}
