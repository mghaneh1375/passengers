package bogen.studio.passengers.Repository;


import bogen.studio.passengers.Model.CommonUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the {@link CommonUser} JPA entity. Any custom methods, not
 * already defined in {@link MongoRepository}, are to be defined here
 */
@Repository
public interface UserRepository extends MongoRepository<CommonUser, String> {

	@Query(value = "{'username' : ?0 }")
	Optional<CommonUser> findByUsername(String username);

	@Query(value = "{'email' : ?0 }")
	Optional<CommonUser> findByEmail(String email);

}
