package kh.edu.cstad.idenity.features.user;

import kh.edu.cstad.idenity.domain.UserAuthority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {

    Set<UserAuthority> findByUserId(Long id);

}
