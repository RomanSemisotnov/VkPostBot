package myPackage.entities;

import myPackage.enums.Action;

import java.util.*;

public class Keyboard {

    private static int DEFAULT_COUNT_BUTTON_ON_LINE = 2;
    private boolean one_time = true;
    private boolean inline = false;
    private List<List<TextButton>> buttons;

    public Keyboard(Integer AttachmentId) {
        buttons = new ArrayList<>();

        String readPayload = "{ \"attachment_id\" : " + AttachmentId + ", \"isRead\": \"yes\" "
                + ", \"prevAction\": \"" + Action.GET_ATTACHMENT_BY_INDEX.name() + "\" } ";
        String nonReadPayload = "{ \"attachment_id\" : " + AttachmentId + ", \"isRead\": \"yes\" "
                + ", \"prevAction\": \"" + Action.GET_ATTACHMENT_BY_INDEX.name() + "\" } ";
        TextButton read = new TextButton("Я прочитал", readPayload);
        TextButton nonRead = new TextButton("Я не прочитал", nonReadPayload);
        buttons.add(Arrays.asList(read, nonRead));
    }

    public static Keyboard keyboardForGetTopic(List<Topic> topics) {
        return new Keyboard(topics, Action.GET_TOPIC_BY_COMMAND);
    }

    public static Keyboard keyboardForSetTopic(List<Topic> topics) {
        return new Keyboard(topics, Action.SET_ATTACHMENT_NAME);
    }

    private Keyboard(List<Topic> topics, Action prevAction) {
        buttons = new ArrayList<>();

        if (topics != null) {
            List<TextButton> buffer = new ArrayList<>();
            Topic current;
            int size = topics.size();
            for (int i = 0; i < size; i++) {
                current = topics.get(i);
                buffer.add(new TextButton(current.getName(), "{ \"topic_id\" : " + current.getId()
                        + ", \"prevAction\": \"" + prevAction.name() + "\" } "));
                if ((i != 0 && (i + 1) % DEFAULT_COUNT_BUTTON_ON_LINE == 0) || i == size - 1) {
                    buttons.add(buffer);
                    buffer = new ArrayList<>();
                }
            }
        }
    }

    public boolean isOne_time() {
        return one_time;
    }

    public void setOne_time(boolean one_time) {
        this.one_time = one_time;
    }

    public boolean isInline() {
        return inline;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
    }

    public List<List<TextButton>> getButtons() {
        return buttons;
    }

    public void setButtons(List<List<TextButton>> buttons) {
        this.buttons = buttons;
    }

}
