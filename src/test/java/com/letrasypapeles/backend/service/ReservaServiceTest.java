package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.dto.ReservaRequest;
import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.repository.ReservaRepository;
import com.letrasypapeles.backend.repository.ProductoRepository;
import com.letrasypapeles.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;
    private Cliente cliente;
    private Producto producto;
    private LocalDateTime fechaReserva;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        fechaReserva = LocalDateTime.now();
        
        cliente = Cliente.builder()
                .id(1L)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .build();

        producto = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .descripcion("Obra de Miguel de Cervantes")
                .precio(new BigDecimal("29.99"))
                .stock(50)
                .build();

        reserva = Reserva.builder()
                .id(1L)
                .fechaReserva(fechaReserva)
                .estado("PENDIENTE")
                .cliente(cliente)
                .producto(producto)
                .build();
    }

    @Test
    void obtenerTodas() {
        // Given
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findAll()).thenReturn(reservas);

        // When
        List<Reserva> result = reservaService.obtenerTodas();

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId() {
        // Given
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        // When
        Optional<Reserva> result = reservaService.obtenerPorId(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("PENDIENTE", result.get().getEstado());
        assertEquals(cliente.getId(), result.get().getCliente().getId());
        assertEquals(producto.getId(), result.get().getProducto().getId());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorIdNoExistente() {
        // Given
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Reserva> result = reservaService.obtenerPorId(99L);

        // Then
        assertFalse(result.isPresent());
        verify(reservaRepository, times(1)).findById(99L);
    }

    @Test
    void guardar() {
        // Given
        Reserva reservaNueva = Reserva.builder()
                .fechaReserva(fechaReserva)
                .estado("NUEVA")
                .cliente(cliente)
                .producto(producto)
                .build();

        Reserva reservaGuardada = Reserva.builder()
                .id(2L)
                .fechaReserva(fechaReserva)
                .estado("NUEVA")
                .cliente(cliente)
                .producto(producto)
                .build();
        
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        // When
        Reserva result = reservaService.guardar(reservaNueva);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("NUEVA", result.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void eliminar() {
        // Given
        Long idToDelete = 1L;
        doNothing().when(reservaRepository).deleteById(idToDelete);

        // When
        reservaService.eliminar(idToDelete);

        // Then
        verify(reservaRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void obtenerPorClienteId() {
        // Given
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findByClienteId(1L)).thenReturn(reservas);

        // When
        List<Reserva> result = reservaService.obtenerPorClienteId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        assertEquals(1L, result.get(0).getCliente().getId());
        verify(reservaRepository, times(1)).findByClienteId(1L);
    }

    @Test
    void obtenerPorProductoId() {
        // Given
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findByProductoId(1L)).thenReturn(reservas);

        // When
        List<Reserva> result = reservaService.obtenerPorProductoId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        assertEquals(1L, result.get(0).getProducto().getId());
        verify(reservaRepository, times(1)).findByProductoId(1L);
    }

    @Test
    void obtenerPorEstado() {
        // Given
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findByEstado("PENDIENTE")).thenReturn(reservas);

        // When
        List<Reserva> result = reservaService.obtenerPorEstado("PENDIENTE");

        // Then
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        verify(reservaRepository, times(1)).findByEstado("PENDIENTE");
    }

    @Test
    void crearReservaConValidacion_Exitoso() {
        // Given
        Long clienteId = 1L;
        Long productoId = 1L;
        Integer cantidad = 5;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // When
        Reserva result = reservaService.crearReservaConValidacion(clienteId, productoId, cantidad);

        // Then
        assertNotNull(result);
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(productoRepository, times(1)).findById(productoId);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void crearReservaConValidacion_ClienteNoExistente() {
        // Given
        Long clienteId = 999L;
        Long productoId = 1L;
        Integer cantidad = 5;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReservaConValidacion(clienteId, productoId, cantidad);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(productoRepository, never()).findById(anyLong());
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void crearReservaConValidacion_ProductoNoExistente() {
        // Given
        Long clienteId = 1L;
        Long productoId = 999L;
        Integer cantidad = 5;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(productoId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReservaConValidacion(clienteId, productoId, cantidad);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(productoRepository, times(1)).findById(productoId);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void crearReservaConValidacion_StockInsuficiente() {
        // Given
        Long clienteId = 1L;
        Long productoId = 1L;
        Integer cantidad = 60; // Mayor al stock disponible (50)

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReservaConValidacion(clienteId, productoId, cantidad);
        });

        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(productoRepository, times(1)).findById(productoId);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void crearReservaConValidacion_ProductoSinStock() {
        // Given
        Long clienteId = 1L;
        Long productoId = 1L;
        Integer cantidad = 1;

        Producto productoSinStock = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .stock(null)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(productoId)).thenReturn(Optional.of(productoSinStock));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearReservaConValidacion(clienteId, productoId, cantidad);
        });

        assertTrue(exception.getMessage().contains("Stock insuficiente"));
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(productoRepository, times(1)).findById(productoId);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void confirmarReserva_Exitoso() {
        // Given
        Long reservaId = 1L;
        Reserva reservaPendiente = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("PENDIENTE")
                .cliente(cliente)
                .producto(producto)
                .build();

        Reserva reservaConfirmada = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("CONFIRMADA")
                .cliente(cliente)
                .producto(producto)
                .build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaPendiente));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaConfirmada);

        // When
        Reserva result = reservaService.confirmarReserva(reservaId);

        // Then
        assertNotNull(result);
        assertEquals("CONFIRMADA", result.getEstado());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(productoRepository, times(1)).save(any(Producto.class));
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void confirmarReserva_ReservaNoExistente() {
        // Given
        Long reservaId = 999L;
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.confirmarReserva(reservaId);
        });

        assertEquals("Reserva no encontrada", exception.getMessage());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(productoRepository, never()).save(any(Producto.class));
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void confirmarReserva_EstadoInvalido() {
        // Given
        Long reservaId = 1L;
        Reserva reservaConfirmada = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("CONFIRMADA")
                .cliente(cliente)
                .producto(producto)
                .build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaConfirmada));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.confirmarReserva(reservaId);
        });

        assertEquals("Solo se pueden confirmar reservas en estado PENDIENTE", exception.getMessage());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(productoRepository, never()).save(any(Producto.class));
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void confirmarReserva_StockInsuficiente() {
        // Given
        Long reservaId = 1L;
        Producto productoSinStock = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .stock(0)
                .build();

        Reserva reservaPendiente = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("PENDIENTE")
                .cliente(cliente)
                .producto(productoSinStock)
                .build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaPendiente));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.confirmarReserva(reservaId);
        });

        assertEquals("Stock insuficiente para confirmar la reserva", exception.getMessage());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(productoRepository, never()).save(any(Producto.class));
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void cancelarReserva_Exitoso() {
        // Given
        Long reservaId = 1L;
        Reserva reservaPendiente = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("PENDIENTE")
                .cliente(cliente)
                .producto(producto)
                .build();

        Reserva reservaCancelada = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("CANCELADA")
                .cliente(cliente)
                .producto(producto)
                .build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaPendiente));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaCancelada);

        // When
        Reserva result = reservaService.cancelarReserva(reservaId);

        // Then
        assertNotNull(result);
        assertEquals("CANCELADA", result.getEstado());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void cancelarReserva_ReservaConfirmada() {
        // Given
        Long reservaId = 1L;
        Reserva reservaConfirmada = Reserva.builder()
                .id(reservaId)
                .fechaReserva(fechaReserva)
                .estado("CONFIRMADA")
                .cliente(cliente)
                .producto(producto)
                .build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaConfirmada));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.cancelarReserva(reservaId);
        });

        assertEquals("No se puede cancelar una reserva ya confirmada", exception.getMessage());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void cancelarReserva_ReservaNoEncontrada() {
        // Given
        Long reservaId = 999L;
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.cancelarReserva(reservaId);
        });

        assertEquals("Reserva no encontrada", exception.getMessage());
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void testPuedeReservar_ProductoExisteConStock() {
        // Given
        Long productoId = 1L;
        Integer cantidad = 5;

        Producto producto = Producto.builder()
                .id(productoId)
                .nombre("Producto Test")
                .stock(10)
                .build();

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        // When
        boolean resultado = reservaService.puedeReservar(productoId, cantidad);

        // Then
        assertTrue(resultado);
        verify(productoRepository).findById(productoId);
    }

    @Test
    void testPuedeReservar_ProductoExisteStockInsuficiente() {
        // Given
        Long productoId = 1L;
        Integer cantidad = 15;

        Producto producto = Producto.builder()
                .id(productoId)
                .nombre("Producto Test")
                .stock(10)
                .build();

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        // When
        boolean resultado = reservaService.puedeReservar(productoId, cantidad);

        // Then
        assertFalse(resultado);
        verify(productoRepository).findById(productoId);
    }

    @Test
    void testPuedeReservar_ProductoNoExiste() {
        // Given
        Long productoId = 1L;
        Integer cantidad = 5;

        when(productoRepository.findById(productoId)).thenReturn(Optional.empty());

        // When
        boolean resultado = reservaService.puedeReservar(productoId, cantidad);

        // Then
        assertFalse(resultado);
        verify(productoRepository).findById(productoId);
    }

    @Test
    void testPuedeReservar_ProductoConStockNull() {
        // Given
        Long productoId = 1L;
        Integer cantidad = 5;

        Producto producto = Producto.builder()
                .id(productoId)
                .nombre("Producto Test")
                .stock(null)
                .build();

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        // When
        boolean resultado = reservaService.puedeReservar(productoId, cantidad);

        // Then
        assertFalse(resultado);
        verify(productoRepository).findById(productoId);
    }

    @Test
    void testContarReservasPendientes() {
        // Given
        Long productoId = 1L;

        Reserva reservaPendiente1 = Reserva.builder()
                .id(1L)
                .producto(producto)
                .estado("PENDIENTE")
                .build();

        Reserva reservaPendiente2 = Reserva.builder()
                .id(2L)
                .producto(producto)
                .estado("PENDIENTE")
                .build();

        Reserva reservaConfirmada = Reserva.builder()
                .id(3L)
                .producto(producto)
                .estado("CONFIRMADA")
                .build();

        List<Reserva> reservas = Arrays.asList(reservaPendiente1, reservaPendiente2, reservaConfirmada);
        when(reservaRepository.findByProductoId(productoId)).thenReturn(reservas);

        // When
        long count = reservaService.contarReservasPendientes(productoId);

        // Then
        assertEquals(2L, count);
        verify(reservaRepository).findByProductoId(productoId);
    }

    @Test
    void testContarReservasPendientes_SinReservasPendientes() {
        // Given
        Long productoId = 1L;

        Reserva reservaConfirmada = Reserva.builder()
                .id(1L)
                .producto(producto)
                .estado("CONFIRMADA")
                .build();

        Reserva reservaCancelada = Reserva.builder()
                .id(2L)
                .producto(producto)
                .estado("CANCELADA")
                .build();

        List<Reserva> reservas = Arrays.asList(reservaConfirmada, reservaCancelada);
        when(reservaRepository.findByProductoId(productoId)).thenReturn(reservas);

        // When
        long count = reservaService.contarReservasPendientes(productoId);

        // Then
        assertEquals(0L, count);
        verify(reservaRepository).findByProductoId(productoId);
    }

    @Test
    void testConfirmarReserva_StockNullBranch() {
        // Given
        Long reservaId = 1L;

        Producto productoSinStock = Producto.builder()
                .id(1L)
                .nombre("Producto Test")
                .stock(null)  // Stock null para cubrir esta rama
                .precio(BigDecimal.valueOf(100.0))
                .build();

        Reserva reservaPendiente = Reserva.builder()
                .id(reservaId)
                .cliente(cliente)
                .producto(productoSinStock)
                .cantidad(2)
                .estado("PENDIENTE")
                .fechaReserva(fechaReserva)
                .build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaPendiente));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservaService.confirmarReserva(reservaId);
        });

        assertEquals("Stock insuficiente para confirmar la reserva", exception.getMessage());
        verify(reservaRepository).findById(reservaId);
        verify(reservaRepository, never()).save(any(Reserva.class));
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void crearDesdeReservaRequest() {
        // Given
        LocalDateTime fechaRequest = LocalDateTime.now();
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(1L)
                .cantidad(3)
                .estado("CONFIRMADA")
                .fechaReserva(fechaRequest)
                .build();

        Reserva reservaCreada = Reserva.builder()
                .id(1L)
                .cliente(cliente)
                .producto(producto)
                .cantidad(3)
                .estado("CONFIRMADA")
                .fechaReserva(fechaRequest)
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaCreada);

        // When
        Reserva result = reservaService.crearDesdeReservaRequest(reservaRequest);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(cliente, result.getCliente());
        assertEquals(producto, result.getProducto());
        assertEquals(3, result.getCantidad());
        assertEquals("CONFIRMADA", result.getEstado());
        assertEquals(fechaRequest, result.getFechaReserva());
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void crearDesdeReservaRequestConClienteNoEncontrado() {
        // Given
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .clienteId(99L)
                .productoId(1L)
                .cantidad(1)
                .build();

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearDesdeReservaRequest(reservaRequest);
        });

        assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
        verify(clienteRepository, times(1)).findById(99L);
        verify(productoRepository, never()).findById(any(Long.class));
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void crearDesdeReservaRequestConProductoNoEncontrado() {
        // Given
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(99L)
                .cantidad(1)
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.crearDesdeReservaRequest(reservaRequest);
        });

        assertEquals("Producto no encontrado con ID: 99", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(99L);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void crearDesdeReservaRequestConValoresPorDefecto() {
        // Given
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .clienteId(1L)
                .productoId(1L)
                .cantidad(null) // Sin cantidad
                .estado(null) // Sin estado
                .fechaReserva(null) // Sin fecha
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva reservaArgument = invocation.getArgument(0);
            return Reserva.builder()
                    .id(1L)
                    .cliente(reservaArgument.getCliente())
                    .producto(reservaArgument.getProducto())
                    .cantidad(reservaArgument.getCantidad())
                    .estado(reservaArgument.getEstado())
                    .fechaReserva(reservaArgument.getFechaReserva())
                    .build();
        });

        // When
        Reserva result = reservaService.crearDesdeReservaRequest(reservaRequest);

        // Then
        assertNotNull(result);
        assertEquals(cliente, result.getCliente());
        assertEquals(producto, result.getProducto());
        assertEquals(1, result.getCantidad()); // Valor por defecto
        assertEquals("PENDIENTE", result.getEstado()); // Valor por defecto
        assertNotNull(result.getFechaReserva()); // Fecha asignada automáticamente
        verify(clienteRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
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

        Reserva reservaConClienteIncompleto = Reserva.builder()
                .cliente(clienteIncompleto)
                .producto(producto)
                .cantidad(1)
                .build();

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteCompleto));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaConClienteIncompleto);

        // When
        Reserva result = reservaService.guardar(reservaConClienteIncompleto);

        // Then
        assertNotNull(result);
        assertEquals(clienteCompleto, result.getCliente());
        verify(clienteRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void guardarConClienteIncompletoNoEncontrado() {
        // Given
        Cliente clienteIncompleto = Cliente.builder()
                .id(99L)
                .build(); // Solo ID, sin nombre ni email

        Reserva reservaConClienteIncompleto = Reserva.builder()
                .cliente(clienteIncompleto)
                .producto(producto)
                .cantidad(1)
                .build();

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaConClienteIncompleto);
        });

        assertEquals("Cliente no encontrado con ID: 99", exception.getMessage());
        verify(clienteRepository, times(1)).findById(99L);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void guardarConProductoIncompleto() {
        // Given
        Producto productoIncompleto = Producto.builder()
                .id(1L)
                .build(); // Solo ID, sin nombre ni precio

        Producto productoCompleto = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .precio(new BigDecimal("25.99"))
                .stock(10)
                .build();

        Reserva reservaConProductoIncompleto = Reserva.builder()
                .cliente(cliente)
                .producto(productoIncompleto)
                .cantidad(1)
                .build();

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoCompleto));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaConProductoIncompleto);

        // When
        Reserva result = reservaService.guardar(reservaConProductoIncompleto);

        // Then
        assertNotNull(result);
        assertEquals(productoCompleto, result.getProducto());
        verify(productoRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void guardarConProductoIncompletoNoEncontrado() {
        // Given
        Producto productoIncompleto = Producto.builder()
                .id(99L)
                .build(); // Solo ID, sin nombre ni precio

        Reserva reservaConProductoIncompleto = Reserva.builder()
                .cliente(cliente)
                .producto(productoIncompleto)
                .cantidad(1)
                .build();

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaConProductoIncompleto);
        });

        assertEquals("Producto no encontrado con ID: 99", exception.getMessage());
        verify(productoRepository, times(1)).findById(99L);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void guardarConProductoCompleto() {
        // Given
        Producto productoCompleto = Producto.builder()
                .id(1L)
                .nombre("El Quijote")
                .precio(new BigDecimal("25.99"))
                .stock(10)
                .build();

        Reserva reservaConProductoCompleto = Reserva.builder()
                .cliente(cliente)
                .producto(productoCompleto)
                .cantidad(1)
                .build();

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaConProductoCompleto);

        // When
        Reserva result = reservaService.guardar(reservaConProductoCompleto);

        // Then
        assertNotNull(result);
        assertEquals(productoCompleto, result.getProducto());
        verify(productoRepository, never()).findById(any(Long.class)); // No debe buscar productos completos
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void guardarSinFechaReserva() {
        // Given
        Reserva reservaSinFecha = Reserva.builder()
                .cliente(cliente)
                .producto(producto)
                .cantidad(1)
                .estado("PENDIENTE")
                .build(); // Sin fecha

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaSinFecha);

        // When
        Reserva result = reservaService.guardar(reservaSinFecha);

        // Then
        assertNotNull(result);
        assertNotNull(result.getFechaReserva()); // Debe tener fecha asignada
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void guardarSinEstado() {
        // Given
        Reserva reservaSinEstado = Reserva.builder()
                .cliente(cliente)
                .producto(producto)
                .cantidad(1)
                .fechaReserva(LocalDateTime.now())
                .build(); // Sin estado

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaSinEstado);

        // When
        Reserva result = reservaService.guardar(reservaSinEstado);

        // Then
        assertNotNull(result);
        assertEquals("PENDIENTE", result.getEstado()); // Debe tener estado por defecto
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void guardarConEstadoVacio() {
        // Given
        Reserva reservaConEstadoVacio = Reserva.builder()
                .cliente(cliente)
                .producto(producto)
                .cantidad(1)
                .fechaReserva(LocalDateTime.now())
                .estado("   ") // Estado vacío
                .build();

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaConEstadoVacio);

        // When
        Reserva result = reservaService.guardar(reservaConEstadoVacio);

        // Then
        assertNotNull(result);
        assertEquals("PENDIENTE", result.getEstado()); // Debe tener estado por defecto
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
}
