package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    private Producto producto;
    private Categoria categoria;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        categoria = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .descripcion("Productos de papeleria")
                .build();

        proveedor = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .contacto("contacto@test.com")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Cuaderno de 100 hojas")
                .precio(new BigDecimal("5.99"))
                .stock(50)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, producto.getId());
        assertEquals("Cuaderno", producto.getNombre());
        assertEquals("Cuaderno de 100 hojas", producto.getDescripcion());
        assertEquals(new BigDecimal("5.99"), producto.getPrecio());
        assertEquals(50, producto.getStock());
        assertEquals(categoria, producto.getCategoria());
        assertEquals(proveedor, producto.getProveedor());

        // Test setters
        producto.setId(2L);
        producto.setNombre("Lapiz");
        producto.setDescripcion("Lapiz HB");
        producto.setPrecio(new BigDecimal("1.50"));
        producto.setStock(100);

        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setId(2L);
        producto.setCategoria(nuevaCategoria);

        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setId(2L);
        producto.setProveedor(nuevoProveedor);

        assertEquals(2L, producto.getId());
        assertEquals("Lapiz", producto.getNombre());
        assertEquals("Lapiz HB", producto.getDescripcion());
        assertEquals(new BigDecimal("1.50"), producto.getPrecio());
        assertEquals(100, producto.getStock());
        assertEquals(nuevaCategoria, producto.getCategoria());
        assertEquals(nuevoProveedor, producto.getProveedor());
    }

    // No collection relationships in this entity

    @Test
    void testEquals() {
        Producto producto1 = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        Producto producto2 = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        Producto producto3 = Producto.builder()
                .id(2L)
                .nombre("Lapiz")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        // Test equals
        assertEquals(producto1, producto2);
        assertNotEquals(producto1, producto3);
        assertNotEquals(producto1, null);
        assertNotEquals(producto1, "string");
        assertEquals(producto1, producto1);

        // Test with null fields
        Producto productoConNulls = new Producto();
        Producto otroProductoConNulls = new Producto();
        assertEquals(productoConNulls, otroProductoConNulls);
        assertNotEquals(producto1, productoConNulls);

        // Test different fields
        Producto differentNombre = Producto.builder()
                .id(1L)
                .nombre("Different")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();
        assertNotEquals(producto1, differentNombre);

        Producto differentDesc = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Different")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();
        assertNotEquals(producto1, differentDesc);

        Producto differentPrecio = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("20.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();
        assertNotEquals(producto1, differentPrecio);

        Producto differentStock = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(10)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();
        assertNotEquals(producto1, differentStock);

        Categoria otraCategoria = Categoria.builder().id(2L).nombre("Otra").build();
        Producto differentCategoria = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(otraCategoria)
                .proveedor(proveedor)
                .build();
        assertNotEquals(producto1, differentCategoria);

        Proveedor otroProveedor = Proveedor.builder().id(2L).nombre("Otro").build();
        Producto differentProveedor = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(otroProveedor)
                .build();
        assertNotEquals(producto1, differentProveedor);
    }

    @Test
    void testHashCode() {
        Producto producto1 = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        Producto producto2 = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        Producto producto3 = Producto.builder()
                .id(2L)
                .nombre("Different")
                .descripcion("Different")
                .precio(new BigDecimal("20.00"))
                .stock(10)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        assertEquals(producto1.hashCode(), producto2.hashCode());
        assertNotEquals(producto1.hashCode(), producto3.hashCode());

        // Test with null fields
        Producto productoConNulls = new Producto();
        Producto otroProductoConNulls = new Producto();
        assertEquals(productoConNulls.hashCode(), otroProductoConNulls.hashCode());

        // Test hashCode consistency
        int hashCode1 = producto1.hashCode();
        int hashCode2 = producto1.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testToString() {
        String toString = producto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Producto"));
        assertTrue(toString.contains("Cuaderno"));
    }

    @Test
    void testBuilder() {
        Producto productoBuilder = Producto.builder()
                .id(3L)
                .nombre("Borrador")
                .descripcion("Borrador blanco")
                .precio(new BigDecimal("0.75"))
                .stock(200)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        assertEquals(3L, productoBuilder.getId());
        assertEquals("Borrador", productoBuilder.getNombre());
        assertEquals("Borrador blanco", productoBuilder.getDescripcion());
        assertEquals(new BigDecimal("0.75"), productoBuilder.getPrecio());
        assertEquals(200, productoBuilder.getStock());
        assertEquals(categoria, productoBuilder.getCategoria());
        assertEquals(proveedor, productoBuilder.getProveedor());
    }

    @Test
    void testNoArgsConstructor() {
        Producto productoVacio = new Producto();
        assertNotNull(productoVacio);
        assertNull(productoVacio.getId());
        assertNull(productoVacio.getNombre());
    }

    @Test
    void testAllArgsConstructor() {
        Producto productoCompleto = new Producto(
                4L,
                "Regla",
                "Regla de 30cm",
                new BigDecimal("2.50"),
                75,
                categoria,
                proveedor
        );

        assertEquals(4L, productoCompleto.getId());
        assertEquals("Regla", productoCompleto.getNombre());
        assertEquals("Regla de 30cm", productoCompleto.getDescripcion());
        assertEquals(new BigDecimal("2.50"), productoCompleto.getPrecio());
        assertEquals(75, productoCompleto.getStock());
        assertEquals(categoria, productoCompleto.getCategoria());
        assertEquals(proveedor, productoCompleto.getProveedor());
    }

    @Test
    void testCanEqual() {
        Producto otroProducto = new Producto();
        assertTrue(producto.canEqual(otroProducto));
        assertFalse(producto.canEqual("not a producto"));
        assertFalse(producto.canEqual(null));
        assertTrue(producto.canEqual(producto));
    }

    @Test
    void testEqualsWithNullValues() {
        // Test productos with some null values
        Producto producto1 = Producto.builder()
                .id(null)
                .nombre(null)
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(null)
                .proveedor(null)
                .build();

        Producto producto2 = Producto.builder()
                .id(null)
                .nombre(null)
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(null)
                .proveedor(null)
                .build();

        assertEquals(producto1, producto2);
        assertEquals(producto1.hashCode(), producto2.hashCode());

        // Test one with null, other with value
        Producto producto3 = Producto.builder()
                .id(1L)
                .nombre("Test")
                .descripcion("Desc")
                .precio(new BigDecimal("10.00"))
                .stock(5)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        assertNotEquals(producto1, producto3);
        assertNotEquals(producto3, producto1);
    }

    @Test
    void testBuilderToString() {
        Producto.ProductoBuilder builder = Producto.builder()
                .id(1L)
                .nombre("Test Producto")
                .precio(new BigDecimal("10.00"));

        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("ProductoBuilder"));
    }

    @Test
    void testBuilderMethods() {
        Producto.ProductoBuilder builder = Producto.builder();

        Producto productoFromBuilder = builder
                .id(5L)
                .nombre("Producto Builder")
                .descripcion("Descripcion Builder")
                .precio(new BigDecimal("15.99"))
                .stock(50)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        assertEquals(5L, productoFromBuilder.getId());
        assertEquals("Producto Builder", productoFromBuilder.getNombre());
        assertEquals("Descripcion Builder", productoFromBuilder.getDescripcion());
        assertEquals(new BigDecimal("15.99"), productoFromBuilder.getPrecio());
        assertEquals(50, productoFromBuilder.getStock());
        assertEquals(categoria, productoFromBuilder.getCategoria());
        assertEquals(proveedor, productoFromBuilder.getProveedor());
    }
}
