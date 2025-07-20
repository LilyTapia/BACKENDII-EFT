package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private Cliente cliente;
    private List<Producto> productos;
    private LocalDateTime fecha;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@test.com")
                .build();

        productos = new ArrayList<>();
        Producto producto1 = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .precio(new BigDecimal("5.99"))
                .build();
        Producto producto2 = Producto.builder()
                .id(2L)
                .nombre("Lapiz")
                .precio(new BigDecimal("1.50"))
                .build();
        productos.add(producto1);
        productos.add(producto2);

        fecha = LocalDateTime.now();

        pedido = Pedido.builder()
                .id(1L)
                .fecha(fecha)
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(productos)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, pedido.getId());
        assertEquals(fecha, pedido.getFecha());
        assertEquals("PENDIENTE", pedido.getEstado());
        assertEquals(cliente, pedido.getCliente());
        assertEquals(productos, pedido.getListaProductos());

        // Test setters
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(2L);
        nuevoCliente.setNombre("Maria");

        List<Producto> nuevosProductos = new ArrayList<>();
        Producto nuevoProducto = new Producto();
        nuevoProducto.setId(3L);
        nuevoProducto.setNombre("Borrador");
        nuevosProductos.add(nuevoProducto);

        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);

        pedido.setId(2L);
        pedido.setFecha(nuevaFecha);
        pedido.setEstado("COMPLETADO");
        pedido.setCliente(nuevoCliente);
        pedido.setListaProductos(nuevosProductos);

        assertEquals(2L, pedido.getId());
        assertEquals(nuevaFecha, pedido.getFecha());
        assertEquals("COMPLETADO", pedido.getEstado());
        assertEquals(nuevoCliente, pedido.getCliente());
        assertEquals(nuevosProductos, pedido.getListaProductos());
    }

    @Test
    void testEquals() {
        Pedido pedido1 = Pedido.builder()
                .id(1L)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        Pedido pedido2 = Pedido.builder()
                .id(1L)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        Pedido pedido3 = Pedido.builder()
                .id(2L)
                .estado("COMPLETADO")
                .fecha(fecha)
                .build();

        assertEquals(pedido1, pedido2);
        assertNotEquals(pedido1, pedido3);
        assertNotEquals(pedido1, null);
        assertNotEquals(pedido1, "string");
        assertEquals(pedido1, pedido1);
    }

    @Test
    void testHashCode() {
        Pedido pedido1 = Pedido.builder()
                .id(1L)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        Pedido pedido2 = Pedido.builder()
                .id(1L)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void testToString() {
        String toString = pedido.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Pedido"));
        assertTrue(toString.contains("PENDIENTE"));
    }

    @Test
    void testBuilder() {
        LocalDateTime builderFecha = LocalDateTime.now().plusHours(2);
        Pedido pedidoBuilder = Pedido.builder()
                .id(3L)
                .fecha(builderFecha)
                .estado("PROCESANDO")
                .cliente(cliente)
                .listaProductos(productos)
                .build();

        assertEquals(3L, pedidoBuilder.getId());
        assertEquals(builderFecha, pedidoBuilder.getFecha());
        assertEquals("PROCESANDO", pedidoBuilder.getEstado());
        assertEquals(cliente, pedidoBuilder.getCliente());
        assertEquals(productos, pedidoBuilder.getListaProductos());
    }

    @Test
    void testNoArgsConstructor() {
        Pedido pedidoVacio = new Pedido();
        assertNotNull(pedidoVacio);
        assertNull(pedidoVacio.getId());
        assertNull(pedidoVacio.getFecha());
        assertNull(pedidoVacio.getEstado());
        assertNull(pedidoVacio.getCliente());
        assertNull(pedidoVacio.getListaProductos());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime constructorFecha = LocalDateTime.now().plusDays(2);
        Pedido pedidoCompleto = new Pedido(
                4L,
                constructorFecha,
                "ENVIADO",
                cliente,
                productos
        );

        assertEquals(4L, pedidoCompleto.getId());
        assertEquals(constructorFecha, pedidoCompleto.getFecha());
        assertEquals("ENVIADO", pedidoCompleto.getEstado());
        assertEquals(cliente, pedidoCompleto.getCliente());
        assertEquals(productos, pedidoCompleto.getListaProductos());
    }

    @Test
    void testBuilderMethods() {
        Pedido.PedidoBuilder builder = Pedido.builder();
        
        LocalDateTime methodFecha = LocalDateTime.now().plusHours(3);
        Pedido pedidoFromBuilder = builder
                .id(5L)
                .fecha(methodFecha)
                .estado("CANCELADO")
                .cliente(cliente)
                .listaProductos(productos)
                .build();

        assertEquals(5L, pedidoFromBuilder.getId());
        assertEquals(methodFecha, pedidoFromBuilder.getFecha());
        assertEquals("CANCELADO", pedidoFromBuilder.getEstado());
        assertEquals(cliente, pedidoFromBuilder.getCliente());
        assertEquals(productos, pedidoFromBuilder.getListaProductos());
    }

    @Test
    void testBuilderToString() {
        Pedido.PedidoBuilder builder = Pedido.builder()
                .id(1L)
                .estado("TEST");
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("PedidoBuilder"));
    }

    @Test
    void testCanEqual() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        
        assertTrue(pedido1.canEqual(pedido2));
        assertFalse(pedido1.canEqual("string"));
        assertFalse(pedido1.canEqual(null));
    }

    @Test
    void testNullValues() {
        pedido.setFecha(null);
        pedido.setEstado(null);
        pedido.setCliente(null);
        pedido.setListaProductos(null);

        assertNull(pedido.getFecha());
        assertNull(pedido.getEstado());
        assertNull(pedido.getCliente());
        assertNull(pedido.getListaProductos());
    }

    @Test
    void testEmptyProductList() {
        pedido.setListaProductos(new ArrayList<>());
        assertNotNull(pedido.getListaProductos());
        assertTrue(pedido.getListaProductos().isEmpty());
    }

    @Test
    void testProductListManipulation() {
        List<Producto> newProducts = new ArrayList<>();
        Producto producto3 = new Producto();
        producto3.setId(3L);
        producto3.setNombre("Regla");
        newProducts.add(producto3);

        pedido.setListaProductos(newProducts);
        assertEquals(1, pedido.getListaProductos().size());
        assertEquals(producto3, pedido.getListaProductos().get(0));
    }

    @Test
    void testEstadoValues() {
        String[] estados = {"PENDIENTE", "PROCESANDO", "ENVIADO", "ENTREGADO", "CANCELADO"};
        
        for (String estado : estados) {
            pedido.setEstado(estado);
            assertEquals(estado, pedido.getEstado());
        }
    }

    @Test
    void testFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusYears(1);
        pedido.setFecha(futureDate);
        assertEquals(futureDate, pedido.getFecha());
    }

    @Test
    void testPastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);
        pedido.setFecha(pastDate);
        assertEquals(pastDate, pedido.getFecha());
    }

    @Test
    void testEqualsWithNullFields() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        // Both null
        assertEquals(pedido1, pedido2);

        // One has id, other doesn't
        pedido1.setId(1L);
        assertNotEquals(pedido1, pedido2);
        assertNotEquals(pedido2, pedido1);

        // Both have same id
        pedido2.setId(1L);
        assertEquals(pedido1, pedido2);

        // Different dates
        pedido1.setFecha(LocalDateTime.now());
        assertNotEquals(pedido1, pedido2);

        // Same dates
        pedido2.setFecha(pedido1.getFecha());
        assertEquals(pedido1, pedido2);

        // Different estados
        pedido1.setEstado("PENDIENTE");
        assertNotEquals(pedido1, pedido2);

        // Same estados
        pedido2.setEstado("PENDIENTE");
        assertEquals(pedido1, pedido2);

        // Different clientes
        pedido1.setCliente(cliente);
        assertNotEquals(pedido1, pedido2);

        // Same clientes
        pedido2.setCliente(cliente);
        assertEquals(pedido1, pedido2);

        // Different productos
        pedido1.setListaProductos(productos);
        assertNotEquals(pedido1, pedido2);

        // Same productos
        pedido2.setListaProductos(productos);
        assertEquals(pedido1, pedido2);
    }

    @Test
    void testHashCodeWithNullFields() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        // Both null - should have same hash
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        // Add fields one by one
        pedido1.setId(1L);
        pedido2.setId(1L);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        pedido1.setFecha(LocalDateTime.of(2023, 1, 1, 10, 0));
        pedido2.setFecha(LocalDateTime.of(2023, 1, 1, 10, 0));
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        pedido1.setEstado("PENDIENTE");
        pedido2.setEstado("PENDIENTE");
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        pedido1.setCliente(cliente);
        pedido2.setCliente(cliente);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());

        pedido1.setListaProductos(productos);
        pedido2.setListaProductos(productos);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        Pedido pedido1 = Pedido.builder()
                .id(1L)
                .fecha(LocalDateTime.of(2023, 1, 1, 10, 0))
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(productos)
                .build();

        // Test with different types
        assertNotEquals(pedido1, new Object());
        assertNotEquals(pedido1, 123);
        assertNotEquals(pedido1, "string");

        // Test with null
        assertNotEquals(pedido1, null);

        // Test reflexivity
        assertEquals(pedido1, pedido1);

        // Test with different field values
        Pedido pedidoDifferentId = Pedido.builder()
                .id(2L)
                .fecha(LocalDateTime.of(2023, 1, 1, 10, 0))
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(productos)
                .build();
        assertNotEquals(pedido1, pedidoDifferentId);

        Pedido pedidoDifferentFecha = Pedido.builder()
                .id(1L)
                .fecha(LocalDateTime.of(2023, 1, 2, 10, 0))
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(productos)
                .build();
        assertNotEquals(pedido1, pedidoDifferentFecha);

        Pedido pedidoDifferentEstado = Pedido.builder()
                .id(1L)
                .fecha(LocalDateTime.of(2023, 1, 1, 10, 0))
                .estado("COMPLETADO")
                .cliente(cliente)
                .listaProductos(productos)
                .build();
        assertNotEquals(pedido1, pedidoDifferentEstado);
    }
}
