package bogen.studio.passengers.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trip")
public class Trip {

    @Id
    @MongoId
    @Field("_id")
    private ObjectId _id;

    @Field("user_id")
    private ObjectId userId;

    @Field("created_by")
    private ObjectId createdBy;

    List<ObjectId> persons;

    @Field("created_at")
    @CreatedDate
    private Date createdAt;

}
