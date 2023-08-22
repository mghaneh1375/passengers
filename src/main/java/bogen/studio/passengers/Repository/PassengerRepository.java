package bogen.studio.passengers.Repository;

import bogen.studio.passengers.Model.Passenger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PassengerRepository extends MongoRepository<Passenger, ObjectId> {

    @Query(value = "{ 'user_id': ?0, $or: [ { 'NID': ?1 }, { 'passport_no': ?1 }, { 'citizen_no': ?1 } ] }")
    Passenger findByUnique(ObjectId userId, String unique);

    @Query(value = "{ 'user_id': ?0, 'removed_at': { $exists: false } }", fields = "{ 'user_id': 0, 'created_at': 0, 'removed_at': 0 }")
    List<Passenger> findByUserId(ObjectId userId);

    @Query(value = "{ '_id': { $in: ?0 } }", fields = "{ '_id': 0, 'user_id': 0, 'created_at': 0, 'removed_at': 0 }")
    List<Passenger> findByIdsIn(List<ObjectId> ids);

    @Query(value = "{ 'user_id': ?0, '_id': ?1 }")
    Passenger findByUserIdAndId(ObjectId userId, ObjectId id);

}
