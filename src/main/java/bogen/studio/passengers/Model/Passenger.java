package bogen.studio.passengers.Model;


import bogen.studio.passengers.Enum.AgeType;
import bogen.studio.passengers.Enum.Sex;
import bogen.studio.passengers.Enum.StayStatus;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "passenger")
public class Passenger {

    @Id
    @MongoId
    @Field("_id")
    private ObjectId _id;

    @Field("user_id")
    private ObjectId userId;

    @Field("name_fa")
    private String nameFa;

    @Field("name_en")
    private String nameEn;

    @Field("last_name_fa")
    private String lastNameFa;

    @Field("last_name_en")
    private String lastNameEn;

    @Field("NID")
    private String NID;

    @Field("passport_no")
    private String passportNo;

    @Field("citizen_no")
    private String citizenNo;

    @Field("stay_status")
    private StayStatus stayStatus;

    private String citizenship;

    @Field("age_type")
    private AgeType ageType = AgeType.ADULT;

    private Sex sex;
    private String phone;
    private String mail;

    @Field("created_at")
    @CreatedDate
    private Date createdAt;

    @Field("removed_at")
    private Date removedAt;
}
