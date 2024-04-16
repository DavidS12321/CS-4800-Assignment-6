import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class ChatHistoryIterator implements Iterator<Message> {
    private final List<Message> messages;
    private final User userToSearchWith;
    private int currentIndex = 0;

    public ChatHistoryIterator(List<Message> messages, User userToSearchWith) {
        this.messages = messages;
        this.userToSearchWith = userToSearchWith;
    }

    @Override
    public boolean hasNext() {
        while (currentIndex < messages.size()) {
            Message currentMessage = messages.get(currentIndex);
            if (currentMessage.sender.equals(userToSearchWith.getName()) || currentMessage.recipients.contains(userToSearchWith.getName())) {
                return true;
            }
            currentIndex++;
        }
        return false;
    }

    @Override
    public Message next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return messages.get(currentIndex++);
    }
}