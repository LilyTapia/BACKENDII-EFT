package com.letrasypapeles.backend.assembler;

import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.entity.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaModelAssemblerTest {

    private ReservaModelAssembler assembler;
    private Reserva reserva;
    private Cliente cliente;
    private Producto producto;

    @BeforeEach
    void setUp() {
        assembler = new ReservaModelAssembler();

        // Create test entities
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@test.com")
                .build();

        Categoria categoria = Categoria.builder()
                .id(1L)
                .nombre("Papeleria")
                .descripcion("Productos de papeleria")
                .build();

        Proveedor proveedor = Proveedor.builder()
                .id(1L)
                .nombre("Proveedor Test")
                .contacto("proveedor@test.com")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Cuaderno universitario")
                .precio(new BigDecimal("5.99"))
                .stock(100)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        reserva = Reserva.builder()
                .id(1L)
                .fechaReserva(LocalDateTime.now())
                .estado("ACTIVA")
                .cantidad(5)
                .cliente(cliente)
                .producto(producto)
                .build();
    }

    @Test
    void testToModel() {
        EntityModel<Reserva> model = assembler.toModel(reserva);

        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertFalse(model.getLinks().isEmpty());
        
        // Verify self link
        assertTrue(model.hasLink("self"));
        assertTrue(model.getLink("self").get().getHref().contains("/api/reservas/1"));
        
        // Verify cliente link
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.getLink("cliente").get().getHref().contains("/api/clientes/1"));
        
        // Verify producto link
        assertTrue(model.hasLink("producto"));
        assertTrue(model.getLink("producto").get().getHref().contains("/api/productos/1"));
        
        // Verify reservas collection link
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.getLink("reservas").get().getHref().contains("/api/reservas"));
    }

    @Test
    @SuppressWarnings("null")
    void testToModelWithNullReserva() {
        assertThrows(IllegalArgumentException.class, () -> {
            assembler.toModel(null);
        });
    }

    @Test
    void testToModelWithNullId() {
        reserva.setId(null);
        
        EntityModel<Reserva> model = assembler.toModel(reserva);
        
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        // Should still have collection link even without ID
        assertTrue(model.hasLink("reservas"));
    }

    @Test
    void testToModelWithNullCliente() {
        reserva.setCliente(null);
        
        EntityModel<Reserva> model = assembler.toModel(reserva);
        
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("reservas"));
        // Should not have cliente link when cliente is null
        assertFalse(model.hasLink("cliente"));
    }

    @Test
    void testToModelWithNullProducto() {
        reserva.setProducto(null);
        
        EntityModel<Reserva> model = assembler.toModel(reserva);
        
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("reservas"));
        // Should not have producto link when producto is null
        assertFalse(model.hasLink("producto"));
    }

    @Test
    void testToModelWithNullClienteId() {
        cliente.setId(null);
        reserva.setCliente(cliente);

        EntityModel<Reserva> model = assembler.toModel(reserva);

        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("reservas"));
        // Cliente link will still be present even with null ID due to implementation
        assertTrue(model.hasLink("cliente"));
    }

    @Test
    void testToModelWithNullProductoId() {
        producto.setId(null);
        reserva.setProducto(producto);

        EntityModel<Reserva> model = assembler.toModel(reserva);

        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("reservas"));
        // Producto link will still be present even with null ID due to implementation
        assertTrue(model.hasLink("producto"));
    }

    @Test
    void testToModelWithDifferentEstados() {
        String[] estados = {"ACTIVA", "CONFIRMADA", "CANCELADA", "EXPIRADA"};
        
        for (String estado : estados) {
            reserva.setEstado(estado);
            EntityModel<Reserva> model = assembler.toModel(reserva);
            
            assertNotNull(model);
            assertEquals(reserva, model.getContent());
            assertEquals(estado, model.getContent().getEstado());
            assertTrue(model.hasLink("self"));
        }
    }

    @Test
    void testToModelLinksStructure() {
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Verify all expected links are present
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify link count (self + cliente + producto + reservas + update + delete)
        assertEquals(6, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithZeroCantidad() {
        reserva.setCantidad(0);
        
        EntityModel<Reserva> model = assembler.toModel(reserva);
        
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals(0, model.getContent().getCantidad());
        assertTrue(model.hasLink("self"));
    }

    @Test
    void testToModelWithNegativeCantidad() {
        reserva.setCantidad(-1);
        
        EntityModel<Reserva> model = assembler.toModel(reserva);
        
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals(-1, model.getContent().getCantidad());
        assertTrue(model.hasLink("self"));
    }

    @Test
    void testToModelWithPendienteEstado() {
        // Given
        reserva.setEstado("PENDIENTE");

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals("PENDIENTE", model.getContent().getEstado());

        // Verify basic links
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links for PENDIENTE state
        assertTrue(model.hasLink("confirmar"));
        assertTrue(model.hasLink("cancelar"));

        // Verify total link count (basic 6 + conditional 2 = 8)
        assertEquals(8, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithConfirmadaEstado() {
        // Given
        reserva.setEstado("CONFIRMADA");

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals("CONFIRMADA", model.getContent().getEstado());

        // Verify basic links
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links are NOT present for CONFIRMADA state
        assertFalse(model.hasLink("confirmar"));
        assertFalse(model.hasLink("cancelar"));

        // Verify total link count (only basic 6 links)
        assertEquals(6, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithCanceladaEstado() {
        // Given
        reserva.setEstado("CANCELADA");

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals("CANCELADA", model.getContent().getEstado());

        // Verify basic links
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links are NOT present for CANCELADA state
        assertFalse(model.hasLink("confirmar"));
        assertFalse(model.hasLink("cancelar"));

        // Verify total link count (only basic 6 links)
        assertEquals(6, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithNullEstado() {
        // Given
        reserva.setEstado(null);

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertNull(model.getContent().getEstado());

        // Verify basic links
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links are NOT present when estado is null
        assertFalse(model.hasLink("confirmar"));
        assertFalse(model.hasLink("cancelar"));

        // Verify total link count (only basic 6 links)
        assertEquals(6, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithEmptyEstado() {
        // Given
        reserva.setEstado("");

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals("", model.getContent().getEstado());

        // Verify basic links
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links are NOT present when estado is empty
        assertFalse(model.hasLink("confirmar"));
        assertFalse(model.hasLink("cancelar"));

        // Verify total link count (only basic 6 links)
        assertEquals(6, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithPendienteEstadoAndNullCliente() {
        // Given
        reserva.setEstado("PENDIENTE");
        reserva.setCliente(null);

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals("PENDIENTE", model.getContent().getEstado());

        // Verify basic links (excluding cliente)
        assertTrue(model.hasLink("self"));
        assertFalse(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links for PENDIENTE state
        assertTrue(model.hasLink("confirmar"));
        assertTrue(model.hasLink("cancelar"));

        // Verify total link count (basic 5 + conditional 2 = 7)
        assertEquals(7, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithPendienteEstadoAndNullProducto() {
        // Given
        reserva.setEstado("PENDIENTE");
        reserva.setProducto(null);

        // When
        EntityModel<Reserva> model = assembler.toModel(reserva);

        // Then
        assertNotNull(model);
        assertEquals(reserva, model.getContent());
        assertEquals("PENDIENTE", model.getContent().getEstado());

        // Verify basic links (excluding producto)
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertFalse(model.hasLink("producto"));
        assertTrue(model.hasLink("reservas"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify conditional links for PENDIENTE state
        assertTrue(model.hasLink("confirmar"));
        assertTrue(model.hasLink("cancelar"));

        // Verify total link count (basic 5 + conditional 2 = 7)
        assertEquals(7, model.getLinks().toList().size());
    }
}
