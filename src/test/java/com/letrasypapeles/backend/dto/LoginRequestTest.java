package com.letrasypapeles.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals("test@example.com", loginRequest.getEmail());
        assertEquals("password123", loginRequest.getPassword());

        // Test setters
        loginRequest.setEmail("new@example.com");
        loginRequest.setPassword("newpassword456");

        assertEquals("new@example.com", loginRequest.getEmail());
        assertEquals("newpassword456", loginRequest.getPassword());
    }

    @Test
    void testEquals() {
        LoginRequest request1 = new LoginRequest("test@example.com", "password123");
        LoginRequest request2 = new LoginRequest("test@example.com", "password123");
        LoginRequest request3 = new LoginRequest("other@example.com", "otherpassword");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request1, null);
        assertNotEquals(request1, "string");
        assertEquals(request1, request1);
    }

    @Test
    void testHashCode() {
        LoginRequest request1 = new LoginRequest("test@example.com", "password123");
        LoginRequest request2 = new LoginRequest("test@example.com", "password123");

        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testToString() {
        String toString = loginRequest.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("LoginRequest"));
        assertTrue(toString.contains("test@example.com"));
        // Note: Lombok @Data includes all fields in toString by default
        assertTrue(toString.contains("password123"));
    }

    @Test
    void testNoArgsConstructor() {
        LoginRequest emptyRequest = new LoginRequest();
        assertNotNull(emptyRequest);
        assertNull(emptyRequest.getEmail());
        assertNull(emptyRequest.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        LoginRequest fullRequest = new LoginRequest("user@test.com", "mypassword");

        assertEquals("user@test.com", fullRequest.getEmail());
        assertEquals("mypassword", fullRequest.getPassword());
    }

    @Test
    void testCanEqual() {
        LoginRequest request1 = new LoginRequest();
        LoginRequest request2 = new LoginRequest();
        
        assertTrue(request1.canEqual(request2));
        assertFalse(request1.canEqual("string"));
        assertFalse(request1.canEqual(null));
    }

    @Test
    void testNullValues() {
        loginRequest.setEmail(null);
        loginRequest.setPassword(null);

        assertNull(loginRequest.getEmail());
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testEmptyValues() {
        loginRequest.setEmail("");
        loginRequest.setPassword("");

        assertEquals("", loginRequest.getEmail());
        assertEquals("", loginRequest.getPassword());
    }

    @Test
    void testValidEmails() {
        String[] validEmails = {
            "test@example.com",
            "user.name@domain.co.uk",
            "user+tag@example.org",
            "123@example.com",
            "test.email.with+symbol@example.com"
        };

        for (String email : validEmails) {
            loginRequest.setEmail(email);
            assertEquals(email, loginRequest.getEmail());
        }
    }

    @Test
    void testPasswordLengths() {
        // Test minimum length password
        loginRequest.setPassword("123456");
        assertEquals("123456", loginRequest.getPassword());

        // Test longer password
        loginRequest.setPassword("verylongpasswordwithmanycharacters");
        assertEquals("verylongpasswordwithmanycharacters", loginRequest.getPassword());

        // Test short password (validation would fail but setter works)
        loginRequest.setPassword("12345");
        assertEquals("12345", loginRequest.getPassword());
    }

    @Test
    void testSpecialCharactersInPassword() {
        String specialPassword = "P@ssw0rd!#$%";
        loginRequest.setPassword(specialPassword);
        assertEquals(specialPassword, loginRequest.getPassword());
    }

    @Test
    void testEqualsWithNullFields() {
        LoginRequest request1 = new LoginRequest();
        LoginRequest request2 = new LoginRequest();
        
        assertEquals(request1, request2);
        
        request1.setEmail("test@example.com");
        assertNotEquals(request1, request2);
        
        request2.setEmail("test@example.com");
        assertEquals(request1, request2);
        
        request1.setPassword("password");
        assertNotEquals(request1, request2);
        
        request2.setPassword("password");
        assertEquals(request1, request2);
    }

    @Test
    void testHashCodeWithNullFields() {
        LoginRequest request1 = new LoginRequest();
        LoginRequest request2 = new LoginRequest();
        
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testCaseInsensitiveEmail() {
        loginRequest.setEmail("Test@Example.COM");
        assertEquals("Test@Example.COM", loginRequest.getEmail());
    }

    @Test
    void testWhitespaceInFields() {
        loginRequest.setEmail(" test@example.com ");
        loginRequest.setPassword(" password123 ");

        assertEquals(" test@example.com ", loginRequest.getEmail());
        assertEquals(" password123 ", loginRequest.getPassword());
    }
}
