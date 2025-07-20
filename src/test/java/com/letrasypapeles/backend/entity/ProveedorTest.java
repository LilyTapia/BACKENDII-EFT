package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProveedorTest {

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        proveedor = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .contacto("contacto@test.com")
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, proveedor.getId());
        assertEquals("Proveedor Test", proveedor.getNombre());
        assertEquals("contacto@test.com", proveedor.getContacto());

        // Test setters
        proveedor.setId(2L);
        proveedor.setNombre("Nuevo Proveedor");
        proveedor.setContacto("nuevo@test.com");

        assertEquals(2L, proveedor.getId());
        assertEquals("Nuevo Proveedor", proveedor.getNombre());
        assertEquals("nuevo@test.com", proveedor.getContacto());
    }

    // No productos relationship in this entity

    @Test
    void testEquals() {
        Proveedor proveedor1 = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .build();

        Proveedor proveedor2 = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .build();

        Proveedor proveedor3 = Proveedor.builder()
                .id(2L)
                .nombre("Otro Proveedor")
                .build();

        // Test equals
        assertEquals(proveedor1, proveedor2);
        assertNotEquals(proveedor1, proveedor3);
        assertNotEquals(proveedor1, null);
        assertNotEquals(proveedor1, "string");
        assertEquals(proveedor1, proveedor1);
    }

    @Test
    void testHashCode() {
        Proveedor proveedor1 = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .build();

        Proveedor proveedor2 = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .build();

        assertEquals(proveedor1.hashCode(), proveedor2.hashCode());
    }

    @Test
    void testToString() {
        String toString = proveedor.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Proveedor"));
        assertTrue(toString.contains("Proveedor Test"));
    }

    @Test
    void testBuilder() {
        Proveedor proveedorBuilder = Proveedor.builder()
                .id(3L)
                .nombre("Proveedor Builder")
                .contacto("builder@test.com")
                .build();

        assertEquals(3L, proveedorBuilder.getId());
        assertEquals("Proveedor Builder", proveedorBuilder.getNombre());
        assertEquals("builder@test.com", proveedorBuilder.getContacto());
    }

    @Test
    void testNoArgsConstructor() {
        Proveedor proveedorVacio = new Proveedor();
        assertNotNull(proveedorVacio);
        assertNull(proveedorVacio.getId());
        assertNull(proveedorVacio.getNombre());
    }

    @Test
    void testAllArgsConstructor() {
        Proveedor proveedorCompleto = new Proveedor(
                4L,
                "Proveedor Completo",
                "completo@test.com"
        );

        assertEquals(4L, proveedorCompleto.getId());
        assertEquals("Proveedor Completo", proveedorCompleto.getNombre());
        assertEquals("completo@test.com", proveedorCompleto.getContacto());
    }

    @Test
    void testBuilderMethods() {
        // Test individual builder methods
        Proveedor.ProveedorBuilder builder = Proveedor.builder();

        Proveedor proveedorFromBuilder = builder
                .id(5L)
                .nombre("Proveedor Individual")
                .contacto("individual@test.com")
                .build();

        assertEquals(5L, proveedorFromBuilder.getId());
        assertEquals("Proveedor Individual", proveedorFromBuilder.getNombre());
        assertEquals("individual@test.com", proveedorFromBuilder.getContacto());
    }

    @Test
    void testBuilderToString() {
        Proveedor.ProveedorBuilder builder = Proveedor.builder()
                .id(1L)
                .nombre("Test");
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("ProveedorBuilder"));
    }

    @Test
    void testCanEqual() {
        Proveedor proveedor1 = new Proveedor();
        Proveedor proveedor2 = new Proveedor();

        assertTrue(proveedor1.canEqual(proveedor2));
        assertFalse(proveedor1.canEqual("string"));
        assertFalse(proveedor1.canEqual(null));
    }

    @Test
    void testEqualsWithNullFields() {
        Proveedor proveedor1 = new Proveedor();
        Proveedor proveedor2 = new Proveedor();

        // Both null
        assertEquals(proveedor1, proveedor2);

        // One has id, other doesn't
        proveedor1.setId(1L);
        assertNotEquals(proveedor1, proveedor2);
        assertNotEquals(proveedor2, proveedor1);

        // Both have same id
        proveedor2.setId(1L);
        assertEquals(proveedor1, proveedor2);

        // Different nombre
        proveedor1.setNombre("Test nombre");
        assertNotEquals(proveedor1, proveedor2);

        // Same nombre
        proveedor2.setNombre("Test nombre");
        assertEquals(proveedor1, proveedor2);

        // Different contacto
        proveedor1.setContacto("test@example.com");
        assertNotEquals(proveedor1, proveedor2);

        // Same contacto
        proveedor2.setContacto("test@example.com");
        assertEquals(proveedor1, proveedor2);
    }

    @Test
    void testHashCodeWithNullFields() {
        Proveedor proveedor1 = new Proveedor();
        Proveedor proveedor2 = new Proveedor();

        // Both null - should have same hash
        assertEquals(proveedor1.hashCode(), proveedor2.hashCode());

        // Add fields one by one
        proveedor1.setId(1L);
        proveedor2.setId(1L);
        assertEquals(proveedor1.hashCode(), proveedor2.hashCode());

        proveedor1.setNombre("Test nombre");
        proveedor2.setNombre("Test nombre");
        assertEquals(proveedor1.hashCode(), proveedor2.hashCode());

        proveedor1.setContacto("test@example.com");
        proveedor2.setContacto("test@example.com");
        assertEquals(proveedor1.hashCode(), proveedor2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        Proveedor proveedor1 = Proveedor.builder()
                .id(1L)
                .nombre("Test nombre")
                .contacto("test@example.com")
                .build();

        // Test with different types
        assertNotEquals(proveedor1, new Object());
        assertNotEquals(proveedor1, 123);
        assertNotEquals(proveedor1, "string");

        // Test with null
        assertNotEquals(proveedor1, null);

        // Test reflexivity
        assertEquals(proveedor1, proveedor1);

        // Test with different field values
        Proveedor proveedorDifferentId = Proveedor.builder()
                .id(2L)
                .nombre("Test nombre")
                .contacto("test@example.com")
                .build();
        assertNotEquals(proveedor1, proveedorDifferentId);

        Proveedor proveedorDifferentNombre = Proveedor.builder()
                .id(1L)
                .nombre("Otro nombre")
                .contacto("test@example.com")
                .build();
        assertNotEquals(proveedor1, proveedorDifferentNombre);

        Proveedor proveedorDifferentContacto = Proveedor.builder()
                .id(1L)
                .nombre("Test nombre")
                .contacto("otro@example.com")
                .build();
        assertNotEquals(proveedor1, proveedorDifferentContacto);
    }

    @Test
    void testNullValues() {
        proveedor.setNombre(null);
        proveedor.setContacto(null);

        assertNull(proveedor.getNombre());
        assertNull(proveedor.getContacto());
    }

    @Test
    void testEmptyValues() {
        proveedor.setNombre("");
        proveedor.setContacto("");

        assertEquals("", proveedor.getNombre());
        assertEquals("", proveedor.getContacto());
    }
}
