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
public interface ArticleRepository extends JpaRepository<Article, Long>
{
    Optional<Article> findOneByReference(String ref);

    List<Article> findByDeletedIsFalseAndSoldAndValidityDurationGreaterThan(boolean sold, Integer zero);

    List<Article> findByCategoryReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(String catRef, boolean sold, Integer zero);

    List<Article> findByCategoryReferenceAndCreatedByReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan
            (String catRef, String userRef, boolean sold, Integer zero);

    List<Article> findByCreatedByReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(String userRef, boolean sold, Integer zero);

    @Query(value = "select art.id from article art where art.reference=?1", nativeQuery = true)
    Optional<BigInteger> findIdByReference(String ref);

    List<Article> findByCategoryReferenceAndCreatedByReferenceAndLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan
            (String categoryRef, String userRef, String label, boolean sold, Integer zero);

    List<Article> findByCategoryReferenceAndLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(String categoryRef, String label, boolean sold, Integer zero);

    List<Article> findByCreatedByReferenceAndLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(String userRef, String label, boolean sold, Integer zero);

    List<Article> findByLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(String label, boolean sold, Integer zero);

    List<Article> findByCategoryReference(String reference);
}
