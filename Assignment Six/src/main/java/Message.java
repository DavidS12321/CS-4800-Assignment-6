import java.util.*;

class Message {
    String sender;
    List<String> recipients;
    String timestamp;
    String content;

    public Message(String sender, List<String> recipients, String content, String timestamp) {
        this.sender = sender;
        this.recipients = new ArrayList<>(recipients);
        this.content = content;
        this.timestamp = timestamp;
    }

    public String toString() {
        return String.format("[%s] %s: %s", timestamp, sender, content);
    }
}