package ch.com.mazad.repository;

import ch.com.mazad.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Chemakh on 27/06/2017.
 */
public interface RegionRepository extends JpaRepository<Region, Long>
{
    Optional<Region> findOneByReference(String reference);
}
