package bogen.studio.passengers.Enum;

public enum StayStatus {

    IRANI("ایرانی"), CITIZEN("اتباع"), FOREIGN("خارجی");

    private String faValue;

    StayStatus(String fa) {
        faValue = fa;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public String toFarsi() {
        return faValue;
    }

}
