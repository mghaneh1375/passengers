package bogen.studio.passengers.Repository;

import bogen.studio.passengers.Model.Trip;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends MongoRepository<Trip, ObjectId> {

    @Query(value = "{ 'user_id': ?0, '_id': ?1, 'expire_at': { $gt: ?2 } }")
    Trip findByUserIdAndId(ObjectId userId, ObjectId id, Long curr);


    @Query(value = "{ 'expire_at': { $lt: ?0 } }", delete = true)
    void removeExpiredReservations(Long curr);

}
