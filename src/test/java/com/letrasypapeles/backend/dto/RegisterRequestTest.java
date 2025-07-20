package com.letrasypapeles.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setNombre("Juan");
        registerRequest.setApellido("Perez");
        registerRequest.setEmail("juan@test.com");
        registerRequest.setPassword("password123");
        registerRequest.setTelefono("123456789");
        registerRequest.setDireccion("Calle 123");
        registerRequest.setRoles(Set.of("CLIENTE"));
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals("Juan", registerRequest.getNombre());
        assertEquals("Perez", registerRequest.getApellido());
        assertEquals("juan@test.com", registerRequest.getEmail());
        assertEquals("password123", registerRequest.getPassword());
        assertEquals("123456789", registerRequest.getTelefono());
        assertEquals("Calle 123", registerRequest.getDireccion());
        assertEquals(Set.of("CLIENTE"), registerRequest.getRoles());

        // Test setters
        registerRequest.setNombre("Maria");
        registerRequest.setApellido("Garcia");
        registerRequest.setEmail("maria@test.com");
        registerRequest.setPassword("newpassword456");
        registerRequest.setTelefono("987654321");
        registerRequest.setDireccion("Avenida 456");
        registerRequest.setRoles(Set.of("ADMIN"));

        assertEquals("Maria", registerRequest.getNombre());
        assertEquals("Garcia", registerRequest.getApellido());
        assertEquals("maria@test.com", registerRequest.getEmail());
        assertEquals("newpassword456", registerRequest.getPassword());
        assertEquals("987654321", registerRequest.getTelefono());
        assertEquals("Avenida 456", registerRequest.getDireccion());
        assertEquals(Set.of("ADMIN"), registerRequest.getRoles());
    }

    @Test
    void testEquals() {
        RegisterRequest request1 = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));
        RegisterRequest request2 = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));
        RegisterRequest request3 = new RegisterRequest("Maria", "Garcia", "maria@test.com", "password456", "987654321", "Avenida 456", Set.of("ADMIN"));

        // Test equals
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request1, null);
        assertNotEquals(request1, "string");
        assertEquals(request1, request1);
    }

    @Test
    void testHashCode() {
        RegisterRequest request1 = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));
        RegisterRequest request2 = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));

        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testToString() {
        String toString = registerRequest.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("RegisterRequest"));
        assertTrue(toString.contains("Juan"));
        assertTrue(toString.contains("Perez"));
        assertTrue(toString.contains("juan@test.com"));
    }

    @Test
    void testNoArgsConstructor() {
        RegisterRequest requestVacio = new RegisterRequest();
        assertNotNull(requestVacio);
        assertNull(requestVacio.getNombre());
        assertNull(requestVacio.getApellido());
        assertNull(requestVacio.getEmail());
        assertNull(requestVacio.getPassword());
        assertNull(requestVacio.getTelefono());
        assertNull(requestVacio.getDireccion());
        assertNull(requestVacio.getRoles());
    }

    @Test
    void testAllArgsConstructor() {
        RegisterRequest requestCompleto = new RegisterRequest(
                "Ana",
                "Martinez",
                "ana@test.com",
                "mypassword321",
                "555123456",
                "Calle Nueva 789",
                Set.of("VENDEDOR")
        );

        assertEquals("Ana", requestCompleto.getNombre());
        assertEquals("Martinez", requestCompleto.getApellido());
        assertEquals("ana@test.com", requestCompleto.getEmail());
        assertEquals("mypassword321", requestCompleto.getPassword());
        assertEquals("555123456", requestCompleto.getTelefono());
        assertEquals("Calle Nueva 789", requestCompleto.getDireccion());
        assertEquals(Set.of("VENDEDOR"), requestCompleto.getRoles());
    }

    @Test
    void testWithNullValues() {
        RegisterRequest requestNulo = new RegisterRequest();
        requestNulo.setNombre(null);
        requestNulo.setApellido(null);
        requestNulo.setEmail(null);
        requestNulo.setPassword(null);
        requestNulo.setTelefono(null);
        requestNulo.setDireccion(null);
        requestNulo.setRoles(null);

        assertNull(requestNulo.getNombre());
        assertNull(requestNulo.getApellido());
        assertNull(requestNulo.getEmail());
        assertNull(requestNulo.getPassword());
        assertNull(requestNulo.getTelefono());
        assertNull(requestNulo.getDireccion());
        assertNull(requestNulo.getRoles());
    }

    @Test
    void testWithEmptyValues() {
        RegisterRequest requestVacio = new RegisterRequest();
        requestVacio.setNombre("");
        requestVacio.setApellido("");
        requestVacio.setEmail("");
        requestVacio.setPassword("");
        requestVacio.setTelefono("");
        requestVacio.setDireccion("");
        requestVacio.setRoles(new HashSet<>());

        assertEquals("", requestVacio.getNombre());
        assertEquals("", requestVacio.getApellido());
        assertEquals("", requestVacio.getEmail());
        assertEquals("", requestVacio.getPassword());
        assertEquals("", requestVacio.getTelefono());
        assertEquals("", requestVacio.getDireccion());
        assertEquals(new HashSet<>(), requestVacio.getRoles());
    }

    @Test
    void testEqualsWithNullFields() {
        RegisterRequest request1 = new RegisterRequest();
        RegisterRequest request2 = new RegisterRequest();

        assertEquals(request1, request2);

        request1.setNombre("Juan");
        assertNotEquals(request1, request2);

        request2.setNombre("Juan");
        assertEquals(request1, request2);
    }

    @Test
    void testHashCodeWithNullFields() {
        RegisterRequest request1 = new RegisterRequest();
        RegisterRequest request2 = new RegisterRequest();

        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testEqualsComprehensive() {
        RegisterRequest request1 = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));

        // Test with different field values
        RegisterRequest requestDifferentNombre = new RegisterRequest("Maria", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));
        assertNotEquals(request1, requestDifferentNombre);

        RegisterRequest requestDifferentApellido = new RegisterRequest("Juan", "Garcia", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));
        assertNotEquals(request1, requestDifferentApellido);

        RegisterRequest requestDifferentEmail = new RegisterRequest("Juan", "Perez", "maria@test.com", "password123", "123456789", "Calle 123", Set.of("CLIENTE"));
        assertNotEquals(request1, requestDifferentEmail);

        RegisterRequest requestDifferentPassword = new RegisterRequest("Juan", "Perez", "juan@test.com", "password456", "123456789", "Calle 123", Set.of("CLIENTE"));
        assertNotEquals(request1, requestDifferentPassword);

        RegisterRequest requestDifferentTelefono = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "987654321", "Calle 123", Set.of("CLIENTE"));
        assertNotEquals(request1, requestDifferentTelefono);

        RegisterRequest requestDifferentDireccion = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Avenida 456", Set.of("CLIENTE"));
        assertNotEquals(request1, requestDifferentDireccion);

        RegisterRequest requestDifferentRoles = new RegisterRequest("Juan", "Perez", "juan@test.com", "password123", "123456789", "Calle 123", Set.of("ADMIN"));
        assertNotEquals(request1, requestDifferentRoles);
    }

    @Test
    void testEqualsWithNullFieldsComprehensive() {
        RegisterRequest request1 = new RegisterRequest();
        RegisterRequest request2 = new RegisterRequest();

        // Both null
        assertEquals(request1, request2);

        // Test each field individually
        request1.setNombre("Juan");
        assertNotEquals(request1, request2);
        request2.setNombre("Juan");
        assertEquals(request1, request2);

        request1.setApellido("Perez");
        assertNotEquals(request1, request2);
        request2.setApellido("Perez");
        assertEquals(request1, request2);

        request1.setEmail("juan@test.com");
        assertNotEquals(request1, request2);
        request2.setEmail("juan@test.com");
        assertEquals(request1, request2);

        request1.setPassword("password123");
        assertNotEquals(request1, request2);
        request2.setPassword("password123");
        assertEquals(request1, request2);

        request1.setTelefono("123456789");
        assertNotEquals(request1, request2);
        request2.setTelefono("123456789");
        assertEquals(request1, request2);

        request1.setDireccion("Calle 123");
        assertNotEquals(request1, request2);
        request2.setDireccion("Calle 123");
        assertEquals(request1, request2);

        request1.setRoles(Set.of("CLIENTE"));
        assertNotEquals(request1, request2);
        request2.setRoles(Set.of("CLIENTE"));
        assertEquals(request1, request2);
    }

    @Test
    void testCanEqual() {
        RegisterRequest request1 = new RegisterRequest();
        RegisterRequest request2 = new RegisterRequest();

        assertTrue(request1.canEqual(request2));
        assertFalse(request1.canEqual("string"));
        assertFalse(request1.canEqual(null));
    }
}
