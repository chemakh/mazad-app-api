package ch.com.mazad.repository;

import ch.com.mazad.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Created by Chemakh on 24/06/2017.
 */
public interface AddressRepository extends JpaRepository<Address, Long>
{
    Optional<Address> findOneByReference(String ref);

    @Query(value = "select ad.id from address ad where ad.reference=?1",nativeQuery = true)
    Optional<BigInteger> findIdByReference(String ref);
}
