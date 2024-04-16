import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class ChatHistoryTest {
    private ChatHistory chatHistory;

    @Before
    public void setUp() {
        chatHistory = new ChatHistory();
    }

    @Test
    public void testAddMessage() {
        Message message = new Message("David", Arrays.asList("Alina"), "Hello, Alina!", "2024-04-01T12:00:00");
        chatHistory.addMessage(message);
        assertEquals(1, chatHistory.getNumberOfMessages());
    }

    @Test
    public void testRemoveLastMemento() {
        Message message1 = new Message("David", Arrays.asList("Alina"), "Hello, Alina!", "2024-04-01T12:00:00");
        chatHistory.addMessage(message1);
        chatHistory.removeLastMemento();
        assertEquals(0, chatHistory.getNumberOfMessages());
    }
}