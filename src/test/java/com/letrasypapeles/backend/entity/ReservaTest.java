package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Reserva reserva;
    private Cliente cliente;
    private Producto producto;
    private LocalDateTime fechaReserva;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@test.com")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .precio(new BigDecimal("5.99"))
                .stock(100)
                .build();

        fechaReserva = LocalDateTime.now();

        reserva = Reserva.builder()
                .id(1L)
                .fechaReserva(fechaReserva)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, reserva.getId());
        assertEquals(fechaReserva, reserva.getFechaReserva());
        assertEquals("ACTIVA", reserva.getEstado());
        assertEquals(5, reserva.getCantidad());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(producto, reserva.getProducto());

        // Test setters
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(2L);
        nuevoCliente.setNombre("Maria");

        Producto nuevoProducto = new Producto();
        nuevoProducto.setId(2L);
        nuevoProducto.setNombre("Lapiz");

        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);

        reserva.setId(2L);
        reserva.setFechaReserva(nuevaFecha);
        reserva.setEstado("CONFIRMADA");
        reserva.setCantidad(10);
        reserva.setCliente(nuevoCliente);
        reserva.setProducto(nuevoProducto);

        assertEquals(2L, reserva.getId());
        assertEquals(nuevaFecha, reserva.getFechaReserva());
        assertEquals("CONFIRMADA", reserva.getEstado());
        assertEquals(10, reserva.getCantidad());
        assertEquals(nuevoCliente, reserva.getCliente());
        assertEquals(nuevoProducto, reserva.getProducto());
    }

    @Test
    void testEquals() {
        Reserva reserva1 = Reserva.builder()
                .id(1L)
                .estado("ACTIVA")
                .cantidad(5)
                .fechaReserva(fechaReserva)
                .build();

        Reserva reserva2 = Reserva.builder()
                .id(1L)
                .estado("ACTIVA")
                .cantidad(5)
                .fechaReserva(fechaReserva)
                .build();

        Reserva reserva3 = Reserva.builder()
                .id(2L)
                .estado("CANCELADA")
                .cantidad(3)
                .fechaReserva(fechaReserva)
                .build();

        assertEquals(reserva1, reserva2);
        assertNotEquals(reserva1, reserva3);
        assertNotEquals(reserva1, null);
        assertNotEquals(reserva1, "string");
        assertEquals(reserva1, reserva1);
    }

    @Test
    void testHashCode() {
        Reserva reserva1 = Reserva.builder()
                .id(1L)
                .estado("ACTIVA")
                .cantidad(5)
                .fechaReserva(fechaReserva)
                .build();

        Reserva reserva2 = Reserva.builder()
                .id(1L)
                .estado("ACTIVA")
                .cantidad(5)
                .fechaReserva(fechaReserva)
                .build();

        assertEquals(reserva1.hashCode(), reserva2.hashCode());
    }

    @Test
    void testToString() {
        String toString = reserva.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Reserva"));
        assertTrue(toString.contains("ACTIVA"));
        assertTrue(toString.contains("5"));
    }

    @Test
    void testBuilder() {
        LocalDateTime builderFecha = LocalDateTime.now().plusHours(2);
        Reserva reservaBuilder = Reserva.builder()
                .id(3L)
                .fechaReserva(builderFecha)
                .estado("PENDIENTE")
                .cantidad(8)
                .cliente(cliente)
                .producto(producto)
                .build();

        assertEquals(3L, reservaBuilder.getId());
        assertEquals(builderFecha, reservaBuilder.getFechaReserva());
        assertEquals("PENDIENTE", reservaBuilder.getEstado());
        assertEquals(8, reservaBuilder.getCantidad());
        assertEquals(cliente, reservaBuilder.getCliente());
        assertEquals(producto, reservaBuilder.getProducto());
    }

    @Test
    void testNoArgsConstructor() {
        Reserva reservaVacia = new Reserva();
        assertNotNull(reservaVacia);
        assertNull(reservaVacia.getId());
        assertNull(reservaVacia.getFechaReserva());
        assertNull(reservaVacia.getEstado());
        assertNull(reservaVacia.getCantidad());
        assertNull(reservaVacia.getCliente());
        assertNull(reservaVacia.getProducto());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime constructorFecha = LocalDateTime.now().plusDays(2);
        Reserva reservaCompleta = new Reserva(
                4L,
                constructorFecha,
                "EXPIRADA",
                15,
                cliente,
                producto
        );

        assertEquals(4L, reservaCompleta.getId());
        assertEquals(constructorFecha, reservaCompleta.getFechaReserva());
        assertEquals("EXPIRADA", reservaCompleta.getEstado());
        assertEquals(15, reservaCompleta.getCantidad());
        assertEquals(cliente, reservaCompleta.getCliente());
        assertEquals(producto, reservaCompleta.getProducto());
    }

    @Test
    void testBuilderMethods() {
        Reserva.ReservaBuilder builder = Reserva.builder();
        
        LocalDateTime methodFecha = LocalDateTime.now().plusHours(3);
        Reserva reservaFromBuilder = builder
                .id(5L)
                .fechaReserva(methodFecha)
                .estado("PROCESANDO")
                .cantidad(12)
                .cliente(cliente)
                .producto(producto)
                .build();

        assertEquals(5L, reservaFromBuilder.getId());
        assertEquals(methodFecha, reservaFromBuilder.getFechaReserva());
        assertEquals("PROCESANDO", reservaFromBuilder.getEstado());
        assertEquals(12, reservaFromBuilder.getCantidad());
        assertEquals(cliente, reservaFromBuilder.getCliente());
        assertEquals(producto, reservaFromBuilder.getProducto());
    }

    @Test
    void testBuilderToString() {
        Reserva.ReservaBuilder builder = Reserva.builder()
                .id(1L)
                .estado("TEST");
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("ReservaBuilder"));
    }

    @Test
    void testCanEqual() {
        Reserva reserva1 = new Reserva();
        Reserva reserva2 = new Reserva();
        
        assertTrue(reserva1.canEqual(reserva2));
        assertFalse(reserva1.canEqual("string"));
        assertFalse(reserva1.canEqual(null));
    }

    @Test
    void testNullValues() {
        reserva.setFechaReserva(null);
        reserva.setEstado(null);
        reserva.setCantidad(null);
        reserva.setCliente(null);
        reserva.setProducto(null);

        assertNull(reserva.getFechaReserva());
        assertNull(reserva.getEstado());
        assertNull(reserva.getCantidad());
        assertNull(reserva.getCliente());
        assertNull(reserva.getProducto());
    }

    @Test
    void testEstadoValues() {
        String[] estados = {"ACTIVA", "CONFIRMADA", "CANCELADA", "EXPIRADA", "PROCESANDO"};
        
        for (String estado : estados) {
            reserva.setEstado(estado);
            assertEquals(estado, reserva.getEstado());
        }
    }

    @Test
    void testCantidadValues() {
        // Test positive values
        reserva.setCantidad(1);
        assertEquals(1, reserva.getCantidad());
        
        reserva.setCantidad(100);
        assertEquals(100, reserva.getCantidad());
        
        // Test zero
        reserva.setCantidad(0);
        assertEquals(0, reserva.getCantidad());
        
        // Test negative (might be allowed for business logic)
        reserva.setCantidad(-1);
        assertEquals(-1, reserva.getCantidad());
    }

    @Test
    void testFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusYears(1);
        reserva.setFechaReserva(futureDate);
        assertEquals(futureDate, reserva.getFechaReserva());
    }

    @Test
    void testPastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);
        reserva.setFechaReserva(pastDate);
        assertEquals(pastDate, reserva.getFechaReserva());
    }

    @Test
    void testEqualsWithNullFields() {
        Reserva reserva1 = new Reserva();
        Reserva reserva2 = new Reserva();
        Reserva reserva3 = Reserva.builder().id(1L).build();

        assertEquals(reserva1, reserva2);
        assertNotEquals(reserva1, reserva3);
        assertNotEquals(reserva3, reserva1);
    }

    @Test
    void testEqualsWithDifferentFields() {
        LocalDateTime fecha = LocalDateTime.now();
        Reserva base = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();

        // Test different id
        Reserva differentId = Reserva.builder()
                .id(2L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();
        assertNotEquals(base, differentId);

        // Test different fechaReserva
        Reserva differentFecha = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha.plusDays(1))
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();
        assertNotEquals(base, differentFecha);

        // Test different estado
        Reserva differentEstado = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("CANCELADA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();
        assertNotEquals(base, differentEstado);

        // Test different cantidad
        Reserva differentCantidad = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(10)
                .cliente(cliente)
                .producto(producto)
                .build();
        assertNotEquals(base, differentCantidad);

        // Test different cliente
        Cliente otroCliente = Cliente.builder().id(2L).nombre("Otro").build();
        Reserva differentCliente = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(otroCliente)
                .producto(producto)
                .build();
        assertNotEquals(base, differentCliente);

        // Test different producto
        Producto otroProducto = Producto.builder().id(2L).nombre("Otro").build();
        Reserva differentProducto = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(otroProducto)
                .build();
        assertNotEquals(base, differentProducto);
    }

    @Test
    void testHashCodeConsistency() {
        LocalDateTime fecha = LocalDateTime.now();
        Reserva reserva = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();

        int hashCode1 = reserva.hashCode();
        int hashCode2 = reserva.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testEqualsWithNullValues() {
        Reserva reserva1 = Reserva.builder()
                .id(null)
                .fechaReserva(null)
                .estado(null)
                .cantidad(null)
                .cliente(null)
                .producto(null)
                .build();

        Reserva reserva2 = Reserva.builder()
                .id(null)
                .fechaReserva(null)
                .estado(null)
                .cantidad(null)
                .cliente(null)
                .producto(null)
                .build();

        assertEquals(reserva1, reserva2);
        assertEquals(reserva1.hashCode(), reserva2.hashCode());

        // Test one with null, other with value
        Reserva reserva3 = Reserva.builder()
                .id(1L)
                .fechaReserva(LocalDateTime.now())
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();

        assertNotEquals(reserva1, reserva3);
        assertNotEquals(reserva3, reserva1);
    }

    @Test
    void testHashCodeWithNullValues() {
        Reserva reserva1 = new Reserva();
        Reserva reserva2 = new Reserva();

        assertEquals(reserva1.hashCode(), reserva2.hashCode());

        // Test with some null values
        reserva1.setId(1L);
        reserva2.setId(1L);
        assertEquals(reserva1.hashCode(), reserva2.hashCode());

        reserva1.setFechaReserva(null);
        reserva2.setFechaReserva(null);
        assertEquals(reserva1.hashCode(), reserva2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        LocalDateTime fecha = LocalDateTime.now();
        Reserva reserva = Reserva.builder()
                .id(1L)
                .fechaReserva(fecha)
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();

        // Test with different class
        Object differentClass = new Object();
        assertNotEquals(reserva, differentClass);

        // Test reflexivity
        assertEquals(reserva, reserva);

        // Test with null
        assertNotEquals(reserva, null);
    }
}
