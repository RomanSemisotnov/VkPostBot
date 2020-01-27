package myPackage.enums;

public enum Command {

    ADD_TOPIC("+");

    private String value;
    Command(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
