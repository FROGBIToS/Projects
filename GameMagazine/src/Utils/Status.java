package Utils;

public enum Status {
    TRUE("True"),
    FALSE("False"),
    W_TRAKCIE("W trakcie"),
    DEFAULT("Default");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return DEFAULT;
    }
}

