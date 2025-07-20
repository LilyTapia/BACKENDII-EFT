package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.dto.PedidoRequest;
import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.repository.PedidoRepository;
import com.letrasypapeles.backend.repository.ClienteRepository;
import com.letrasypapeles.backend.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private Cliente cliente;
    private Producto producto;
    private LocalDateTime fecha;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        fecha = LocalDateTime.now();
        
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .build();

        List<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(producto);

        pedido = Pedido.builder()
                .id(1L)
                .fecha(fecha)
                .estado("PENDIENTE")
                .cliente(cliente)
                .listaProductos(listaProductos)
                .build();
    }

    @Test
    void obtenerTodos() {
        // Given
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // When
        List<Pedido> result = pedidoService.obtenerTodos();

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId() {
        // Given
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        // When
        Optional<Pedido> result = pedidoService.obtenerPorId(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("PENDIENTE", result.get().getEstado());
        assertEquals(cliente.getId(), result.get().getCliente().getId());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorIdNoExistente() {
        // Given
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Pedido> result = pedidoService.obtenerPorId(99L);

        // Then
        assertFalse(result.isPresent());
        verify(pedidoRepository, times(1)).findById(99L);
    }

    @Test
    void guardar() {
        // Given
        Pedido pedidoNuevo = Pedido.builder()
                .fecha(fecha)
                .estado("NUEVO")
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .build();

        Pedido pedidoGuardado = Pedido.builder()
                .id(2L)
                .fecha(fecha)
                .estado("NUEVO")
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .build();
        
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        // When
        Pedido result = pedidoService.guardar(pedidoNuevo);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("NUEVO", result.getEstado());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void eliminar() {
        // Given
        Long idToDelete = 1L;
        doNothing().when(pedidoRepository).deleteById(idToDelete);

        // When
        pedidoService.eliminar(idToDelete);

        // Then
        verify(pedidoRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void obtenerPorClienteId() {
        // Given
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoRepository.findByClienteId(1L)).thenReturn(pedidos);

        // When
        List<Pedido> result = pedidoService.obtenerPorClienteId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        assertEquals(1L, result.get(0).getCliente().getId());
        verify(pedidoRepository, times(1)).findByClienteId(1L);
    }

    @Test
    void obtenerPorEstado() {
        // Given
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(pedidoRepository.findByEstado("PENDIENTE")).thenReturn(pedidos);

        // When
        List<Pedido> result = pedidoService.obtenerPorEstado("PENDIENTE");

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        verify(pedidoRepository, times(1)).findByEstado("PENDIENTE");
    }

    @Test
    void guardarConClienteIncompleto() {
        // Given
        Cliente clienteIncompleto = Cliente.builder()
                .id(1L)
                .build(); // Solo ID, sin nombre ni email

        Cliente clienteCompleto = Cliente.builder()
                .id(1L)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .build();

        Pedido pedidoConClienteIncompleto = Pedido.builder()
                .cliente(clienteIncompleto)
                .listaProductos(new ArrayList<>())
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteCompleto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoConClienteIncompleto);

        // When
        Pedido result = pedidoService.guardar(pedidoConClienteIncompleto);

        // Then
        assertNotNull(result);
        assertEquals(clienteCompleto, result.getCliente());
        verify(clienteRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void guardarConClienteIncompletoNoEncontrado() {
        // Given
        Cliente clienteIncompleto = Cliente.builder()
                .id(99L)
                .build(); // Solo ID, sin nombre ni email

        Pedido pedidoConClienteIncompleto = Pedido.builder()
                .cliente(clienteIncompleto)
                .listaProductos(new ArrayList<>())
                .build();

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.guardar(pedidoConClienteIncompleto);
        });

        assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
        verify(clienteRepository, times(1)).findById(99L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void guardarConProductosIncompletos() {
        // Given
        Producto productoIncompleto = Producto.builder()
                .id(1L)
                .build(); // Solo ID, sin nombre ni precio

        Producto productoCompleto = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .precio(new BigDecimal("25.99"))
                .build();

        List<Producto> productosIncompletos = Arrays.asList(productoIncompleto);

        Pedido pedidoConProductosIncompletos = Pedido.builder()
                .cliente(cliente)
                .listaProductos(productosIncompletos)
                .build();

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoCompleto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoConProductosIncompletos);

        // When
        Pedido result = pedidoService.guardar(pedidoConProductosIncompletos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getListaProductos().size());
        assertEquals(productoCompleto, result.getListaProductos().get(0));
        verify(productoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void guardarConProductoIncompletoNoEncontrado() {
        // Given
        Producto productoIncompleto = Producto.builder()
                .id(99L)
                .build(); // Solo ID, sin nombre ni precio

        List<Producto> productosIncompletos = Arrays.asList(productoIncompleto);

        Pedido pedidoConProductosIncompletos = Pedido.builder()
                .cliente(cliente)
                .listaProductos(productosIncompletos)
                .build();

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.guardar(pedidoConProductosIncompletos);
        });

        assertEquals("Producto no encontrado con ID: 99", exception.getMessage());
        verify(productoRepository, times(1)).findById(99L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void guardarConProductosCompletos() {
        // Given
        Producto productoCompleto = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .precio(new BigDecimal("25.99"))
                .build();

        List<Producto> productosCompletos = Arrays.asList(productoCompleto);

        Pedido pedidoConProductosCompletos = Pedido.builder()
                .cliente(cliente)
                .listaProductos(productosCompletos)
                .build();

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoConProductosCompletos);

        // When
        Pedido result = pedidoService.guardar(pedidoConProductosCompletos);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getListaProductos().size());
        assertEquals(productoCompleto, result.getListaProductos().get(0));
        verify(productoRepository, never()).findById(any(Long.class)); // No debe buscar productos completos
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void guardarSinFecha() {
        // Given
        Pedido pedidoSinFecha = Pedido.builder()
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .estado("PENDIENTE")
                .build(); // Sin fecha

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSinFecha);

        // When
        Pedido result = pedidoService.guardar(pedidoSinFecha);

        // Then
        assertNotNull(result);
        assertNotNull(result.getFecha()); // Debe tener fecha asignada
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void guardarSinEstado() {
        // Given
        Pedido pedidoSinEstado = Pedido.builder()
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .fecha(LocalDateTime.now())
                .build(); // Sin estado

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSinEstado);

        // When
        Pedido result = pedidoService.guardar(pedidoSinEstado);

        // Then
        assertNotNull(result);
        assertEquals("PENDIENTE", result.getEstado()); // Debe tener estado por defecto
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void guardarConEstadoVacio() {
        // Given
        Pedido pedidoConEstadoVacio = Pedido.builder()
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .fecha(LocalDateTime.now())
                .estado("   ") // Estado vacío
                .build();

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoConEstadoVacio);

        // When
        Pedido result = pedidoService.guardar(pedidoConEstadoVacio);

        // Then
        assertNotNull(result);
        assertEquals("PENDIENTE", result.getEstado()); // Debe tener estado por defecto
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void crearDesdePedidoRequest() {
        // Given
        LocalDateTime fechaRequest = LocalDateTime.now();
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L, 2L))
                .estado("CONFIRMADO")
                .fecha(fechaRequest)
                .build();

        Producto producto2 = Producto.builder()
                .id(2L)
                .nombre("Don Quijote")
                .precio(new BigDecimal("30.99"))
                .build();

        Pedido pedidoCreado = Pedido.builder()
                .id(1L)
                .cliente(cliente)
                .listaProductos(Arrays.asList(producto, producto2))
                .estado("CONFIRMADO")
                .fecha(fechaRequest)
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.findById(2L)).thenReturn(Optional.of(producto2));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoCreado);

        // When
        Pedido result = pedidoService.crearDesdePedidoRequest(pedidoRequest);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(cliente, result.getCliente());
        assertEquals(2, result.getListaProductos().size());
        assertEquals("CONFIRMADO", result.getEstado());
        assertEquals(fechaRequest, result.getFecha());
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(2L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void crearDesdePedidoRequestConClienteNoEncontrado() {
        // Given
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .clienteId(99L)
                .productosIds(Arrays.asList(1L))
                .build();

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.crearDesdePedidoRequest(pedidoRequest);
        });

        assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
        verify(clienteRepository, times(1)).findById(99L);
        verify(productoRepository, never()).findById(any(Long.class));
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void crearDesdePedidoRequestConProductoNoEncontrado() {
        // Given
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L, 99L))
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.crearDesdePedidoRequest(pedidoRequest);
        });

        assertEquals("Producto no encontrado con ID: 99", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(99L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void crearDesdePedidoRequestSinProductos() {
        // Given
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(null) // Sin productos
                .build();

        Pedido pedidoCreado = Pedido.builder()
                .id(1L)
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .estado("PENDIENTE")
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoCreado);

        // When
        Pedido result = pedidoService.crearDesdePedidoRequest(pedidoRequest);

        // Then
        assertNotNull(result);
        assertEquals(cliente, result.getCliente());
        assertTrue(result.getListaProductos().isEmpty());
        assertEquals("PENDIENTE", result.getEstado());
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, never()).findById(any(Long.class));
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void crearDesdePedidoRequestConProductosVacios() {
        // Given
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(new ArrayList<>()) // Lista vacía
                .build();

        Pedido pedidoCreado = Pedido.builder()
                .id(1L)
                .cliente(cliente)
                .listaProductos(new ArrayList<>())
                .estado("PENDIENTE")
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoCreado);

        // When
        Pedido result = pedidoService.crearDesdePedidoRequest(pedidoRequest);

        // Then
        assertNotNull(result);
        assertEquals(cliente, result.getCliente());
        assertTrue(result.getListaProductos().isEmpty());
        assertEquals("PENDIENTE", result.getEstado());
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, never()).findById(any(Long.class));
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void crearDesdePedidoRequestConValoresPorDefecto() {
        // Given
        PedidoRequest pedidoRequest = PedidoRequest.builder()
                .clienteId(1L)
                .productosIds(Arrays.asList(1L))
                .estado(null) // Sin estado
                .fecha(null) // Sin fecha
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedidoArgument = invocation.getArgument(0);
            return Pedido.builder()
                    .id(1L)
                    .cliente(pedidoArgument.getCliente())
                    .listaProductos(pedidoArgument.getListaProductos())
                    .estado(pedidoArgument.getEstado())
                    .fecha(pedidoArgument.getFecha())
                    .build();
        });

        // When
        Pedido result = pedidoService.crearDesdePedidoRequest(pedidoRequest);

        // Then
        assertNotNull(result);
        assertEquals(cliente, result.getCliente());
        assertEquals(1, result.getListaProductos().size());
        assertEquals("PENDIENTE", result.getEstado()); // Estado por defecto
        assertNotNull(result.getFecha()); // Fecha asignada automáticamente
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }
}
