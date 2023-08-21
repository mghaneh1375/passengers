package bogen.studio.passengers.Enum;

public enum AgeType {


    ADULT("بزرگسال"), CHILD("کودک"), INFANT("نوزاد");

    private String faValue;

    AgeType(String fa) {
        faValue = fa;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public String toFarsi() {
        return faValue;
    }

}
