import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class ChatServerTest {
    private ChatServer chatServer;
    private User david;
    private User alina;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        chatServer = new ChatServer();
        david = new User("David", chatServer);
        alina = new User("Alina", chatServer);
    }

    @Test
    public void testRegisterAndUnregisterUser() {
        chatServer.registerUser(david);
        assertTrue(outContent.toString().contains("David has joined the chat."));

        chatServer.unregisterUser(david);
        assertTrue(outContent.toString().contains("David has left the chat."));
    }

    @Test
    public void testSendMessage() {
        chatServer.registerUser(david);
        chatServer.registerUser(alina);
        Message message = new Message("David", Arrays.asList("Alina"), "Hello Alina!", new Date().toString());
        chatServer.sendMessage(message);

        assertTrue(outContent.toString().contains("Alina received from David: Hello Alina!"));
    }

    @Test
    public void testBlockSender() {
        chatServer.registerUser(david);
        chatServer.registerUser(alina);
        chatServer.blockSender("Alina", "David");

        Message message = new Message("David", Arrays.asList("Alina"), "Hello Alina!", new Date().toString());
        chatServer.sendMessage(message);

        assertFalse(outContent.toString().contains("Alina received from David: Hello Alina!"));
        assertTrue(outContent.toString().contains("Alina has blocked David"));
    }

    @Test
    public void testUndoMessage() {
        chatServer.registerUser(david);
        chatServer.registerUser(alina);
        Message message = new Message("David", Arrays.asList("Alina"), "Hello Alina!", new Date().toString());
        MessageMemento memento = new MessageMemento("David", Arrays.asList("Alina"), "Hello Alina!", new Date().toString());

        chatServer.sendMessage(message);
        chatServer.undoMessage(david, memento);

        assertTrue(outContent.toString().contains("Alina notified to disregard message from David"));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}