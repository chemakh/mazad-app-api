package ch.com.mazad.repository;

import ch.com.mazad.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Chemakh on 27/06/2017.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findOneByName(String name);

    Optional<Category> findOneByReference(String reference);
}
