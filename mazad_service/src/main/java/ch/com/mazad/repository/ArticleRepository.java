package ch.com.mazad.repository;

import ch.com.mazad.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Created by Chemakh on 22/06/2017.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findOneByReference(String ref);

    List<Article> findByDeletedIsFalseAndSoldIsFalse();

    List<Article> findByCategoryReference(String catRef);

    List<Article> findByCategoryReferenceAndCreatedByReferenceAndDeletedIsFalse(String catRef, String userRef);

    List<Article> findByCreatedByReferenceAndDeletedIsFalse(String userRef);

    @Query(value = "select art.id from article art where art.reference=?1", nativeQuery = true)
    Optional<BigInteger> findIdByReference(String ref);
}
