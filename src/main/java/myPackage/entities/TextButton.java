package myPackage.entities;

public class TextButton {

    private String color = "primary";
    private Action action;

    public TextButton(String label) {
        action = new Action(label);
    }

    public TextButton(String label, String payload) {
        action = new Action(label, payload);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    private static class Action {
        private String type = "text";
        private String label;
        private String payload;

        public Action(String label) {
            this.label = label;
        }

        private Action(String label, String payload) {
            this.label = label;
            this.payload = payload;
        }

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

    }

}
