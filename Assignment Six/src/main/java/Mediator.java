interface Mediator {
    void sendMessage(Message message);
    void registerUser(User user);
    void unregisterUser(User user);
    void undoMessage(User user, MessageMemento memento);
    void blockSender(String blocker, String blockee);
}