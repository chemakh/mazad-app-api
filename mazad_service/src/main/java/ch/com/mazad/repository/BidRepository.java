package ch.com.mazad.repository;

import ch.com.mazad.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by Chemakh on 22/06/2017.
 */
public interface BidRepository extends JpaRepository<Bid,Long> {

    Optional<Bid> findOneByReference(String ref);

    Optional<Bid> findOneByReferenceAndArticleReference(String ref, String refArticle);

    Optional<Bid> findOneByReferenceAndArticleReferenceAndUserReference(String ref, String refArticle, String refUser);

    List<Bid> findByArticleReferenceAndUserReference(String refArticle, String refUser);

    List<Bid> findByArticleReference(String refArticle);

    List<Bid> findByArticleCategoryReferenceAndUserReference(String catRef, String userRef);

    List<Bid> findByUserReference(String userRef);


    @Query(value = "select b.id from bid b where b.reference=?1",nativeQuery = true)
    Optional<Long> findIdByReference(String ref);
}
