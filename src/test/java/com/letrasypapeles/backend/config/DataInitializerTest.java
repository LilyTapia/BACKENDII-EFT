package com.letrasypapeles.backend.config;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DataInitializerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(roleRepository, clienteRepository, productoRepository,
              categoriaRepository, inventarioRepository, reservaRepository,
              pedidoRepository, proveedorRepository, sucursalRepository,
              notificacionRepository, passwordEncoder);
    }

    @Test
    void run_WhenRolesExist_ShouldNotInitializeData() throws Exception {
        // Given
        when(roleRepository.count()).thenReturn(1L);

        // When
        dataInitializer.run();

        // Then
        verify(roleRepository).count();
        verify(roleRepository, never()).save(any(Role.class));
        verify(clienteRepository, never()).save(any(Cliente.class));
        verify(productoRepository, never()).saveAll(any());
        verify(categoriaRepository, never()).saveAll(any());
        verify(inventarioRepository, never()).saveAll(any());
        verify(reservaRepository, never()).saveAll(any());
        verify(pedidoRepository, never()).saveAll(any());
    }

    @Test
    void run_WhenNoRolesExist_ShouldInitializeAllData() throws Exception {
        // Given
        when(roleRepository.count()).thenReturn(0L);
        when(roleRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Role mockRole = new Role();
        mockRole.setNombre("ROLE_CLIENTE");
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        Cliente mockCliente = new Cliente();
        mockCliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(mockCliente);

        // When
        dataInitializer.run();

        // Then
        verify(roleRepository).count();
        verify(roleRepository, atLeastOnce()).save(any(Role.class));
        verify(clienteRepository, atLeastOnce()).save(any(Cliente.class));
        verify(productoRepository, atLeastOnce()).save(any());
        verify(categoriaRepository, atLeastOnce()).save(any());
        verify(inventarioRepository, atLeastOnce()).save(any());
        verify(reservaRepository, atLeastOnce()).save(any());
        verify(pedidoRepository, atLeastOnce()).save(any());
    }

    @Test
    void run_ShouldCreateCorrectRoles() throws Exception {
        // Given
        when(roleRepository.count()).thenReturn(0L);
        when(roleRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Role mockRole = new Role();
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        Cliente mockCliente = new Cliente();
        mockCliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(mockCliente);

        // When
        dataInitializer.run();

        // Then
        verify(roleRepository, times(3)).save(any(Role.class));
    }

    @Test
    void run_ShouldCreateTestUsers() throws Exception {
        // Given
        when(roleRepository.count()).thenReturn(0L);
        when(roleRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Role mockRole = new Role();
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        Cliente mockCliente = new Cliente();
        mockCliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(mockCliente);

        // When
        dataInitializer.run();

        // Then
        verify(clienteRepository, atLeast(3)).save(any(Cliente.class));
        verify(passwordEncoder, atLeast(3)).encode(anyString());
    }

    @Test
    void run_ShouldHandlePasswordEncoding() throws Exception {
        // Given
        when(roleRepository.count()).thenReturn(0L);
        when(roleRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Role mockRole = new Role();
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        Cliente mockCliente = new Cliente();
        mockCliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(mockCliente);

        // When
        dataInitializer.run();

        // Then
        verify(passwordEncoder, atLeast(3)).encode(anyString());
    }

    @Test
    void run_ShouldCreateSampleData() throws Exception {
        // Given
        when(roleRepository.count()).thenReturn(0L);
        when(roleRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Role mockRole = new Role();
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        Cliente mockCliente = new Cliente();
        mockCliente.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(mockCliente);

        // When
        dataInitializer.run();

        // Then
        verify(productoRepository, atLeastOnce()).save(any());
        verify(categoriaRepository, atLeastOnce()).save(any());
        verify(inventarioRepository, atLeastOnce()).save(any());
        verify(reservaRepository, atLeastOnce()).save(any());
        verify(pedidoRepository, atLeastOnce()).save(any());
    }
}
