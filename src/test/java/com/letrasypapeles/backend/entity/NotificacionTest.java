package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

    private Notificacion notificacion;
    private Cliente cliente;
    private LocalDateTime fecha;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@test.com")
                .build();

        fecha = LocalDateTime.now();

        notificacion = Notificacion.builder()
                .id(1L)
                .mensaje("Producto disponible")
                .fecha(fecha)
                .cliente(cliente)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, notificacion.getId());
        assertEquals("Producto disponible", notificacion.getMensaje());
        assertEquals(fecha, notificacion.getFecha());
        assertEquals(cliente, notificacion.getCliente());

        // Test setters
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(2L);
        nuevoCliente.setNombre("Maria");

        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(1);

        notificacion.setId(2L);
        notificacion.setMensaje("Pedido confirmado");
        notificacion.setFecha(nuevaFecha);
        notificacion.setCliente(nuevoCliente);

        assertEquals(2L, notificacion.getId());
        assertEquals("Pedido confirmado", notificacion.getMensaje());
        assertEquals(nuevaFecha, notificacion.getFecha());
        assertEquals(nuevoCliente, notificacion.getCliente());
    }

    @Test
    void testEquals() {
        Notificacion notificacion1 = Notificacion.builder()
                .id(1L)
                .mensaje("Test mensaje")
                .fecha(fecha)
                .build();

        Notificacion notificacion2 = Notificacion.builder()
                .id(1L)
                .mensaje("Test mensaje")
                .fecha(fecha)
                .build();

        Notificacion notificacion3 = Notificacion.builder()
                .id(2L)
                .mensaje("Otro mensaje")
                .fecha(fecha)
                .build();

        assertEquals(notificacion1, notificacion2);
        assertNotEquals(notificacion1, notificacion3);
        assertNotEquals(notificacion1, null);
        assertNotEquals(notificacion1, "string");
        assertEquals(notificacion1, notificacion1);
    }

    @Test
    void testHashCode() {
        Notificacion notificacion1 = Notificacion.builder()
                .id(1L)
                .mensaje("Test mensaje")
                .fecha(fecha)
                .build();

        Notificacion notificacion2 = Notificacion.builder()
                .id(1L)
                .mensaje("Test mensaje")
                .fecha(fecha)
                .build();

        assertEquals(notificacion1.hashCode(), notificacion2.hashCode());
    }

    @Test
    void testToString() {
        String toString = notificacion.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Notificacion"));
        assertTrue(toString.contains("Producto disponible"));
    }

    @Test
    void testBuilder() {
        LocalDateTime builderFecha = LocalDateTime.now().plusHours(2);
        Notificacion notificacionBuilder = Notificacion.builder()
                .id(3L)
                .mensaje("Reserva cancelada")
                .fecha(builderFecha)
                .cliente(cliente)
                .build();

        assertEquals(3L, notificacionBuilder.getId());
        assertEquals("Reserva cancelada", notificacionBuilder.getMensaje());
        assertEquals(builderFecha, notificacionBuilder.getFecha());
        assertEquals(cliente, notificacionBuilder.getCliente());
    }

    @Test
    void testNoArgsConstructor() {
        Notificacion notificacionVacia = new Notificacion();
        assertNotNull(notificacionVacia);
        assertNull(notificacionVacia.getId());
        assertNull(notificacionVacia.getMensaje());
        assertNull(notificacionVacia.getFecha());
        assertNull(notificacionVacia.getCliente());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime constructorFecha = LocalDateTime.now().plusDays(2);
        Notificacion notificacionCompleta = new Notificacion(
                4L,
                "Stock bajo",
                constructorFecha,
                cliente
        );

        assertEquals(4L, notificacionCompleta.getId());
        assertEquals("Stock bajo", notificacionCompleta.getMensaje());
        assertEquals(constructorFecha, notificacionCompleta.getFecha());
        assertEquals(cliente, notificacionCompleta.getCliente());
    }

    @Test
    void testBuilderMethods() {
        Notificacion.NotificacionBuilder builder = Notificacion.builder();
        
        LocalDateTime methodFecha = LocalDateTime.now().plusHours(3);
        Notificacion notificacionFromBuilder = builder
                .id(5L)
                .mensaje("Promocion especial")
                .fecha(methodFecha)
                .cliente(cliente)
                .build();

        assertEquals(5L, notificacionFromBuilder.getId());
        assertEquals("Promocion especial", notificacionFromBuilder.getMensaje());
        assertEquals(methodFecha, notificacionFromBuilder.getFecha());
        assertEquals(cliente, notificacionFromBuilder.getCliente());
    }

    @Test
    void testBuilderToString() {
        Notificacion.NotificacionBuilder builder = Notificacion.builder()
                .id(1L)
                .mensaje("Test");
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("NotificacionBuilder"));
    }

    @Test
    void testCanEqual() {
        Notificacion notificacion1 = new Notificacion();
        Notificacion notificacion2 = new Notificacion();
        
        assertTrue(notificacion1.canEqual(notificacion2));
        assertFalse(notificacion1.canEqual("string"));
        assertFalse(notificacion1.canEqual(null));
    }

    @Test
    void testNullValues() {
        notificacion.setMensaje(null);
        notificacion.setFecha(null);
        notificacion.setCliente(null);

        assertNull(notificacion.getMensaje());
        assertNull(notificacion.getFecha());
        assertNull(notificacion.getCliente());
    }

    @Test
    void testEmptyMessage() {
        notificacion.setMensaje("");
        assertEquals("", notificacion.getMensaje());
    }

    @Test
    void testLongMessage() {
        String longMessage = "Este es un mensaje muy largo que podria contener mucha informacion importante para el cliente sobre el estado de su pedido o reserva";
        notificacion.setMensaje(longMessage);
        assertEquals(longMessage, notificacion.getMensaje());
    }

    @Test
    void testFutureDate() {
        LocalDateTime futureDate = LocalDateTime.now().plusYears(1);
        notificacion.setFecha(futureDate);
        assertEquals(futureDate, notificacion.getFecha());
    }

    @Test
    void testPastDate() {
        LocalDateTime pastDate = LocalDateTime.now().minusYears(1);
        notificacion.setFecha(pastDate);
        assertEquals(pastDate, notificacion.getFecha());
    }

    @Test
    void testEqualsWithNullFields() {
        Notificacion notificacion1 = new Notificacion();
        Notificacion notificacion2 = new Notificacion();

        // Both null
        assertEquals(notificacion1, notificacion2);

        // One has id, other doesn't
        notificacion1.setId(1L);
        assertNotEquals(notificacion1, notificacion2);
        assertNotEquals(notificacion2, notificacion1);

        // Both have same id
        notificacion2.setId(1L);
        assertEquals(notificacion1, notificacion2);

        // Different mensaje
        notificacion1.setMensaje("Test mensaje");
        assertNotEquals(notificacion1, notificacion2);

        // Same mensaje
        notificacion2.setMensaje("Test mensaje");
        assertEquals(notificacion1, notificacion2);

        // Different fecha
        notificacion1.setFecha(LocalDateTime.now());
        assertNotEquals(notificacion1, notificacion2);

        // Same fecha
        notificacion2.setFecha(notificacion1.getFecha());
        assertEquals(notificacion1, notificacion2);

        // Different cliente
        notificacion1.setCliente(cliente);
        assertNotEquals(notificacion1, notificacion2);

        // Same cliente
        notificacion2.setCliente(cliente);
        assertEquals(notificacion1, notificacion2);
    }

    @Test
    void testHashCodeWithNullFields() {
        Notificacion notificacion1 = new Notificacion();
        Notificacion notificacion2 = new Notificacion();

        // Both null - should have same hash
        assertEquals(notificacion1.hashCode(), notificacion2.hashCode());

        // Add fields one by one
        notificacion1.setId(1L);
        notificacion2.setId(1L);
        assertEquals(notificacion1.hashCode(), notificacion2.hashCode());

        notificacion1.setMensaje("Test mensaje");
        notificacion2.setMensaje("Test mensaje");
        assertEquals(notificacion1.hashCode(), notificacion2.hashCode());

        LocalDateTime testFecha = LocalDateTime.of(2023, 1, 1, 10, 0);
        notificacion1.setFecha(testFecha);
        notificacion2.setFecha(testFecha);
        assertEquals(notificacion1.hashCode(), notificacion2.hashCode());

        notificacion1.setCliente(cliente);
        notificacion2.setCliente(cliente);
        assertEquals(notificacion1.hashCode(), notificacion2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        Notificacion notificacion1 = Notificacion.builder()
                .id(1L)
                .mensaje("Test mensaje")
                .fecha(LocalDateTime.of(2023, 1, 1, 10, 0))
                .cliente(cliente)
                .build();

        // Test with different types
        assertNotEquals(notificacion1, new Object());
        assertNotEquals(notificacion1, 123);
        assertNotEquals(notificacion1, "string");

        // Test with null
        assertNotEquals(notificacion1, null);

        // Test reflexivity
        assertEquals(notificacion1, notificacion1);

        // Test with different field values
        Notificacion notificacionDifferentId = Notificacion.builder()
                .id(2L)
                .mensaje("Test mensaje")
                .fecha(LocalDateTime.of(2023, 1, 1, 10, 0))
                .cliente(cliente)
                .build();
        assertNotEquals(notificacion1, notificacionDifferentId);

        Notificacion notificacionDifferentMensaje = Notificacion.builder()
                .id(1L)
                .mensaje("Otro mensaje")
                .fecha(LocalDateTime.of(2023, 1, 1, 10, 0))
                .cliente(cliente)
                .build();
        assertNotEquals(notificacion1, notificacionDifferentMensaje);

        Notificacion notificacionDifferentFecha = Notificacion.builder()
                .id(1L)
                .mensaje("Test mensaje")
                .fecha(LocalDateTime.of(2023, 1, 2, 10, 0))
                .cliente(cliente)
                .build();
        assertNotEquals(notificacion1, notificacionDifferentFecha);
    }
}
