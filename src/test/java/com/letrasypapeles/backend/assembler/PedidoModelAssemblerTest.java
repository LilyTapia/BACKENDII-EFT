package com.letrasypapeles.backend.assembler;

import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.entity.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoModelAssemblerTest {

    private PedidoModelAssembler assembler;
    private Pedido pedido;
    private Cliente cliente;
    private List<Producto> productos;

    @BeforeEach
    void setUp() {
        assembler = new PedidoModelAssembler();

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

        Producto producto1 = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Cuaderno universitario")
                .precio(new BigDecimal("5.99"))
                .stock(100)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        Producto producto2 = Producto.builder()
                .id(2L)
                .nombre("Lapiz")
                .descripcion("Lapiz HB")
                .precio(new BigDecimal("1.50"))
                .stock(50)
                .categoria(categoria)
                .proveedor(proveedor)
                .build();

        productos = Arrays.asList(producto1, producto2);

        pedido = Pedido.builder()
                .id(1L)
                .fecha(LocalDateTime.now())
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(productos)
                .build();
    }

    @Test
    void testToModel() {
        EntityModel<Pedido> model = assembler.toModel(pedido);

        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertFalse(model.getLinks().isEmpty());
        
        // Verify self link
        assertTrue(model.hasLink("self"));
        assertTrue(model.getLink("self").get().getHref().contains("/api/pedidos/1"));
        
        // Verify cliente link
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.getLink("cliente").get().getHref().contains("/api/clientes/1"));
        
        // Verify productos links
        assertTrue(model.hasLink("producto-1"));
        assertTrue(model.getLink("producto-1").get().getHref().contains("/api/productos/1"));
        
        assertTrue(model.hasLink("producto-2"));
        assertTrue(model.getLink("producto-2").get().getHref().contains("/api/productos/2"));
        
        // Verify pedidos collection link
        assertTrue(model.hasLink("pedidos"));
        assertTrue(model.getLink("pedidos").get().getHref().contains("/api/pedidos"));
    }

    @Test
    @SuppressWarnings("null")
    void testToModelWithNullPedido() {
        assertThrows(IllegalArgumentException.class, () -> {
            assembler.toModel(null);
        });
    }

    @Test
    void testToModelWithNullId() {
        pedido.setId(null);
        
        EntityModel<Pedido> model = assembler.toModel(pedido);
        
        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        // Should still have collection link even without ID
        assertTrue(model.hasLink("pedidos"));
    }

    @Test
    void testToModelWithNullCliente() {
        pedido.setCliente(null);
        
        EntityModel<Pedido> model = assembler.toModel(pedido);
        
        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("pedidos"));
        // Should not have cliente link when cliente is null
        assertFalse(model.hasLink("cliente"));
    }

    @Test
    void testToModelWithEmptyProductos() {
        pedido.setListaProductos(new ArrayList<>());
        
        EntityModel<Pedido> model = assembler.toModel(pedido);
        
        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("pedidos"));
        // Should not have producto links when list is empty
        assertFalse(model.hasLink("producto-1"));
        assertFalse(model.hasLink("producto-2"));
    }

    @Test
    void testToModelWithNullProductos() {
        pedido.setListaProductos(null);
        
        EntityModel<Pedido> model = assembler.toModel(pedido);
        
        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("pedidos"));
        // Should not have producto links when list is null
        assertFalse(model.hasLink("producto-1"));
        assertFalse(model.hasLink("producto-2"));
    }

    @Test
    void testToModelWithNullClienteId() {
        cliente.setId(null);
        pedido.setCliente(cliente);

        EntityModel<Pedido> model = assembler.toModel(pedido);

        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("pedidos"));
        // Cliente link will still be present even with null ID due to implementation
        assertTrue(model.hasLink("cliente"));
    }

    @Test
    void testToModelWithProductoNullId() {
        Producto productoSinId = Producto.builder()
                .nombre("Producto Sin ID")
                .precio(new BigDecimal("10.00"))
                .build();

        pedido.setListaProductos(Arrays.asList(productoSinId));

        EntityModel<Pedido> model = assembler.toModel(pedido);

        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("pedidos"));
        // Producto link will still be present even with null ID due to implementation
        assertTrue(model.hasLink("producto-null"));
    }

    @Test
    void testToModelWithDifferentEstados() {
        String[] estados = {"PENDIENTE", "PROCESANDO", "ENVIADO", "ENTREGADO", "CANCELADO"};
        
        for (String estado : estados) {
            pedido.setEstado(estado);
            EntityModel<Pedido> model = assembler.toModel(pedido);
            
            assertNotNull(model);
            assertEquals(pedido, model.getContent());
            assertEquals(estado, model.getContent().getEstado());
            assertTrue(model.hasLink("self"));
        }
    }

    @Test
    void testToModelLinksStructure() {
        EntityModel<Pedido> model = assembler.toModel(pedido);

        // Verify all expected links are present
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto-1"));
        assertTrue(model.hasLink("producto-2"));
        assertTrue(model.hasLink("pedidos"));
        assertTrue(model.hasLink("productos"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify link count (self + cliente + 2 productos + pedidos + productos + update + delete)
        assertEquals(8, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithSingleProducto() {
        Producto unicoProducto = productos.get(0);
        pedido.setListaProductos(Arrays.asList(unicoProducto));

        EntityModel<Pedido> model = assembler.toModel(pedido);

        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("producto-1"));
        assertFalse(model.hasLink("producto-2"));
        assertTrue(model.hasLink("pedidos"));
        assertTrue(model.hasLink("productos"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Should have 7 links (self + cliente + 1 producto + pedidos + productos + update + delete)
        assertEquals(7, model.getLinks().toList().size());
    }

    @Test
    void testToModelWithManyProductos() {
        List<Producto> muchosProductos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Producto producto = Producto.builder()
                    .id((long) i)
                    .nombre("Producto " + i)
                    .precio(new BigDecimal("10.00"))
                    .build();
            muchosProductos.add(producto);
        }

        pedido.setListaProductos(muchosProductos);

        EntityModel<Pedido> model = assembler.toModel(pedido);

        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.hasLink("self"));
        assertTrue(model.hasLink("cliente"));
        assertTrue(model.hasLink("pedidos"));
        assertTrue(model.hasLink("productos"));
        assertTrue(model.hasLink("update"));
        assertTrue(model.hasLink("delete"));

        // Verify all producto links
        for (int i = 1; i <= 5; i++) {
            assertTrue(model.hasLink("producto-" + i));
        }

        // Should have 11 links (self + cliente + 5 productos + pedidos + productos + update + delete)
        assertEquals(11, model.getLinks().toList().size());
    }
}
