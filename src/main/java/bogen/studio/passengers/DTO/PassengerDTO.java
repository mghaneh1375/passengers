package bogen.studio.passengers.DTO;


import bogen.studio.passengers.Validator.ValidatedPassenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ValidatedPassenger
public class PassengerDTO {

    private String nameFa;
    private String nameEn;
    private String lastNameFa;
    private String lastNameEn;
    private String NID;
    private String passportNo;
    private String citizenNo;
    private String stayStatus;
    private String citizenship;
    private String ageType;
    private String sex;
    private String mail;
    private String phone;
}
