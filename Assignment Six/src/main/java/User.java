import java.util.*;

class User implements IterableByUser {
    private String name;
    private ChatHistory history = new ChatHistory();
    private Mediator chatMediator;

    public User(String name, Mediator chatMediator) {
        this.name = name;
        this.chatMediator = chatMediator;
        chatMediator.registerUser(this);
    }

    public String getName() {
        return name;
    }

    public void sendMessage(List<String> recipients, String content) {
        Message message = new Message(this.name, recipients, content, new Date().toString());
        chatMediator.sendMessage(message);
        history.addMessage(message);
    }

    public void receiveMessage(Message message) {
        System.out.println(this.name + " received from " + message.sender + ": " + message.content);
    }

    public void undoLastMessage() {
        if (history.removeLastMemento()) {
            System.out.println(this.name + " successfully undid the last message.");
        } else {
            System.out.println(this.name + " no message to undo.");
        }
    }

    public void showChatHistory() {
        System.out.println("Chat history for " + this.name + ":");
        history.showHistory();
    }

    public void blockUser(String userToBlock) {
        chatMediator.blockSender(this.name, userToBlock);
    }

    public void notifyUndo(String sender, MessageMemento memento) {
        System.out.println(this.name + " notified to disregard message from " + sender + " sent at " + memento.timestamp);
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return history.iterator(userToSearchWith);
    }

    public Iterable<Message> searchMessagesByUser(User userToSearchWith) {
        return () -> iterator(userToSearchWith);
    }

}