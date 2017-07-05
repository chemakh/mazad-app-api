package ch.com.mazad.repository;

import ch.com.mazad.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Created by Chemakh on 22/06/2017.
 */
public interface SaleRepository extends JpaRepository<Sale,Long> {

    Optional<Sale> findOneByReference(String ref);

    @Query(value = "select b.id from sale b where b.reference=?1",nativeQuery = true)
    Optional<BigInteger> findIdByReference(String ref);
}
