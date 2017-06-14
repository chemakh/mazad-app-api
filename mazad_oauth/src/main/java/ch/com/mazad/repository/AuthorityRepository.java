package ch.com.mazad.repository;

import ch.com.mazad.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority,Long>
{
}
