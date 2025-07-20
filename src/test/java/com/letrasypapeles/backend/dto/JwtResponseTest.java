package com.letrasypapeles.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    private JwtResponse jwtResponse;
    private String token;
    private String email;
    private List<String> roles;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.token";
        email = "test@example.com";
        roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        
        jwtResponse = new JwtResponse();
    }

    @Test
    void constructor_NoArgs_CreatesEmptyJwtResponse() {
        // When
        JwtResponse response = new JwtResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertEquals("Bearer", response.getType()); // Default value
        assertNull(response.getEmail());
        assertNull(response.getRoles());
    }

    @Test
    void constructor_AllArgs_CreatesJwtResponseWithAllFields() {
        // When
        JwtResponse response = new JwtResponse(token, "Bearer", email, roles);

        // Then
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(email, response.getEmail());
        assertEquals(roles, response.getRoles());
        assertEquals(2, response.getRoles().size());
        assertTrue(response.getRoles().contains("ROLE_USER"));
        assertTrue(response.getRoles().contains("ROLE_ADMIN"));
    }

    @Test
    void constructor_ThreeArgs_CreatesJwtResponseWithDefaultType() {
        // When
        JwtResponse response = new JwtResponse(token, email, roles);

        // Then
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals("Bearer", response.getType()); // Default value should remain
        assertEquals(email, response.getEmail());
        assertEquals(roles, response.getRoles());
    }

    @Test
    void constructor_ThreeArgs_WithNullValues_CreatesJwtResponse() {
        // When
        JwtResponse response = new JwtResponse(null, null, null);

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertEquals("Bearer", response.getType()); // Default value should remain
        assertNull(response.getEmail());
        assertNull(response.getRoles());
    }

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Given
        String newToken = "new.jwt.token";
        String newType = "Custom";
        String newEmail = "new@example.com";
        List<String> newRoles = Arrays.asList("ROLE_CLIENTE");

        // When & Then
        jwtResponse.setToken(newToken);
        assertEquals(newToken, jwtResponse.getToken());

        jwtResponse.setType(newType);
        assertEquals(newType, jwtResponse.getType());

        jwtResponse.setEmail(newEmail);
        assertEquals(newEmail, jwtResponse.getEmail());

        jwtResponse.setRoles(newRoles);
        assertEquals(newRoles, jwtResponse.getRoles());
        assertEquals(1, jwtResponse.getRoles().size());
        assertTrue(jwtResponse.getRoles().contains("ROLE_CLIENTE"));
    }

    @Test
    void setToken_WithNull_SetsNull() {
        // When
        jwtResponse.setToken(null);

        // Then
        assertNull(jwtResponse.getToken());
    }

    @Test
    void setToken_WithEmptyString_SetsEmptyString() {
        // When
        jwtResponse.setToken("");

        // Then
        assertEquals("", jwtResponse.getToken());
    }

    @Test
    void setType_WithNull_SetsNull() {
        // When
        jwtResponse.setType(null);

        // Then
        assertNull(jwtResponse.getType());
    }

    @Test
    void setType_WithCustomValue_SetsCustomValue() {
        // Given
        String customType = "CustomBearer";

        // When
        jwtResponse.setType(customType);

        // Then
        assertEquals(customType, jwtResponse.getType());
    }

    @Test
    void setEmail_WithNull_SetsNull() {
        // When
        jwtResponse.setEmail(null);

        // Then
        assertNull(jwtResponse.getEmail());
    }

    @Test
    void setEmail_WithEmptyString_SetsEmptyString() {
        // When
        jwtResponse.setEmail("");

        // Then
        assertEquals("", jwtResponse.getEmail());
    }

    @Test
    void setRoles_WithNull_SetsNull() {
        // When
        jwtResponse.setRoles(null);

        // Then
        assertNull(jwtResponse.getRoles());
    }

    @Test
    void setRoles_WithEmptyList_SetsEmptyList() {
        // Given
        List<String> emptyRoles = Arrays.asList();

        // When
        jwtResponse.setRoles(emptyRoles);

        // Then
        assertNotNull(jwtResponse.getRoles());
        assertTrue(jwtResponse.getRoles().isEmpty());
    }

    @Test
    void setRoles_WithSingleRole_SetsSingleRole() {
        // Given
        List<String> singleRole = Arrays.asList("ROLE_USER");

        // When
        jwtResponse.setRoles(singleRole);

        // Then
        assertEquals(1, jwtResponse.getRoles().size());
        assertTrue(jwtResponse.getRoles().contains("ROLE_USER"));
    }

    @Test
    void setRoles_WithMultipleRoles_SetsMultipleRoles() {
        // Given
        List<String> multipleRoles = Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_CLIENTE");

        // When
        jwtResponse.setRoles(multipleRoles);

        // Then
        assertEquals(3, jwtResponse.getRoles().size());
        assertTrue(jwtResponse.getRoles().contains("ROLE_USER"));
        assertTrue(jwtResponse.getRoles().contains("ROLE_ADMIN"));
        assertTrue(jwtResponse.getRoles().contains("ROLE_CLIENTE"));
    }

    @Test
    void defaultType_IsBearer() {
        // Given
        JwtResponse response = new JwtResponse();

        // When & Then
        assertEquals("Bearer", response.getType());
    }

    @Test
    void toString_ContainsAllFields() {
        // Given
        jwtResponse.setToken(token);
        jwtResponse.setType("Bearer");
        jwtResponse.setEmail(email);
        jwtResponse.setRoles(roles);

        // When
        String toStringResult = jwtResponse.toString();

        // Then
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains(token));
        assertTrue(toStringResult.contains("Bearer"));
        assertTrue(toStringResult.contains(email));
        assertTrue(toStringResult.contains("ROLE_USER"));
        assertTrue(toStringResult.contains("ROLE_ADMIN"));
    }

    @Test
    void equals_SameObject_ReturnsTrue() {
        // When & Then
        assertTrue(jwtResponse.equals(jwtResponse));
    }

    @Test
    void equals_NullObject_ReturnsFalse() {
        // When & Then
        assertFalse(jwtResponse.equals(null));
    }

    @Test
    void equals_DifferentClass_ReturnsFalse() {
        // Given
        Object otherObject = "Not a JwtResponse";

        // When & Then
        assertFalse(jwtResponse.equals(otherObject));
    }

    @Test
    void equals_SameValues_ReturnsTrue() {
        // Given
        JwtResponse response1 = new JwtResponse(token, email, roles);
        JwtResponse response2 = new JwtResponse(token, email, roles);

        // When & Then
        assertTrue(response1.equals(response2));
    }

    @Test
    void hashCode_SameValues_ReturnsSameHashCode() {
        // Given
        JwtResponse response1 = new JwtResponse(token, email, roles);
        JwtResponse response2 = new JwtResponse(token, email, roles);

        // When & Then
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void constructor_AllArgs_WithNullType_SetsNullType() {
        // When
        JwtResponse response = new JwtResponse(token, null, email, roles);

        // Then
        assertEquals(token, response.getToken());
        assertNull(response.getType());
        assertEquals(email, response.getEmail());
        assertEquals(roles, response.getRoles());
    }

    @Test
    void constructor_AllArgs_WithEmptyType_SetsEmptyType() {
        // When
        JwtResponse response = new JwtResponse(token, "", email, roles);

        // Then
        assertEquals(token, response.getToken());
        assertEquals("", response.getType());
        assertEquals(email, response.getEmail());
        assertEquals(roles, response.getRoles());
    }

    @Test
    void testEqualsWithNullFields() {
        JwtResponse response1 = new JwtResponse();
        JwtResponse response2 = new JwtResponse();

        // Both null
        assertEquals(response1, response2);

        // One has token, other doesn't
        response1.setToken("token1");
        assertNotEquals(response1, response2);
        assertNotEquals(response2, response1);

        // Both have same token
        response2.setToken("token1");
        assertEquals(response1, response2);

        // Different types (note: default type is "Bearer", so we need to set different values)
        response1.setType("Basic");
        assertNotEquals(response1, response2);

        // Same types
        response2.setType("Basic");
        assertEquals(response1, response2);

        // Different emails
        response1.setEmail("test@example.com");
        assertNotEquals(response1, response2);

        // Same emails
        response2.setEmail("test@example.com");
        assertEquals(response1, response2);

        // Different roles
        List<String> roles1 = Arrays.asList("ROLE_USER");
        response1.setRoles(roles1);
        assertNotEquals(response1, response2);

        // Same roles
        response2.setRoles(roles1);
        assertEquals(response1, response2);
    }

    @Test
    void testHashCodeWithNullFields() {
        JwtResponse response1 = new JwtResponse();
        JwtResponse response2 = new JwtResponse();

        // Both null - should have same hash
        assertEquals(response1.hashCode(), response2.hashCode());

        // Add fields one by one
        response1.setToken("token1");
        response2.setToken("token1");
        assertEquals(response1.hashCode(), response2.hashCode());

        response1.setType("Bearer");
        response2.setType("Bearer");
        assertEquals(response1.hashCode(), response2.hashCode());

        response1.setEmail("test@example.com");
        response2.setEmail("test@example.com");
        assertEquals(response1.hashCode(), response2.hashCode());

        List<String> roles1 = Arrays.asList("ROLE_USER");
        response1.setRoles(roles1);
        response2.setRoles(roles1);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        JwtResponse response1 = new JwtResponse("token1", "Bearer", "test@example.com", Arrays.asList("ROLE_USER"));

        // Test with different types
        assertNotEquals(response1, new Object());
        assertNotEquals(response1, 123);
        assertNotEquals(response1, "string");

        // Test with null
        assertNotEquals(response1, null);

        // Test reflexivity
        assertEquals(response1, response1);

        // Test with different field values
        JwtResponse responseDifferentToken = new JwtResponse("token2", "Bearer", "test@example.com", Arrays.asList("ROLE_USER"));
        assertNotEquals(response1, responseDifferentToken);

        JwtResponse responseDifferentType = new JwtResponse("token1", "Basic", "test@example.com", Arrays.asList("ROLE_USER"));
        assertNotEquals(response1, responseDifferentType);

        JwtResponse responseDifferentEmail = new JwtResponse("token1", "Bearer", "other@example.com", Arrays.asList("ROLE_USER"));
        assertNotEquals(response1, responseDifferentEmail);

        JwtResponse responseDifferentRoles = new JwtResponse("token1", "Bearer", "test@example.com", Arrays.asList("ROLE_ADMIN"));
        assertNotEquals(response1, responseDifferentRoles);
    }

    @Test
    void testHashCodeWithDifferentValues() {
        JwtResponse response1 = new JwtResponse("token1", "Bearer", "test@example.com", Arrays.asList("ROLE_USER"));
        JwtResponse response2 = new JwtResponse("token2", "Bearer", "test@example.com", Arrays.asList("ROLE_USER"));

        // Different tokens should have different hash codes (most likely)
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testCanEqual() {
        JwtResponse response1 = new JwtResponse();
        JwtResponse response2 = new JwtResponse();

        assertTrue(response1.canEqual(response2));
        assertFalse(response1.canEqual("string"));
        assertFalse(response1.canEqual(null));
    }
}
