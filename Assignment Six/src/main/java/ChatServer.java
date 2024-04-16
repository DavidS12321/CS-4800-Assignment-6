import java.util.*;

class ChatServer implements Mediator {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Set<String>> blockLists = new HashMap<>();

    @Override
    public void registerUser(User user) {
        users.put(user.getName(), user);
        blockLists.put(user.getName(), new HashSet<>());
        System.out.println(user.getName() + " has joined the chat.");
    }

    @Override
    public void unregisterUser(User user) {
        users.remove(user.getName());
        blockLists.remove(user.getName());
        System.out.println(user.getName() + " has left the chat.");
    }

    @Override
    public void sendMessage(Message message) {
        List<String> recipients = new ArrayList<>(message.recipients);
        for (String recipient : recipients) {
            if (users.containsKey(recipient) && !blockLists.get(recipient).contains(message.sender)) {
                users.get(recipient).receiveMessage(message);
            }
        }
    }

    @Override
    public void blockSender(String blocker, String blockee) {
        if (blockLists.containsKey(blocker)) {
            blockLists.get(blocker).add(blockee);
            System.out.println(blocker + " has blocked " + blockee);
        }
    }

    @Override
    public void undoMessage(User user, MessageMemento memento) {
        for (String recipient : memento.recipients) {
            if (users.containsKey(recipient)) {
                users.get(recipient).notifyUndo(user.getName(), memento);
            }
        }
    }
}