import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class UserTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private User david;
    private User alina;
    private ChatServer chatServer;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        chatServer = new ChatServer();
        david = new User("David", chatServer);
        alina = new User("Alina", chatServer);
        chatServer.registerUser(david);
        chatServer.registerUser(alina);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testSendMessage() {
        david.sendMessage(Arrays.asList("Alina"), "Hello Alina!");
        assertTrue(outContent.toString().contains("Alina received from David: Hello Alina!"));
    }

    @Test
    public void testReceiveMessage() {
        Message message = new Message("Alina", Arrays.asList("David"), "Hi David", new Date().toString());
        david.receiveMessage(message);
        assertTrue(outContent.toString().contains("David received from Alina: Hi David"));
    }

    @Test
    public void testUndoLastMessage() {
        david.sendMessage(Arrays.asList("Alina"), "First message");
        outContent.reset();
        david.undoLastMessage();
        assertTrue(outContent.toString().contains("David successfully undid the last message"));
        assertFalse(outContent.toString().contains("First message"));
    }

    @Test
    public void testShowChatHistory() {
        outContent.reset();

        david.sendMessage(Arrays.asList("Alina"), "Checking history");

        outContent.reset();
        david.showChatHistory();

        String output = outContent.toString();
        assertTrue("Expected history output not found, got: " + output, output.contains("Checking history"));
    }

    @Test
    public void testBlockUser() {
        alina.blockUser("David");
        david.sendMessage(Arrays.asList("Alina"), "You shouldn't see this");
        assertFalse(outContent.toString().contains("Alina received from David: You shouldn't see this"));
    }

    @Test
    public void testNotifyUndo() {
        MessageMemento memento = new MessageMemento("David", Arrays.asList("Alina"), "Undo this message", new Date().toString());
        alina.notifyUndo("David", memento);
        assertTrue(outContent.toString().contains("Alina notified to disregard message from David"));
    }

    @Test
    public void testMessageIteration() {
        david.sendMessage(Arrays.asList("Alina"), "Hello, Alina!");
        david.sendMessage(Arrays.asList("Alina"), "How are you, Alina?");

        int count = 0;
        for (Message message : david.searchMessagesByUser(alina)) {
            count++;
            assertTrue(message.toString().contains("Alina"));
        }
        assertEquals(2, count);
    }
}