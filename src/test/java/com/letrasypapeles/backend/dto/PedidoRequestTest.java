package com.letrasypapeles.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestTest {

    private PedidoRequest pedidoRequest;
    private List<Long> productosIds;
    private LocalDateTime fecha;

    @BeforeEach
    void setUp() {
        productosIds = Arrays.asList(1L, 2L, 3L);
        fecha = LocalDateTime.now();

        pedidoRequest = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productosIds)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, pedidoRequest.getClienteId());
        assertEquals(productosIds, pedidoRequest.getProductosIds());
        assertEquals("PENDIENTE", pedidoRequest.getEstado());
        assertEquals(fecha, pedidoRequest.getFecha());

        // Test setters
        List<Long> nuevosProductos = Arrays.asList(4L, 5L, 6L);
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);
        pedidoRequest.setClienteId(2L);
        pedidoRequest.setProductosIds(nuevosProductos);
        pedidoRequest.setEstado("COMPLETADO");
        pedidoRequest.setFecha(nuevaFecha);

        assertEquals(2L, pedidoRequest.getClienteId());
        assertEquals(nuevosProductos, pedidoRequest.getProductosIds());
        assertEquals("COMPLETADO", pedidoRequest.getEstado());
        assertEquals(nuevaFecha, pedidoRequest.getFecha());
    }

    @Test
    void testEquals() {
        PedidoRequest pedido1 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productosIds)
                .build();

        PedidoRequest pedido2 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productosIds)
                .build();

        PedidoRequest pedido3 = PedidoRequest.builder()
                .clienteId(2L)
                .productosIds(Arrays.asList(7L, 8L))
                .build();

        // Test equals
        assertEquals(pedido1, pedido2);
        assertNotEquals(pedido1, pedido3);
        assertNotEquals(pedido1, null);
        assertNotEquals(pedido1, "string");
        assertEquals(pedido1, pedido1);
    }

    @Test
    void testHashCode() {
        PedidoRequest pedido1 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productosIds)
                .build();

        PedidoRequest pedido2 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productosIds)
                .build();

        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void testToString() {
        String toString = pedidoRequest.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("PedidoRequest"));
        assertTrue(toString.contains("clienteId=1"));
        assertTrue(toString.contains("productosIds"));
    }

    @Test
    void testBuilder() {
        List<Long> productos = Arrays.asList(10L, 11L, 12L);
        PedidoRequest pedidoBuilder = PedidoRequest.builder()
                .clienteId(5L)
                .productosIds(productos)
                .build();

        assertEquals(5L, pedidoBuilder.getClienteId());
        assertEquals(productos, pedidoBuilder.getProductosIds());
    }

    @Test
    void testNoArgsConstructor() {
        PedidoRequest pedidoVacio = new PedidoRequest();
        assertNotNull(pedidoVacio);
        assertNull(pedidoVacio.getClienteId());
        assertNull(pedidoVacio.getProductosIds());
    }

    @Test
    void testAllArgsConstructor() {
        List<Long> productos = Arrays.asList(20L, 21L, 22L);
        LocalDateTime fechaTest = LocalDateTime.now().plusHours(1);
        PedidoRequest pedidoCompleto = new PedidoRequest(7L, productos, "PROCESANDO", fechaTest);

        assertEquals(7L, pedidoCompleto.getClienteId());
        assertEquals(productos, pedidoCompleto.getProductosIds());
        assertEquals("PROCESANDO", pedidoCompleto.getEstado());
        assertEquals(fechaTest, pedidoCompleto.getFecha());
    }

    @Test
    void testBuilderMethods() {
        // Test individual builder methods
        PedidoRequest.PedidoRequestBuilder builder = PedidoRequest.builder();
        
        List<Long> productos = Arrays.asList(30L, 31L, 32L);
        PedidoRequest pedidoFromBuilder = builder
                .clienteId(9L)
                .productosIds(productos)
                .build();

        assertEquals(9L, pedidoFromBuilder.getClienteId());
        assertEquals(productos, pedidoFromBuilder.getProductosIds());
    }

    @Test
    void testBuilderToString() {
        PedidoRequest.PedidoRequestBuilder builder = PedidoRequest.builder()
                .clienteId(1L);
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("PedidoRequestBuilder"));
    }

    @Test
    void testCanEqual() {
        PedidoRequest pedido1 = new PedidoRequest();
        PedidoRequest pedido2 = new PedidoRequest();
        
        assertTrue(pedido1.canEqual(pedido2));
        assertFalse(pedido1.canEqual("string"));
        assertFalse(pedido1.canEqual(null));
    }

    @Test
    void testWithEmptyProductList() {
        PedidoRequest pedidoVacio = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList())
                .build();

        assertEquals(1L, pedidoVacio.getClienteId());
        assertNotNull(pedidoVacio.getProductosIds());
        assertTrue(pedidoVacio.getProductosIds().isEmpty());
    }

    @Test
    void testWithNullProductList() {
        PedidoRequest pedidoNulo = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(null)
                .build();

        assertEquals(1L, pedidoNulo.getClienteId());
        assertNull(pedidoNulo.getProductosIds());
    }

    @Test
    void testEqualsWithNullFields() {
        PedidoRequest request1 = new PedidoRequest();
        PedidoRequest request2 = new PedidoRequest();
        PedidoRequest request3 = PedidoRequest.builder().clienteId(1L).build();

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request3, request1);
    }

    @Test
    void testEqualsWithDifferentFields() {
        LocalDateTime fecha = LocalDateTime.now();
        List<Long> productos = Arrays.asList(1L, 2L, 3L);

        PedidoRequest base = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productos)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        // Test different clienteId
        PedidoRequest differentClienteId = PedidoRequest.builder()
                .clienteId(2L)
                .productosIds(productos)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();
        assertNotEquals(base, differentClienteId);

        // Test different productosIds
        PedidoRequest differentProductos = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(4L, 5L, 6L))
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();
        assertNotEquals(base, differentProductos);

        // Test different estado
        PedidoRequest differentEstado = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productos)
                .estado("CONFIRMADO")
                .fecha(fecha)
                .build();
        assertNotEquals(base, differentEstado);

        // Test different fecha
        PedidoRequest differentFecha = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productos)
                .estado("PENDIENTE")
                .fecha(fecha.plusDays(1))
                .build();
        assertNotEquals(base, differentFecha);
    }

    @Test
    void testHashCodeConsistency() {
        LocalDateTime fecha = LocalDateTime.now();
        List<Long> productos = Arrays.asList(1L, 2L, 3L);

        PedidoRequest request = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productos)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        int hashCode1 = request.hashCode();
        int hashCode2 = request.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testEqualsWithNullValues() {
        PedidoRequest request1 = PedidoRequest.builder()
                .clienteId(null)
                .productosIds(null)
                .estado(null)
                .fecha(null)
                .build();

        PedidoRequest request2 = PedidoRequest.builder()
                .clienteId(null)
                .productosIds(null)
                .estado(null)
                .fecha(null)
                .build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        // Test one with null, other with value
        PedidoRequest request3 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L, 2L))
                .estado("PENDIENTE")
                .fecha(LocalDateTime.now())
                .build();

        assertNotEquals(request1, request3);
        assertNotEquals(request3, request1);
    }

    @Test
    void testHashCodeWithNullValues() {
        PedidoRequest request1 = new PedidoRequest();
        PedidoRequest request2 = new PedidoRequest();

        assertEquals(request1.hashCode(), request2.hashCode());

        // Test with some null values
        request1.setClienteId(1L);
        request2.setClienteId(1L);
        assertEquals(request1.hashCode(), request2.hashCode());

        request1.setProductosIds(null);
        request2.setProductosIds(null);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        List<Long> productos = Arrays.asList(1L, 2L, 3L);
        PedidoRequest request = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productos)
                .estado("PENDIENTE")
                .fecha(LocalDateTime.now())
                .build();

        // Test with different class
        Object differentClass = new Object();
        assertNotEquals(request, differentClass);

        // Test reflexivity
        assertEquals(request, request);

        // Test with null
        assertNotEquals(request, null);
    }

    @Test
    void testBuilderChaining() {
        LocalDateTime fecha = LocalDateTime.now();
        List<Long> productos = Arrays.asList(1L, 2L, 3L);

        PedidoRequest request = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(productos)
                .estado("PENDIENTE")
                .fecha(fecha)
                .build();

        assertNotNull(request);
        assertEquals(1L, request.getClienteId());
        assertEquals(productos, request.getProductosIds());
        assertEquals("PENDIENTE", request.getEstado());
        assertEquals(fecha, request.getFecha());
    }

    @Test
    void testEqualsWithDifferentProductListSizes() {
        PedidoRequest request1 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L, 2L))
                .build();

        PedidoRequest request2 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L, 2L, 3L))
                .build();

        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testEqualsWithSameProductListDifferentOrder() {
        PedidoRequest request1 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L, 2L, 3L))
                .build();

        PedidoRequest request2 = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(3L, 2L, 1L))
                .build();

        assertNotEquals(request1, request2);
    }
}
