package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .descripcion("Productos de papeleria")
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, categoria.getId());
        assertEquals("Papeleria", categoria.getNombre());
        assertEquals("Productos de papeleria", categoria.getDescripcion());

        // Test setters
        categoria.setId(2L);
        categoria.setNombre("Oficina");
        categoria.setDescripcion("Productos de oficina");

        assertEquals(2L, categoria.getId());
        assertEquals("Oficina", categoria.getNombre());
        assertEquals("Productos de oficina", categoria.getDescripcion());
    }

    // No productos relationship in this entity

    @Test
    void testEquals() {
        Categoria categoria1 = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .build();

        Categoria categoria2 = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .build();

        Categoria categoria3 = Categoria.builder()
                .id(2L)
                .nombre("Oficina")
                .build();

        // Test equals
        assertEquals(categoria1, categoria2);
        assertNotEquals(categoria1, categoria3);
        assertNotEquals(categoria1, null);
        assertNotEquals(categoria1, "string");
        assertEquals(categoria1, categoria1);
    }

    @Test
    void testHashCode() {
        Categoria categoria1 = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .build();

        Categoria categoria2 = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .build();

        assertEquals(categoria1.hashCode(), categoria2.hashCode());
    }

    @Test
    void testToString() {
        String toString = categoria.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Categoria"));
        assertTrue(toString.contains("Papeleria"));
    }

    @Test
    void testBuilder() {
        Categoria categoriaBuilder = Categoria.builder()
                .id(3L)
                .nombre("Tecnologia")
                .descripcion("Productos tecnologicos")
                .build();

        assertEquals(3L, categoriaBuilder.getId());
        assertEquals("Tecnologia", categoriaBuilder.getNombre());
        assertEquals("Productos tecnologicos", categoriaBuilder.getDescripcion());
    }

    @Test
    void testNoArgsConstructor() {
        Categoria categoriaVacia = new Categoria();
        assertNotNull(categoriaVacia);
        assertNull(categoriaVacia.getId());
        assertNull(categoriaVacia.getNombre());
    }

    @Test
    void testAllArgsConstructor() {
        Categoria categoriaCompleta = new Categoria(
                4L,
                "Arte",
                "Productos de arte"
        );

        assertEquals(4L, categoriaCompleta.getId());
        assertEquals("Arte", categoriaCompleta.getNombre());
        assertEquals("Productos de arte", categoriaCompleta.getDescripcion());
    }

    @Test
    void testBuilderMethods() {
        // Test individual builder methods
        Categoria.CategoriaBuilder builder = Categoria.builder();

        Categoria categoriaFromBuilder = builder
                .id(5L)
                .nombre("Deportes")
                .descripcion("Productos deportivos")
                .build();

        assertEquals(5L, categoriaFromBuilder.getId());
        assertEquals("Deportes", categoriaFromBuilder.getNombre());
        assertEquals("Productos deportivos", categoriaFromBuilder.getDescripcion());
    }

    @Test
    void testBuilderToString() {
        Categoria.CategoriaBuilder builder = Categoria.builder()
                .id(1L)
                .nombre("Test");

        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("CategoriaBuilder"));
    }

    @Test
    void testCanEqual() {
        Categoria categoria1 = new Categoria();
        Categoria categoria2 = new Categoria();

        assertTrue(categoria1.canEqual(categoria2));
        assertFalse(categoria1.canEqual("string"));
        assertFalse(categoria1.canEqual(null));
    }

    @Test
    void testEqualsWithNullFields() {
        Categoria categoria1 = new Categoria();
        Categoria categoria2 = new Categoria();

        // Both null
        assertEquals(categoria1, categoria2);

        // One has id, other doesn't
        categoria1.setId(1L);
        assertNotEquals(categoria1, categoria2);
        assertNotEquals(categoria2, categoria1);

        // Both have same id
        categoria2.setId(1L);
        assertEquals(categoria1, categoria2);

        // Different nombre
        categoria1.setNombre("Test nombre");
        assertNotEquals(categoria1, categoria2);

        // Same nombre
        categoria2.setNombre("Test nombre");
        assertEquals(categoria1, categoria2);

        // Different descripcion
        categoria1.setDescripcion("Test descripcion");
        assertNotEquals(categoria1, categoria2);

        // Same descripcion
        categoria2.setDescripcion("Test descripcion");
        assertEquals(categoria1, categoria2);
    }

    @Test
    void testHashCodeWithNullFields() {
        Categoria categoria1 = new Categoria();
        Categoria categoria2 = new Categoria();

        // Both null - should have same hash
        assertEquals(categoria1.hashCode(), categoria2.hashCode());

        // Add fields one by one
        categoria1.setId(1L);
        categoria2.setId(1L);
        assertEquals(categoria1.hashCode(), categoria2.hashCode());

        categoria1.setNombre("Test nombre");
        categoria2.setNombre("Test nombre");
        assertEquals(categoria1.hashCode(), categoria2.hashCode());

        categoria1.setDescripcion("Test descripcion");
        categoria2.setDescripcion("Test descripcion");
        assertEquals(categoria1.hashCode(), categoria2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        Categoria categoria1 = Categoria.builder()
                .id(1L)
                .nombre("Test nombre")
                .descripcion("Test descripcion")
                .build();

        // Test with different types
        assertNotEquals(categoria1, new Object());
        assertNotEquals(categoria1, 123);
        assertNotEquals(categoria1, "string");

        // Test with null
        assertNotEquals(categoria1, null);

        // Test reflexivity
        assertEquals(categoria1, categoria1);

        // Test with different field values
        Categoria categoriaDifferentId = Categoria.builder()
                .id(2L)
                .nombre("Test nombre")
                .descripcion("Test descripcion")
                .build();
        assertNotEquals(categoria1, categoriaDifferentId);

        Categoria categoriaDifferentNombre = Categoria.builder()
                .id(1L)
                .nombre("Otro nombre")
                .descripcion("Test descripcion")
                .build();
        assertNotEquals(categoria1, categoriaDifferentNombre);

        Categoria categoriaDifferentDescripcion = Categoria.builder()
                .id(1L)
                .nombre("Test nombre")
                .descripcion("Otra descripcion")
                .build();
        assertNotEquals(categoria1, categoriaDifferentDescripcion);
    }

    @Test
    void testNullValues() {
        categoria.setNombre(null);
        categoria.setDescripcion(null);

        assertNull(categoria.getNombre());
        assertNull(categoria.getDescripcion());
    }

    @Test
    void testEmptyValues() {
        categoria.setNombre("");
        categoria.setDescripcion("");

        assertEquals("", categoria.getNombre());
        assertEquals("", categoria.getDescripcion());
    }
}
