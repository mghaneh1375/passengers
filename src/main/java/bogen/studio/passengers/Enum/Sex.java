package bogen.studio.passengers.Enum;

public enum Sex {


    MALE("آقا"), FEMALE("خانم");

    private String faValue;

    Sex(String fa) {
        faValue = fa;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public String toFarsi() {
        return faValue;
    }

}
