package com.letrasypapeles.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {

    private MessageResponse messageResponse;

    @BeforeEach
    void setUp() {
        messageResponse = new MessageResponse();
        messageResponse.setMessage("Test message");
    }

    @Test
    void testGettersAndSetters() {
        // Test getter
        assertEquals("Test message", messageResponse.getMessage());

        // Test setter
        messageResponse.setMessage("New message");
        assertEquals("New message", messageResponse.getMessage());
    }

    @Test
    void testEquals() {
        MessageResponse response1 = new MessageResponse("Test message");
        MessageResponse response2 = new MessageResponse("Test message");
        MessageResponse response3 = new MessageResponse("Different message");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response1, null);
        assertNotEquals(response1, "string");
        assertEquals(response1, response1);
    }

    @Test
    void testHashCode() {
        MessageResponse response1 = new MessageResponse("Test message");
        MessageResponse response2 = new MessageResponse("Test message");

        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testToString() {
        String toString = messageResponse.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("MessageResponse"));
        assertTrue(toString.contains("Test message"));
    }

    @Test
    void testNoArgsConstructor() {
        MessageResponse emptyResponse = new MessageResponse();
        assertNotNull(emptyResponse);
        assertNull(emptyResponse.getMessage());
    }

    @Test
    void testAllArgsConstructor() {
        MessageResponse fullResponse = new MessageResponse("Constructor message");
        assertEquals("Constructor message", fullResponse.getMessage());
    }

    @Test
    void testCanEqual() {
        MessageResponse response1 = new MessageResponse();
        MessageResponse response2 = new MessageResponse();
        
        assertTrue(response1.canEqual(response2));
        assertFalse(response1.canEqual("string"));
        assertFalse(response1.canEqual(null));
    }

    @Test
    void testNullMessage() {
        messageResponse.setMessage(null);
        assertNull(messageResponse.getMessage());
    }

    @Test
    void testEmptyMessage() {
        messageResponse.setMessage("");
        assertEquals("", messageResponse.getMessage());
    }

    @Test
    void testLongMessage() {
        String longMessage = "This is a very long message that contains a lot of text and could be used to test how the MessageResponse handles longer strings without any issues";
        messageResponse.setMessage(longMessage);
        assertEquals(longMessage, messageResponse.getMessage());
    }

    @Test
    void testSpecialCharacters() {
        String specialMessage = "Message with special chars: áéíóú ñ @#$%^&*()";
        messageResponse.setMessage(specialMessage);
        assertEquals(specialMessage, messageResponse.getMessage());
    }

    @Test
    void testMultilineMessage() {
        String multilineMessage = "Line 1\nLine 2\nLine 3";
        messageResponse.setMessage(multilineMessage);
        assertEquals(multilineMessage, messageResponse.getMessage());
    }

    @Test
    void testWhitespaceMessage() {
        String whitespaceMessage = "   Message with spaces   ";
        messageResponse.setMessage(whitespaceMessage);
        assertEquals(whitespaceMessage, messageResponse.getMessage());
    }

    @Test
    void testNumericMessage() {
        String numericMessage = "12345";
        messageResponse.setMessage(numericMessage);
        assertEquals(numericMessage, messageResponse.getMessage());
    }

    @Test
    void testJsonLikeMessage() {
        String jsonMessage = "{\"status\":\"success\",\"data\":\"value\"}";
        messageResponse.setMessage(jsonMessage);
        assertEquals(jsonMessage, messageResponse.getMessage());
    }

    @Test
    void testEqualsWithNullMessage() {
        MessageResponse response1 = new MessageResponse();
        MessageResponse response2 = new MessageResponse();
        
        assertEquals(response1, response2);
        
        response1.setMessage("test");
        assertNotEquals(response1, response2);
        
        response2.setMessage("test");
        assertEquals(response1, response2);
    }

    @Test
    void testHashCodeWithNullMessage() {
        MessageResponse response1 = new MessageResponse();
        MessageResponse response2 = new MessageResponse();
        
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testSuccessMessages() {
        String[] successMessages = {
            "Operation completed successfully",
            "User registered successfully",
            "Login successful",
            "Data saved successfully",
            "Request processed"
        };

        for (String message : successMessages) {
            messageResponse.setMessage(message);
            assertEquals(message, messageResponse.getMessage());
        }
    }

    @Test
    void testErrorMessages() {
        String[] errorMessages = {
            "Invalid credentials",
            "User not found",
            "Access denied",
            "Internal server error",
            "Validation failed"
        };

        for (String message : errorMessages) {
            messageResponse.setMessage(message);
            assertEquals(message, messageResponse.getMessage());
        }
    }

    @Test
    void testUnicodeMessage() {
        String unicodeMessage = "Unicode test: 🚀 ✅ ❌ 💡 🔥";
        messageResponse.setMessage(unicodeMessage);
        assertEquals(unicodeMessage, messageResponse.getMessage());
    }

    @Test
    void testTabsAndNewlines() {
        String complexMessage = "Message\twith\ttabs\nand\nnewlines\r\nand\r\ncarriage\r\nreturns";
        messageResponse.setMessage(complexMessage);
        assertEquals(complexMessage, messageResponse.getMessage());
    }
}
