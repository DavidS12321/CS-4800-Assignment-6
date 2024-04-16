import java.util.*;

class MessageMemento {
    String sender;
    List<String> recipients;
    String content;
    String timestamp;

    public MessageMemento(String sender, List<String> recipients, String content, String timestamp) {
        this.sender = sender;
        this.recipients = new ArrayList<>(recipients);
        this.content = content;
        this.timestamp = timestamp;
    }
}