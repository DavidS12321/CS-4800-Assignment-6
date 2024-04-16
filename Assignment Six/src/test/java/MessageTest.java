import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageToString() {
        Message message = new Message("Sender", Arrays.asList("Recipient"), "Hello", "timestamp");
        String expectedString = "[timestamp] Sender: Hello";
        assertEquals(expectedString, message.toString());
    }
}