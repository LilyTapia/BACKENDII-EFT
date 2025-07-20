package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setNombre("ROLE_CLIENTE");
    }

    @Test
    void testObtenerTodos() {
        List<Role> roles = Arrays.asList(role);
        when(roleService.obtenerTodos()).thenReturn(roles);

        ResponseEntity<List<Role>> response = roleController.obtenerTodos();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testObtenerPorNombre() {
        when(roleService.obtenerPorNombre("ROLE_CLIENTE")).thenReturn(Optional.of(role));

        ResponseEntity<Role> response = roleController.obtenerPorNombre("ROLE_CLIENTE");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("ROLE_CLIENTE", response.getBody().getNombre());
    }

    @Test
    void testObtenerPorNombreNoEncontrado() {
        when(roleService.obtenerPorNombre("ROLE_GERENTE")).thenReturn(Optional.empty());

        ResponseEntity<Role> response = roleController.obtenerPorNombre("ROLE_GERENTE");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testCrearRole() {
        when(roleService.guardar(any(Role.class))).thenReturn(role);

        ResponseEntity<Role> response = roleController.crearRole(role);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("ROLE_CLIENTE", response.getBody().getNombre());
    }

    @Test
    void testEliminarRole() {
        when(roleService.obtenerPorNombre("ROLE_CLIENTE")).thenReturn(Optional.of(role));
        doNothing().when(roleService).eliminar("ROLE_CLIENTE");

        ResponseEntity<Void> response = roleController.eliminarRole("ROLE_CLIENTE");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testEliminarRoleNoEncontrado() {
        when(roleService.obtenerPorNombre("ROLE_GERENTE")).thenReturn(Optional.empty());

        ResponseEntity<Void> response = roleController.eliminarRole("ROLE_GERENTE");

        assertEquals(404, response.getStatusCode().value());
    }
}