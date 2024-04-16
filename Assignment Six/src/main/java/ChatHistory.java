import java.util.*;

class ChatHistory implements IterableByUser {
    private List<Message> messages = new ArrayList<>();
    private List<MessageMemento> mementos = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
        mementos.add(new MessageMemento(message.sender, message.recipients, message.content, message.timestamp));
    }

    public MessageMemento getLastMemento() {
        if (!mementos.isEmpty()) {
            return mementos.get(mementos.size() - 1);
        }
        return null;
    }

    public boolean removeLastMemento() {
        if (!mementos.isEmpty()) {
            mementos.remove(mementos.size() - 1);
        }
        return removeLastMessage();
    }

    public void showHistory() {
        for (Message msg : messages) {
            System.out.println(msg);
        }
    }

    private boolean removeLastMessage() {
        if (!messages.isEmpty()) {
            messages.remove(messages.size() - 1);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new ChatHistoryIterator(messages, userToSearchWith);
    }

    public int getNumberOfMessages() {
        return messages.size();
    }
}