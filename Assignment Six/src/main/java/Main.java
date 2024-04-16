import java.util.*;

public class Main {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        User david = new User("David", server);
        User alina = new User("Alina", server);
        User joe = new User("Joe", server);

        System.out.println();

        david.sendMessage(Arrays.asList("Alina", "Joe"), "Hello everyone!");
        alina.sendMessage(Arrays.asList("David"), "Hello David!");
        joe.sendMessage(Arrays.asList("David"), "Whatsup David?");
        david.sendMessage(Arrays.asList("Alina", "Joe"), "Im doing great!");

        System.out.println();

        alina.blockUser("David");
        david.sendMessage(Arrays.asList("Joe"), "I think Alina Blocked me Joe");
        david.sendMessage(Arrays.asList("Alina"), "Alina, did you get this?");

        System.out.println();

        david.showChatHistory();
        System.out.println();
        alina.showChatHistory();
        System.out.println();
        joe.showChatHistory();

        System.out.println();

        david.undoLastMessage();
        joe.undoLastMessage();

        System.out.println();

        System.out.println("Iterating through David's messages");
        for (Message message : david.searchMessagesByUser(joe)) {
            System.out.println(message);
        }

        System.out.println("\n-------Chat history after undo-------\n");
        david.showChatHistory();
        System.out.println();
        joe.showChatHistory();
    }
}