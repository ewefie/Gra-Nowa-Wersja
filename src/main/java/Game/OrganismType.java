package Game;

public enum OrganismType {
    ANTELOPE("ANTELOPE"),
    DANDELION("DANDELION"),
    FOX("FOX"),
    GRASS("GRASS"),
    GUARANA("GUARANA"),
    SHEEP("SHEEP"),
    TORTOISE("TORTOISE"),
    WOLF("WOLF"),
    WOLFBERRY("WOLFBERRY");

    private final String value;

    OrganismType(String value) {
        this.value = value;
    }

    public static OrganismType fromValue(String value) {
        if (value != null) {
            for (OrganismType organismType : values()) {
                if (organismType.value.equals(value)) {
                    return organismType;
                }
            }
        }
        throw new IllegalArgumentException("Invalid color: " + value);
    }

    public String toValue() {
        return value;
    }
}
