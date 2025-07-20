package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.repository.ClienteRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public UsuarioService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String password = cliente.getContraseña();
        if (password == null || password.trim().isEmpty()) {
            password = "N/A"; // Default password for users without password
        }

        return new org.springframework.security.core.userdetails.User(
                cliente.getEmail(),
                password,
                getAuthorities(cliente)
        );
    }

    private Collection<GrantedAuthority> getAuthorities(Cliente cliente) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        System.out.println("=== DEBUG: Cargando autoridades para usuario: " + cliente.getEmail() + " ===");
        System.out.println("Roles encontrados: " + cliente.getRoles().size());

        cliente.getRoles().forEach(role -> {
            System.out.println("Rol original en BD: '" + role.getNombre() + "'");
            String roleName = role.getNombre().startsWith("ROLE_") ? role.getNombre() : "ROLE_" + role.getNombre();
            authorities.add(new SimpleGrantedAuthority(roleName));
            System.out.println("Autoridad agregada: '" + roleName + "'");
        });

        System.out.println("Total autoridades: " + authorities.size());
        System.out.println("=== FIN DEBUG ===");
        return new ArrayList<>(authorities);
    }
}
