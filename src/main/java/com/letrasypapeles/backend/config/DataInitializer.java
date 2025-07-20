package com.letrasypapeles.backend.config;

import com.letrasypapeles.backend.entity.*;
import com.letrasypapeles.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ProveedorRepository proveedorRepository;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize data if database is empty
        if (roleRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Initialize Roles
        Role roleCliente = new Role();
        roleCliente.setNombre("ROLE_CLIENTE");

        Role roleVendedor = new Role();
        roleVendedor.setNombre("ROLE_VENDEDOR");

        Role roleAdmin = new Role();
        roleAdmin.setNombre("ROLE_ADMIN");

        roleRepository.save(roleCliente);
        roleRepository.save(roleVendedor);
        roleRepository.save(roleAdmin);

        // Initialize Categories
        Categoria papeleria = new Categoria();
        papeleria.setNombre("Papelería");
        papeleria.setDescripcion("Productos de papelería y oficina");
        categoriaRepository.save(papeleria);

        Categoria libros = new Categoria();
        libros.setNombre("Libros");
        libros.setDescripcion("Libros y material de lectura");
        categoriaRepository.save(libros);

        Categoria arte = new Categoria();
        arte.setNombre("Arte");
        arte.setDescripcion("Materiales de arte y manualidades");
        categoriaRepository.save(arte);

        // Initialize Providers
        Proveedor distribuidora = new Proveedor();
        distribuidora.setNombre("Distribuidora Central");
        distribuidora.setContacto("contacto@distribuidora.com");
        proveedorRepository.save(distribuidora);

        Proveedor papeleriaMayorista = new Proveedor();
        papeleriaMayorista.setNombre("Papelería Mayorista");
        papeleriaMayorista.setContacto("ventas@papeleria.com");
        proveedorRepository.save(papeleriaMayorista);

        Proveedor editorial = new Proveedor();
        editorial.setNombre("Editorial Libros");
        editorial.setContacto("pedidos@editorial.com");
        proveedorRepository.save(editorial);

        // Initialize Branches
        Sucursal central = new Sucursal();
        central.setNombre("Sucursal Central");
        central.setRegion("Región Metropolitana");
        central.setDireccion("Av. Principal 123");
        sucursalRepository.save(central);

        Sucursal norte = new Sucursal();
        norte.setNombre("Sucursal Norte");
        norte.setRegion("Antofagasta");
        norte.setDireccion("Calle Norte 456");
        sucursalRepository.save(norte);

        Sucursal sur = new Sucursal();
        sur.setNombre("Sucursal Sur");
        sur.setRegion("Valparaíso");
        sur.setDireccion("Av. Sur 789");
        sucursalRepository.save(sur);

        // Initialize Clients
        Cliente juan = new Cliente();
        juan.setNombre("Juan");
        juan.setApellido("Pérez");
        juan.setEmail("juan.perez@example.com");
        juan.setContraseña(passwordEncoder.encode("password123"));
        juan.setPuntosFidelidad(150);
        juan.setRoles(Set.of(roleCliente));
        clienteRepository.save(juan);

        Cliente ana = new Cliente();
        ana.setNombre("Ana");
        ana.setApellido("López");
        ana.setEmail("ana.lopez@example.com");
        ana.setContraseña(passwordEncoder.encode("password456"));
        ana.setPuntosFidelidad(300);
        ana.setRoles(Set.of(roleCliente));
        clienteRepository.save(ana);

        Cliente carlos = new Cliente();
        carlos.setNombre("Carlos");
        carlos.setApellido("González");
        carlos.setEmail("carlos.gonzalez@example.com");
        carlos.setContraseña(passwordEncoder.encode("password789"));
        carlos.setPuntosFidelidad(75);
        carlos.setRoles(Set.of(roleCliente));
        clienteRepository.save(carlos);

        // Initialize Products
        Producto cuaderno = new Producto();
        cuaderno.setNombre("Cuaderno Universitario");
        cuaderno.setDescripcion("Cuaderno de 100 hojas rayadas");
        cuaderno.setPrecio(new BigDecimal("2500.00"));
        cuaderno.setStock(50);
        cuaderno.setCategoria(papeleria);
        cuaderno.setProveedor(distribuidora);
        productoRepository.save(cuaderno);

        Producto libro = new Producto();
        libro.setNombre("Libro de Cuentos");
        libro.setDescripcion("Libro infantil ilustrado");
        libro.setPrecio(new BigDecimal("7800.00"));
        libro.setStock(20);
        libro.setCategoria(libros);
        libro.setProveedor(editorial);
        productoRepository.save(libro);

        Producto lapices = new Producto();
        lapices.setNombre("Lápices de Colores");
        lapices.setDescripcion("Set de 12 lápices de colores");
        lapices.setPrecio(new BigDecimal("3200.00"));
        lapices.setStock(35);
        lapices.setCategoria(arte);
        lapices.setProveedor(papeleriaMayorista);
        productoRepository.save(lapices);

        Producto agenda = new Producto();
        agenda.setNombre("Agenda 2025");
        agenda.setDescripcion("Agenda ejecutiva anual");
        agenda.setPrecio(new BigDecimal("15000.00"));
        agenda.setStock(15);
        agenda.setCategoria(papeleria);
        agenda.setProveedor(distribuidora);
        productoRepository.save(agenda);

        Producto marcadores = new Producto();
        marcadores.setNombre("Marcadores");
        marcadores.setDescripcion("Set de marcadores permanentes");
        marcadores.setPrecio(new BigDecimal("4500.00"));
        marcadores.setStock(25);
        marcadores.setCategoria(arte);
        marcadores.setProveedor(papeleriaMayorista);
        productoRepository.save(marcadores);

        // Initialize Orders
        Pedido pedido1 = new Pedido();
        pedido1.setFecha(LocalDateTime.now().minusDays(2));
        pedido1.setEstado("PENDIENTE");
        pedido1.setCliente(juan);
        pedido1.setListaProductos(List.of(cuaderno, lapices));
        pedidoRepository.save(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setFecha(LocalDateTime.now().minusDays(3));
        pedido2.setEstado("CONFIRMADO");
        pedido2.setCliente(ana);
        pedido2.setListaProductos(List.of(libro, agenda));
        pedidoRepository.save(pedido2);

        // Initialize Reservations
        Reserva reserva1 = new Reserva();
        reserva1.setCliente(juan);
        reserva1.setProducto(cuaderno);
        reserva1.setFechaReserva(LocalDateTime.now().minusHours(6));
        reserva1.setEstado("PENDIENTE");
        reserva1.setCantidad(2);
        reservaRepository.save(reserva1);

        Reserva reserva2 = new Reserva();
        reserva2.setCliente(ana);
        reserva2.setProducto(libro);
        reserva2.setFechaReserva(LocalDateTime.now().minusHours(18));
        reserva2.setEstado("CONFIRMADA");
        reserva2.setCantidad(1);
        reservaRepository.save(reserva2);

        Reserva reserva3 = new Reserva();
        reserva3.setCliente(carlos);
        reserva3.setProducto(lapices);
        reserva3.setFechaReserva(LocalDateTime.now().plusHours(6));
        reserva3.setEstado("PENDIENTE");
        reserva3.setCantidad(3);
        reservaRepository.save(reserva3);

        // Initialize Inventory
        Inventario inv1 = new Inventario();
        inv1.setCantidad(50);
        inv1.setUmbral(10);
        inv1.setProducto(cuaderno);
        inv1.setSucursal(central);
        inventarioRepository.save(inv1);

        Inventario inv2 = new Inventario();
        inv2.setCantidad(20);
        inv2.setUmbral(5);
        inv2.setProducto(libro);
        inv2.setSucursal(central);
        inventarioRepository.save(inv2);

        Inventario inv3 = new Inventario();
        inv3.setCantidad(35);
        inv3.setUmbral(8);
        inv3.setProducto(lapices);
        inv3.setSucursal(norte);
        inventarioRepository.save(inv3);

        Inventario inv4 = new Inventario();
        inv4.setCantidad(15);
        inv4.setUmbral(3);
        inv4.setProducto(agenda);
        inv4.setSucursal(central);
        inventarioRepository.save(inv4);

        Inventario inv5 = new Inventario();
        inv5.setCantidad(25);
        inv5.setUmbral(5);
        inv5.setProducto(marcadores);
        inv5.setSucursal(norte);
        inventarioRepository.save(inv5);

        // Initialize Notifications
        Notificacion notif1 = new Notificacion();
        notif1.setMensaje("Su reserva ha sido confirmada");
        notif1.setFecha(LocalDateTime.now().minusHours(12));
        notif1.setCliente(ana);
        notificacionRepository.save(notif1);

        Notificacion notif2 = new Notificacion();
        notif2.setMensaje("Nuevo producto disponible");
        notif2.setFecha(LocalDateTime.now().minusHours(8));
        notif2.setCliente(juan);
        notificacionRepository.save(notif2);

        Notificacion notif3 = new Notificacion();
        notif3.setMensaje("Recordatorio: recoja su pedido");
        notif3.setFecha(LocalDateTime.now().minusHours(2));
        notif3.setCliente(juan);
        notificacionRepository.save(notif3);

        System.out.println("✅ Initial data loaded successfully!");
    }
}
