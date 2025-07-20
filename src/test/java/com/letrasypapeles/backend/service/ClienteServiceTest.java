package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.ClienteRepository;
import com.letrasypapeles.backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private Role role;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        role = new Role();
        role.setNombre("ROLE_CLIENTE");

        cliente = Cliente.builder()
                .id(1L)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .contraseña("password123")
                .puntosFidelidad(0)
                .build();
    }

    @Test
    void obtenerTodos() {
        // Given
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        // When
        List<Cliente> result = clienteService.obtenerTodos();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        Optional<Cliente> result = clienteService.obtenerPorId(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorIdNoExistente() {
        // Given
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Cliente> result = clienteService.obtenerPorId(99L);

        // Then
        assertFalse(result.isPresent());
        verify(clienteRepository, times(1)).findById(99L);
    }

    @Test
    void obtenerPorEmail() {
        // Given
        when(clienteRepository.findByEmail("test@example.com")).thenReturn(Optional.of(cliente));

        // When
        Optional<Cliente> result = clienteService.obtenerPorEmail("test@example.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getNombre());
        verify(clienteRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void obtenerPorEmailNoExistente() {
        // Given
        when(clienteRepository.findByEmail("noexiste@example.com")).thenReturn(Optional.empty());

        // When
        Optional<Cliente> result = clienteService.obtenerPorEmail("noexiste@example.com");

        // Then
        assertFalse(result.isPresent());
        verify(clienteRepository, times(1)).findByEmail("noexiste@example.com");
    }

    @Test
    void registrarCliente() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = "encoded_password";
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(roleRepository.findByNombre("ROLE_CLIENTE")).thenReturn(Optional.of(role));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente clienteInput = Cliente.builder()
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .contraseña(rawPassword)
                .build();
        Cliente result = clienteService.registrarCliente(clienteInput);

        // Then
        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(roleRepository, times(1)).findByNombre("ROLE_CLIENTE");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void registrarClienteEmailDuplicado() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        Cliente clienteInput = Cliente.builder()
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .contraseña("password123")
                .build();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.registrarCliente(clienteInput);
        });

        assertEquals("El correo electrónico ya está registrado.", exception.getMessage());
        verify(clienteRepository, times(1)).existsByEmail(anyString());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void actualizarCliente() {
        // Given
        Cliente clienteActualizado = Cliente.builder()
                .id(1L)
                .nombre("Test Actualizado")
                .apellido("Usuario Actualizado")
                .email("test@example.com")
                .contraseña("password123")
                .puntosFidelidad(10)
                .build();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        Cliente result = clienteService.actualizarCliente(clienteActualizado);

        // Then
        assertNotNull(result);
        assertEquals("Test Actualizado", result.getNombre());
        assertEquals("Usuario Actualizado", result.getApellido());
        assertEquals(10, result.getPuntosFidelidad());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void eliminar() {
        // Given
        Long idToDelete = 1L;
        doNothing().when(clienteRepository).deleteById(idToDelete);

        // When
        clienteService.eliminar(idToDelete);

        // Then
        verify(clienteRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void acumularPuntosFidelidad_ClienteExistente() {
        // Given
        Long clienteId = 1L;
        Double montoCompra = 100.0;
        Cliente clienteExistente = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .puntosFidelidad(50)
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .puntosFidelidad(60) // 50 + 10 puntos (100/10)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        Cliente result = clienteService.acumularPuntosFidelidad(clienteId, montoCompra);

        // Then
        assertNotNull(result);
        assertEquals(60, result.getPuntosFidelidad());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void acumularPuntosFidelidad_ClienteSinPuntosIniciales() {
        // Given
        Long clienteId = 1L;
        Double montoCompra = 50.0;
        Cliente clienteExistente = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .puntosFidelidad(null) // Sin puntos iniciales
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .puntosFidelidad(5) // 0 + 5 puntos (50/10)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        Cliente result = clienteService.acumularPuntosFidelidad(clienteId, montoCompra);

        // Then
        assertNotNull(result);
        assertEquals(5, result.getPuntosFidelidad());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void acumularPuntosFidelidad_ClienteNoExistente() {
        // Given
        Long clienteId = 999L;
        Double montoCompra = 100.0;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.acumularPuntosFidelidad(clienteId, montoCompra);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void acumularPuntosFidelidad_MontoMenorA10() {
        // Given
        Long clienteId = 1L;
        Double montoCompra = 5.0; // Menor a 10, no debería generar puntos
        Cliente clienteExistente = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .puntosFidelidad(20)
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .puntosFidelidad(20) // Sin cambios
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        Cliente result = clienteService.acumularPuntosFidelidad(clienteId, montoCompra);

        // Then
        assertNotNull(result);
        assertEquals(20, result.getPuntosFidelidad());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void tieneRole_ClienteConRole() {
        // Given
        Long clienteId = 1L;
        String nombreRole = "ROLE_CLIENTE";
        Role roleCliente = new Role();
        roleCliente.setNombre("ROLE_CLIENTE");

        Cliente clienteConRole = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(Set.of(roleCliente))
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteConRole));

        // When
        boolean result = clienteService.tieneRole(clienteId, nombreRole);

        // Then
        assertTrue(result);
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void tieneRole_ClienteSinRole() {
        // Given
        Long clienteId = 1L;
        String nombreRole = "ADMIN";
        Role roleCliente = new Role();
        roleCliente.setNombre("ROLE_CLIENTE");

        Cliente clienteConRole = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(Set.of(roleCliente))
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteConRole));

        // When
        boolean result = clienteService.tieneRole(clienteId, nombreRole);

        // Then
        assertFalse(result);
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    void asignarRole_Exitoso() {
        // Given
        Long clienteId = 1L;
        String nombreRole = "ADMIN";
        Role roleAdmin = new Role();
        roleAdmin.setNombre("ADMIN");

        Cliente clienteExistente = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(new HashSet<>())
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(Set.of(roleAdmin))
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(roleRepository.findByNombre(nombreRole)).thenReturn(Optional.of(roleAdmin));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        Cliente result = clienteService.asignarRole(clienteId, nombreRole);

        // Then
        assertNotNull(result);
        assertTrue(result.getRoles().contains(roleAdmin));
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(roleRepository, times(1)).findByNombre(nombreRole);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void asignarRole_ClienteNoExistente() {
        // Given
        Long clienteId = 999L;
        String nombreRole = "ADMIN";
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.asignarRole(clienteId, nombreRole);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(roleRepository, never()).findByNombre(anyString());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void asignarRole_RoleNoExistente() {
        // Given
        Long clienteId = 1L;
        String nombreRole = "ROLE_INEXISTENTE";
        Cliente clienteExistente = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(new HashSet<>())
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(roleRepository.findByNombre(nombreRole)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.asignarRole(clienteId, nombreRole);
        });

        assertEquals("Role no encontrado: " + nombreRole, exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(roleRepository, times(1)).findByNombre(nombreRole);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void removerRole_Exitoso() {
        // Given
        Long clienteId = 1L;
        String nombreRole = "ADMIN";
        Role roleAdmin = new Role();
        roleAdmin.setNombre("ADMIN");

        Set<Role> rolesIniciales = new HashSet<>();
        rolesIniciales.add(roleAdmin);

        Cliente clienteExistente = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(rolesIniciales)
                .build();

        Cliente clienteActualizado = Cliente.builder()
                .id(clienteId)
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .roles(new HashSet<>())
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(roleRepository.findByNombre(nombreRole)).thenReturn(Optional.of(roleAdmin));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // When
        Cliente result = clienteService.removerRole(clienteId, nombreRole);

        // Then
        assertNotNull(result);
        assertFalse(result.getRoles().contains(roleAdmin));
        verify(clienteRepository, times(1)).findById(clienteId);
        verify(roleRepository, times(1)).findByNombre(nombreRole);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void registrarCliente_RoleNoExistente_CreaRoleNuevo() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = "encoded_password";
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(roleRepository.findByNombre("ROLE_CLIENTE")).thenReturn(Optional.empty()); // Role no existe
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente clienteInput = Cliente.builder()
                .nombre("Test")
                .apellido("Usuario")
                .email("test@example.com")
                .contraseña(rawPassword)
                .build();
        Cliente result = clienteService.registrarCliente(clienteInput);

        // Then
        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(roleRepository, times(1)).findByNombre("ROLE_CLIENTE");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        // Verificar que se inicializan los puntos de fidelidad en 0
        assertEquals(0, clienteInput.getPuntosFidelidad());
    }

    @Test
    void registrarClienteConRoles_ClienteNuevo_RegistraConRolesEspecificos() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setApellido("User");
        cliente.setEmail("test@example.com");
        cliente.setContraseña("password123");

        Set<String> roleNames = Set.of("ROLE_GERENTE", "ROLE_EMPLEADO");

        Role roleGerente = new Role();
        roleGerente.setNombre("ROLE_GERENTE");
        Role roleEmpleado = new Role();
        roleEmpleado.setNombre("ROLE_EMPLEADO");

        when(clienteRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(roleRepository.findByNombre("ROLE_GERENTE")).thenReturn(Optional.of(roleGerente));
        when(roleRepository.findByNombre("ROLE_EMPLEADO")).thenReturn(Optional.of(roleEmpleado));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente resultado = clienteService.registrarClienteConRoles(cliente, roleNames);

        // Then
        assertNotNull(resultado);
        assertEquals("encodedPassword", cliente.getContraseña());
        assertEquals(0, cliente.getPuntosFidelidad());
        assertEquals(2, cliente.getRoles().size());
        assertTrue(cliente.getRoles().contains(roleGerente));
        assertTrue(cliente.getRoles().contains(roleEmpleado));

        verify(clienteRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(roleRepository).findByNombre("ROLE_GERENTE");
        verify(roleRepository).findByNombre("ROLE_EMPLEADO");
        verify(clienteRepository).save(cliente);
    }

    @Test
    void registrarClienteConRoles_EmailYaExiste_LanzaExcepcion() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setEmail("existing@example.com");
        Set<String> roleNames = Set.of("ROLE_CLIENTE");

        when(clienteRepository.existsByEmail("existing@example.com")).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.registrarClienteConRoles(cliente, roleNames);
        });

        assertEquals("El correo electrónico ya está registrado.", exception.getMessage());
        verify(clienteRepository).existsByEmail("existing@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void registrarClienteConRoles_RoleNoExiste_CreaRoleNuevo() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setApellido("User");
        cliente.setEmail("test@example.com");
        cliente.setContraseña("password123");

        Set<String> roleNames = Set.of("ROLE_NUEVO");

        Role roleNuevo = new Role();
        roleNuevo.setNombre("ROLE_NUEVO");

        when(clienteRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(roleRepository.findByNombre("ROLE_NUEVO")).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(roleNuevo);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente resultado = clienteService.registrarClienteConRoles(cliente, roleNames);

        // Then
        assertNotNull(resultado);
        assertEquals(1, cliente.getRoles().size());
        assertTrue(cliente.getRoles().contains(roleNuevo));

        verify(roleRepository).findByNombre("ROLE_NUEVO");
        verify(roleRepository).save(any(Role.class));
        verify(clienteRepository).save(cliente);
    }

    @Test
    void registrarClienteConRoles_MultipleRoles_RegistraCorrectamente() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("Multi");
        cliente.setApellido("Role");
        cliente.setEmail("multi@example.com");
        cliente.setContraseña("password123");

        Set<String> roleNames = Set.of("ROLE_GERENTE", "ROLE_EMPLEADO", "ROLE_CLIENTE");

        Role roleGerente = new Role();
        roleGerente.setNombre("ROLE_GERENTE");
        Role roleEmpleado = new Role();
        roleEmpleado.setNombre("ROLE_EMPLEADO");
        Role roleCliente = new Role();
        roleCliente.setNombre("ROLE_CLIENTE");

        when(clienteRepository.existsByEmail("multi@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(roleRepository.findByNombre("ROLE_GERENTE")).thenReturn(Optional.of(roleGerente));
        when(roleRepository.findByNombre("ROLE_EMPLEADO")).thenReturn(Optional.of(roleEmpleado));
        when(roleRepository.findByNombre("ROLE_CLIENTE")).thenReturn(Optional.of(roleCliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente resultado = clienteService.registrarClienteConRoles(cliente, roleNames);

        // Then
        assertNotNull(resultado);
        assertEquals(3, cliente.getRoles().size());
        assertTrue(cliente.getRoles().contains(roleGerente));
        assertTrue(cliente.getRoles().contains(roleEmpleado));
        assertTrue(cliente.getRoles().contains(roleCliente));

        verify(roleRepository).findByNombre("ROLE_GERENTE");
        verify(roleRepository).findByNombre("ROLE_EMPLEADO");
        verify(roleRepository).findByNombre("ROLE_CLIENTE");
    }

    @Test
    void registrarClienteConRoles_EmptyRoleSet_NoAsignaRoles() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNombre("No");
        cliente.setApellido("Roles");
        cliente.setEmail("noroles@example.com");
        cliente.setContraseña("password123");

        Set<String> roleNames = Set.of(); // Empty set

        when(clienteRepository.existsByEmail("noroles@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente resultado = clienteService.registrarClienteConRoles(cliente, roleNames);

        // Then
        assertNotNull(resultado);
        assertEquals(0, cliente.getRoles().size());

        verify(roleRepository, never()).findByNombre(anyString());
        verify(roleRepository, never()).save(any(Role.class));
    }

    // Missing exception tests for 100% coverage

    @Test
    void tieneRole_ClienteNoEncontrado() {
        // Given
        Long clienteId = 999L;
        String nombreRole = "ADMIN";
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.tieneRole(clienteId, nombreRole);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteRepository).findById(clienteId);
    }

    @Test
    void removerRole_ClienteNoEncontrado() {
        // Given
        Long clienteId = 999L;
        String nombreRole = "ADMIN";
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.removerRole(clienteId, nombreRole);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteRepository).findById(clienteId);
        verify(roleRepository, never()).findByNombre(anyString());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void removerRole_RoleNoEncontrado() {
        // Given
        Long clienteId = 1L;
        String nombreRole = "ROLE_INEXISTENTE";
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(roleRepository.findByNombre(nombreRole)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.removerRole(clienteId, nombreRole);
        });

        assertEquals("Role no encontrado: " + nombreRole, exception.getMessage());
        verify(clienteRepository).findById(clienteId);
        verify(roleRepository).findByNombre(nombreRole);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}
