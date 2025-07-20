package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    private Inventario inventario;
    private Producto producto;
    private Sucursal sucursal;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .precio(new BigDecimal("5.99"))
                .build();

        sucursal = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Calle 123")
                .region("Metropolitana")
                .build();

        inventario = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(10)
                .producto(producto)
                .sucursal(sucursal)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, inventario.getId());
        assertEquals(100, inventario.getCantidad());
        assertEquals(10, inventario.getUmbral());
        assertEquals(producto, inventario.getProducto());
        assertEquals(sucursal, inventario.getSucursal());

        // Test setters
        Producto nuevoProducto = new Producto();
        nuevoProducto.setId(2L);
        nuevoProducto.setNombre("Lapiz");

        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setId(2L);
        nuevaSucursal.setNombre("Sucursal Norte");

        inventario.setId(2L);
        inventario.setCantidad(50);
        inventario.setUmbral(5);
        inventario.setProducto(nuevoProducto);
        inventario.setSucursal(nuevaSucursal);

        assertEquals(2L, inventario.getId());
        assertEquals(50, inventario.getCantidad());
        assertEquals(5, inventario.getUmbral());
        assertEquals(nuevoProducto, inventario.getProducto());
        assertEquals(nuevaSucursal, inventario.getSucursal());
    }

    @Test
    void testEquals() {
        Inventario inventario1 = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(10)
                .build();

        Inventario inventario2 = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(10)
                .build();

        Inventario inventario3 = Inventario.builder()
                .id(2L)
                .cantidad(50)
                .umbral(5)
                .build();

        assertEquals(inventario1, inventario2);
        assertNotEquals(inventario1, inventario3);
        assertNotEquals(inventario1, null);
        assertNotEquals(inventario1, "string");
        assertEquals(inventario1, inventario1);
    }

    @Test
    void testHashCode() {
        Inventario inventario1 = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(10)
                .build();

        Inventario inventario2 = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(10)
                .build();

        assertEquals(inventario1.hashCode(), inventario2.hashCode());
    }

    @Test
    void testToString() {
        String toString = inventario.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Inventario"));
        assertTrue(toString.contains("100"));
        assertTrue(toString.contains("10"));
    }

    @Test
    void testBuilder() {
        Inventario inventarioBuilder = Inventario.builder()
                .id(3L)
                .cantidad(200)
                .umbral(20)
                .producto(producto)
                .sucursal(sucursal)
                .build();

        assertEquals(3L, inventarioBuilder.getId());
        assertEquals(200, inventarioBuilder.getCantidad());
        assertEquals(20, inventarioBuilder.getUmbral());
        assertEquals(producto, inventarioBuilder.getProducto());
        assertEquals(sucursal, inventarioBuilder.getSucursal());
    }

    @Test
    void testNoArgsConstructor() {
        Inventario inventarioVacio = new Inventario();
        assertNotNull(inventarioVacio);
        assertNull(inventarioVacio.getId());
        assertNull(inventarioVacio.getCantidad());
        assertNull(inventarioVacio.getUmbral());
        assertNull(inventarioVacio.getProducto());
        assertNull(inventarioVacio.getSucursal());
    }

    @Test
    void testAllArgsConstructor() {
        Inventario inventarioCompleto = new Inventario(
                4L,
                150,
                15,
                producto,
                sucursal
        );

        assertEquals(4L, inventarioCompleto.getId());
        assertEquals(150, inventarioCompleto.getCantidad());
        assertEquals(15, inventarioCompleto.getUmbral());
        assertEquals(producto, inventarioCompleto.getProducto());
        assertEquals(sucursal, inventarioCompleto.getSucursal());
    }

    @Test
    void testBuilderMethods() {
        Inventario.InventarioBuilder builder = Inventario.builder();
        
        Inventario inventarioFromBuilder = builder
                .id(5L)
                .cantidad(75)
                .umbral(8)
                .producto(producto)
                .sucursal(sucursal)
                .build();

        assertEquals(5L, inventarioFromBuilder.getId());
        assertEquals(75, inventarioFromBuilder.getCantidad());
        assertEquals(8, inventarioFromBuilder.getUmbral());
        assertEquals(producto, inventarioFromBuilder.getProducto());
        assertEquals(sucursal, inventarioFromBuilder.getSucursal());
    }

    @Test
    void testBuilderToString() {
        Inventario.InventarioBuilder builder = Inventario.builder()
                .id(1L)
                .cantidad(100);
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("InventarioBuilder"));
    }

    @Test
    void testCanEqual() {
        Inventario inventario1 = new Inventario();
        Inventario inventario2 = new Inventario();
        
        assertTrue(inventario1.canEqual(inventario2));
        assertFalse(inventario1.canEqual("string"));
        assertFalse(inventario1.canEqual(null));
    }

    @Test
    void testNullValues() {
        inventario.setProducto(null);
        inventario.setSucursal(null);
        inventario.setCantidad(null);
        inventario.setUmbral(null);

        assertNull(inventario.getProducto());
        assertNull(inventario.getSucursal());
        assertNull(inventario.getCantidad());
        assertNull(inventario.getUmbral());
    }

    @Test
    void testZeroValues() {
        inventario.setCantidad(0);
        inventario.setUmbral(0);

        assertEquals(0, inventario.getCantidad());
        assertEquals(0, inventario.getUmbral());
    }

    @Test
    void testNegativeValues() {
        inventario.setCantidad(-10);
        inventario.setUmbral(-5);

        assertEquals(-10, inventario.getCantidad());
        assertEquals(-5, inventario.getUmbral());
    }

    @Test
    void testEqualsWithNullFields() {
        Inventario inventario1 = new Inventario();
        Inventario inventario2 = new Inventario();

        // Both null
        assertEquals(inventario1, inventario2);

        // One has id, other doesn't
        inventario1.setId(1L);
        assertNotEquals(inventario1, inventario2);
        assertNotEquals(inventario2, inventario1);

        // Both have same id
        inventario2.setId(1L);
        assertEquals(inventario1, inventario2);

        // Different cantidad
        inventario1.setCantidad(100);
        assertNotEquals(inventario1, inventario2);

        // Same cantidad
        inventario2.setCantidad(100);
        assertEquals(inventario1, inventario2);

        // Different umbral
        inventario1.setUmbral(10);
        assertNotEquals(inventario1, inventario2);

        // Same umbral
        inventario2.setUmbral(10);
        assertEquals(inventario1, inventario2);

        // Different producto
        inventario1.setProducto(producto);
        assertNotEquals(inventario1, inventario2);

        // Same producto
        inventario2.setProducto(producto);
        assertEquals(inventario1, inventario2);

        // Different sucursal
        inventario1.setSucursal(sucursal);
        assertNotEquals(inventario1, inventario2);

        // Same sucursal
        inventario2.setSucursal(sucursal);
        assertEquals(inventario1, inventario2);
    }

    @Test
    void testHashCodeWithNullFields() {
        Inventario inventario1 = new Inventario();
        Inventario inventario2 = new Inventario();

        // Both null - should have same hash
        assertEquals(inventario1.hashCode(), inventario2.hashCode());

        // Add fields one by one
        inventario1.setId(1L);
        inventario2.setId(1L);
        assertEquals(inventario1.hashCode(), inventario2.hashCode());

        inventario1.setCantidad(100);
        inventario2.setCantidad(100);
        assertEquals(inventario1.hashCode(), inventario2.hashCode());

        inventario1.setUmbral(10);
        inventario2.setUmbral(10);
        assertEquals(inventario1.hashCode(), inventario2.hashCode());

        inventario1.setProducto(producto);
        inventario2.setProducto(producto);
        assertEquals(inventario1.hashCode(), inventario2.hashCode());

        inventario1.setSucursal(sucursal);
        inventario2.setSucursal(sucursal);
        assertEquals(inventario1.hashCode(), inventario2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        Inventario inventario1 = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(10)
                .producto(producto)
                .sucursal(sucursal)
                .build();

        // Test with different types
        assertNotEquals(inventario1, new Object());
        assertNotEquals(inventario1, 123);
        assertNotEquals(inventario1, "string");

        // Test with null
        assertNotEquals(inventario1, null);

        // Test reflexivity
        assertEquals(inventario1, inventario1);

        // Test with different field values
        Inventario inventarioDifferentId = Inventario.builder()
                .id(2L)
                .cantidad(100)
                .umbral(10)
                .producto(producto)
                .sucursal(sucursal)
                .build();
        assertNotEquals(inventario1, inventarioDifferentId);

        Inventario inventarioDifferentCantidad = Inventario.builder()
                .id(1L)
                .cantidad(200)
                .umbral(10)
                .producto(producto)
                .sucursal(sucursal)
                .build();
        assertNotEquals(inventario1, inventarioDifferentCantidad);

        Inventario inventarioDifferentUmbral = Inventario.builder()
                .id(1L)
                .cantidad(100)
                .umbral(20)
                .producto(producto)
                .sucursal(sucursal)
                .build();
        assertNotEquals(inventario1, inventarioDifferentUmbral);
    }
}
