package com.letrasypapeles.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaRequestTest {

    private ReservaRequest reservaRequest;
    private LocalDateTime fechaReserva;

    @BeforeEach
    void setUp() {
        fechaReserva = LocalDateTime.now();
        reservaRequest = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("ACTIVA")
                .fechaReserva(fechaReserva)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, reservaRequest.getClienteId());
        assertEquals(2L, reservaRequest.getProductoId());
        assertEquals(5, reservaRequest.getCantidad());
        assertEquals("ACTIVA", reservaRequest.getEstado());
        assertEquals(fechaReserva, reservaRequest.getFechaReserva());

        // Test setters
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);
        reservaRequest.setClienteId(3L);
        reservaRequest.setProductoId(4L);
        reservaRequest.setCantidad(10);
        reservaRequest.setEstado("CANCELADA");
        reservaRequest.setFechaReserva(nuevaFecha);

        assertEquals(3L, reservaRequest.getClienteId());
        assertEquals(4L, reservaRequest.getProductoId());
        assertEquals(10, reservaRequest.getCantidad());
        assertEquals("CANCELADA", reservaRequest.getEstado());
        assertEquals(nuevaFecha, reservaRequest.getFechaReserva());
    }

    @Test
    void testEquals() {
        ReservaRequest reserva1 = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("ACTIVA")
                .fechaReserva(fechaReserva)
                .build();

        ReservaRequest reserva2 = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("ACTIVA")
                .fechaReserva(fechaReserva)
                .build();

        ReservaRequest reserva3 = ReservaRequest.builder()
                .clienteId(2L)
                .productoId(3L)
                .cantidad(10)
                .estado("CANCELADA")
                .fechaReserva(fechaReserva)
                .build();

        // Test equals
        assertEquals(reserva1, reserva2);
        assertNotEquals(reserva1, reserva3);
        assertNotEquals(reserva1, null);
        assertNotEquals(reserva1, "string");
        assertEquals(reserva1, reserva1);
    }

    @Test
    void testHashCode() {
        ReservaRequest reserva1 = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .fechaReserva(fechaReserva)
                .build();

        ReservaRequest reserva2 = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .fechaReserva(fechaReserva)
                .build();

        assertEquals(reserva1.hashCode(), reserva2.hashCode());
    }

    @Test
    void testToString() {
        String toString = reservaRequest.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("ReservaRequest"));
        assertTrue(toString.contains("clienteId=1"));
        assertTrue(toString.contains("productoId=2"));
        assertTrue(toString.contains("cantidad=5"));
    }

    @Test
    void testBuilder() {
        LocalDateTime fecha = LocalDateTime.now().plusHours(2);
        ReservaRequest reservaBuilder = ReservaRequest.builder()
                .clienteId(5L)
                .productoId(6L)
                .cantidad(15)
                .fechaReserva(fecha)
                .build();

        assertEquals(5L, reservaBuilder.getClienteId());
        assertEquals(6L, reservaBuilder.getProductoId());
        assertEquals(15, reservaBuilder.getCantidad());
        assertEquals(fecha, reservaBuilder.getFechaReserva());
    }

    @Test
    void testNoArgsConstructor() {
        ReservaRequest reservaVacia = new ReservaRequest();
        assertNotNull(reservaVacia);
        assertNull(reservaVacia.getClienteId());
        assertNull(reservaVacia.getProductoId());
        assertNull(reservaVacia.getCantidad());
        assertNull(reservaVacia.getFechaReserva());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime fecha = LocalDateTime.now().plusDays(2);
        ReservaRequest reservaCompleta = new ReservaRequest(7L, 8L, 20, "PENDIENTE", fecha);

        assertEquals(7L, reservaCompleta.getClienteId());
        assertEquals(8L, reservaCompleta.getProductoId());
        assertEquals(20, reservaCompleta.getCantidad());
        assertEquals("PENDIENTE", reservaCompleta.getEstado());
        assertEquals(fecha, reservaCompleta.getFechaReserva());
    }

    @Test
    void testBuilderMethods() {
        // Test individual builder methods
        ReservaRequest.ReservaRequestBuilder builder = ReservaRequest.builder();
        
        LocalDateTime fecha = LocalDateTime.now().plusHours(3);
        ReservaRequest reservaFromBuilder = builder
                .clienteId(9L)
                .productoId(10L)
                .cantidad(25)
                .fechaReserva(fecha)
                .build();

        assertEquals(9L, reservaFromBuilder.getClienteId());
        assertEquals(10L, reservaFromBuilder.getProductoId());
        assertEquals(25, reservaFromBuilder.getCantidad());
        assertEquals(fecha, reservaFromBuilder.getFechaReserva());
    }

    @Test
    void testBuilderToString() {
        ReservaRequest.ReservaRequestBuilder builder = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L);
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("ReservaRequestBuilder"));
    }

    @Test
    void testCanEqual() {
        ReservaRequest reserva1 = new ReservaRequest();
        ReservaRequest reserva2 = new ReservaRequest();
        
        assertTrue(reserva1.canEqual(reserva2));
        assertFalse(reserva1.canEqual("string"));
        assertFalse(reserva1.canEqual(null));
    }

    @Test
    void testEqualsWithNullFields() {
        ReservaRequest request1 = new ReservaRequest();
        ReservaRequest request2 = new ReservaRequest();
        ReservaRequest request3 = ReservaRequest.builder().clienteId(1L).build();

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request3, request1);
    }

    @Test
    void testEqualsWithDifferentFields() {
        LocalDateTime fecha = LocalDateTime.now();
        ReservaRequest base = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(fecha)
                .build();

        // Test different clienteId
        ReservaRequest differentClienteId = ReservaRequest.builder()
                .clienteId(2L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(fecha)
                .build();
        assertNotEquals(base, differentClienteId);

        // Test different productoId
        ReservaRequest differentProductoId = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(3L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(fecha)
                .build();
        assertNotEquals(base, differentProductoId);

        // Test different cantidad
        ReservaRequest differentCantidad = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(10)
                .estado("PENDIENTE")
                .fechaReserva(fecha)
                .build();
        assertNotEquals(base, differentCantidad);

        // Test different estado
        ReservaRequest differentEstado = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("CONFIRMADA")
                .fechaReserva(fecha)
                .build();
        assertNotEquals(base, differentEstado);

        // Test different fechaReserva
        ReservaRequest differentFecha = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(fecha.plusDays(1))
                .build();
        assertNotEquals(base, differentFecha);
    }

    @Test
    void testHashCodeConsistency() {
        LocalDateTime fecha = LocalDateTime.now();
        ReservaRequest request = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(fecha)
                .build();

        int hashCode1 = request.hashCode();
        int hashCode2 = request.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testEqualsWithNullValues() {
        ReservaRequest request1 = ReservaRequest.builder()
                .clienteId(null)
                .productoId(null)
                .cantidad(null)
                .estado(null)
                .fechaReserva(null)
                .build();

        ReservaRequest request2 = ReservaRequest.builder()
                .clienteId(null)
                .productoId(null)
                .cantidad(null)
                .estado(null)
                .fechaReserva(null)
                .build();

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());

        // Test one with null, other with value
        ReservaRequest request3 = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(LocalDateTime.now())
                .build();

        assertNotEquals(request1, request3);
        assertNotEquals(request3, request1);
    }

    @Test
    void testHashCodeWithNullValues() {
        ReservaRequest request1 = new ReservaRequest();
        ReservaRequest request2 = new ReservaRequest();

        assertEquals(request1.hashCode(), request2.hashCode());

        // Test with some null values
        request1.setClienteId(1L);
        request2.setClienteId(1L);
        assertEquals(request1.hashCode(), request2.hashCode());

        request1.setProductoId(null);
        request2.setProductoId(null);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        ReservaRequest request = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(LocalDateTime.now())
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
        ReservaRequest request = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(2L)
                .cantidad(5)
                .estado("PENDIENTE")
                .fechaReserva(fecha)
                .build();

        assertNotNull(request);
        assertEquals(1L, request.getClienteId());
        assertEquals(2L, request.getProductoId());
        assertEquals(5, request.getCantidad());
        assertEquals("PENDIENTE", request.getEstado());
        assertEquals(fecha, request.getFechaReserva());
    }
}
