

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private MessageType type;
    private String text;
    private Date date;

    public Message(MessageType type, String text) {
        this.type = type;
        this.text = text;
        this.date = new Date();
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }
}
