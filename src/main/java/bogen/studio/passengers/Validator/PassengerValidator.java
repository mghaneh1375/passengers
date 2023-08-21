package bogen.studio.passengers.Validator;

import bogen.studio.passengers.DTO.PassengerDTO;
import bogen.studio.passengers.Enum.AgeType;
import bogen.studio.passengers.Enum.Sex;
import bogen.studio.passengers.Enum.StayStatus;
import org.json.JSONObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PassengerValidator implements ConstraintValidator<ValidatedPassenger, PassengerDTO> {

    private static final String regex = "^[a-zA-Z]+$";
    private static final Pattern pattern = Pattern.compile(regex);
    private static final Pattern mailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern phonePattern = Pattern.compile("^(09)\\d{9}$");

    @Override
    public void initialize(ValidatedPassenger constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    public boolean validationNationalCode(String code) {

        if (code.length() != 10)
            return false;

        try {
            long nationalCode = Long.parseLong(code);
            byte[] arrayNationalCode = new byte[10];

            //extract digits from number
            for (int i = 0; i < 10; i++) {
                arrayNationalCode[i] = (byte) (nationalCode % 10);
                nationalCode = nationalCode / 10;
            }

            //Checking the control digit
            int sum = 0;
            for (int i = 9; i > 0; i--)
                sum += arrayNationalCode[i] * (i + 1);
            int temp = sum % 11;
            if (temp < 2)
                return arrayNationalCode[0] == temp;
            else
                return arrayNationalCode[0] == 11 - temp;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isValid(PassengerDTO passengerDTO, ConstraintValidatorContext context) {

        boolean isErrored = false;
        JSONObject errs = new JSONObject();

        if(passengerDTO.getNameFa() == null || passengerDTO.getNameFa().length() < 3) {
            errs.put("nameFa", "لطفا نام فارسی را وارد نمایید");
            isErrored = true;
        }

        if(passengerDTO.getLastNameFa() == null || passengerDTO.getLastNameFa().length() < 3) {
            errs.put("lastNameFa", "لطفا نام خانوادگی فارسی را وارد نمایید");
            isErrored = true;
        }

        if(passengerDTO.getNameEn() == null || passengerDTO.getNameEn().length() < 3) {
            errs.put("nameEn", "لطفا نام انگلیسی را وارد نمایید");
            isErrored = true;
        }

        if(passengerDTO.getLastNameEn() == null || passengerDTO.getLastNameEn().length() < 3) {
            errs.put("lastNameEn", "لطفا نام خانوادگی انگلیسی را وارد نمایید");
            isErrored = true;
        }

        if(passengerDTO.getNameEn() != null &&
                !pattern.matcher(passengerDTO.getNameEn().replace(" ", "")).matches()
        ) {
            errs.put("nameEn", "نام انگلیسی معتبر نمی باشد");
            isErrored = true;
        }

        if(passengerDTO.getLastNameEn() != null &&
                !pattern.matcher(passengerDTO.getLastNameEn().replace(" ", "")).matches()) {
            errs.put("lastNameEn", "نام خانوادگی انگلیسی معتبر نمی باشد");
            isErrored = true;
        }

        if(passengerDTO.getStayStatus() == null || !EnumValidatorImp.isValid(passengerDTO.getStayStatus(), StayStatus.class)) {
            errs.put("stayStatus", "نام خانوادگی انگلیسی معتبر نمی باشد");
            isErrored = true;
        }

        if(passengerDTO.getStayStatus() != null) {
            if (passengerDTO.getStayStatus().equalsIgnoreCase(StayStatus.IRANI.getName())) {

                if (passengerDTO.getCitizenship() != null) {
                    errs.put("citizenship", "تابعیت نامعتبر است");
                    isErrored = true;
                }

                if (passengerDTO.getNID() == null ||
                        !validationNationalCode(passengerDTO.getNID())
                ) {
                    errs.put("NID", "کد ملی نامعتبر است");
                    isErrored = true;
                }

            } else {

                if (passengerDTO.getCitizenship() == null || passengerDTO.getCitizenship().length() < 3) {
                    errs.put("citizenship", "لطفا تابعیت را وارد نمایید");
                    isErrored = true;
                }

                if (passengerDTO.getStayStatus().equalsIgnoreCase(StayStatus.FOREIGN.getName()) &&
                        (passengerDTO.getPassportNo() == null || passengerDTO.getPassportNo().length() < 9)
                ) {
                    errs.put("passportNo", "شماره پاسپورت نامعتبر است");
                    isErrored = true;
                }

                if (passengerDTO.getStayStatus().equalsIgnoreCase(StayStatus.CITIZEN.getName()) &&
                        (passengerDTO.getCitizenNo() == null || passengerDTO.getCitizenNo().length() < 4)
                ) {
                    errs.put("citizenNo", "کد اتباع نامعتبر است");
                    isErrored = true;
                }

            }
        }

        if(passengerDTO.getAgeType() == null || !EnumValidatorImp.isValid(passengerDTO.getAgeType(), AgeType.class)) {
            errs.put("ageType", "سن وارد شده معتبر نمی باشد");
            isErrored = true;
        }

        if(passengerDTO.getSex() == null || !EnumValidatorImp.isValid(passengerDTO.getSex(), Sex.class)) {
            errs.put("sex", "جنسیت وارد شده معتبر نمی باشد");
            isErrored = true;
        }

        if(passengerDTO.getPhone() != null &&
            !phonePattern.matcher(passengerDTO.getPhone()).matches()
        ) {
            errs.put("sex", "شماره همراه وارد شده معتبر نمی باشد");
            isErrored = true;
        }

        if(passengerDTO.getMail() != null &&
                !mailPattern.matcher(passengerDTO.getMail()).matches()
        ) {
            errs.put("sex", "ایمیل وارد شده معتبر نمی باشد");
            isErrored = true;
        }

        if(isErrored) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errs.toString()).addConstraintViolation();
        }

        return !isErrored;

    }
}
