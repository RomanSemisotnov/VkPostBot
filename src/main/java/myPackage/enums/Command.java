package myPackage.enums;

public enum Command {

    ADD_TOPIC("+");
 // команда на просмотр сообщений и так далее
    private String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
