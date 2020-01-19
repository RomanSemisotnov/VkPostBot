package myPackage.entities;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    private boolean one_time = true;
    private boolean inline = false;
    private List<List<TextButton>> buttons;

    public Keyboard(List<TextButton> buttons){
        this.buttons=new ArrayList<>();
        this.buttons.add(buttons);
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
