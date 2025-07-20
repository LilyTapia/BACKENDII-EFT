package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SucursalTest {

    private Sucursal sucursal;

    @BeforeEach
    void setUp() {
        sucursal = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Calle Principal 123")
                .region("Metropolitana")
                .build();
    }

    @Test
    void testGettersAndSetters() {
        // Test getters
        assertEquals(1L, sucursal.getId());
        assertEquals("Sucursal Centro", sucursal.getNombre());
        assertEquals("Calle Principal 123", sucursal.getDireccion());
        assertEquals("Metropolitana", sucursal.getRegion());

        // Test setters
        sucursal.setId(2L);
        sucursal.setNombre("Sucursal Norte");
        sucursal.setDireccion("Avenida Norte 456");
        sucursal.setRegion("Valparaiso");

        assertEquals(2L, sucursal.getId());
        assertEquals("Sucursal Norte", sucursal.getNombre());
        assertEquals("Avenida Norte 456", sucursal.getDireccion());
        assertEquals("Valparaiso", sucursal.getRegion());
    }

    @Test
    void testEquals() {
        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Calle Principal 123")
                .region("Metropolitana")
                .build();

        Sucursal sucursal2 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Calle Principal 123")
                .region("Metropolitana")
                .build();

        Sucursal sucursal3 = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal Sur")
                .direccion("Calle Sur 789")
                .region("Biobio")
                .build();

        assertEquals(sucursal1, sucursal2);
        assertNotEquals(sucursal1, sucursal3);
        assertNotEquals(sucursal1, null);
        assertNotEquals(sucursal1, "string");
        assertEquals(sucursal1, sucursal1);
    }

    @Test
    void testHashCode() {
        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Calle Principal 123")
                .region("Metropolitana")
                .build();

        Sucursal sucursal2 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Centro")
                .direccion("Calle Principal 123")
                .region("Metropolitana")
                .build();

        assertEquals(sucursal1.hashCode(), sucursal2.hashCode());
    }

    @Test
    void testToString() {
        String toString = sucursal.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Sucursal"));
        assertTrue(toString.contains("Sucursal Centro"));
        assertTrue(toString.contains("Calle Principal 123"));
        assertTrue(toString.contains("Metropolitana"));
    }

    @Test
    void testBuilder() {
        Sucursal sucursalBuilder = Sucursal.builder()
                .id(3L)
                .nombre("Sucursal Este")
                .direccion("Calle Este 321")
                .region("Antofagasta")
                .build();

        assertEquals(3L, sucursalBuilder.getId());
        assertEquals("Sucursal Este", sucursalBuilder.getNombre());
        assertEquals("Calle Este 321", sucursalBuilder.getDireccion());
        assertEquals("Antofagasta", sucursalBuilder.getRegion());
    }

    @Test
    void testNoArgsConstructor() {
        Sucursal sucursalVacia = new Sucursal();
        assertNotNull(sucursalVacia);
        assertNull(sucursalVacia.getId());
        assertNull(sucursalVacia.getNombre());
        assertNull(sucursalVacia.getDireccion());
        assertNull(sucursalVacia.getRegion());
    }

    @Test
    void testAllArgsConstructor() {
        Sucursal sucursalCompleta = new Sucursal(
                4L,
                "Sucursal Oeste",
                "Avenida Oeste 654",
                "Atacama"
        );

        assertEquals(4L, sucursalCompleta.getId());
        assertEquals("Sucursal Oeste", sucursalCompleta.getNombre());
        assertEquals("Avenida Oeste 654", sucursalCompleta.getDireccion());
        assertEquals("Atacama", sucursalCompleta.getRegion());
    }

    @Test
    void testBuilderMethods() {
        Sucursal.SucursalBuilder builder = Sucursal.builder();
        
        Sucursal sucursalFromBuilder = builder
                .id(5L)
                .nombre("Sucursal Mall")
                .direccion("Mall Plaza 987")
                .region("Coquimbo")
                .build();

        assertEquals(5L, sucursalFromBuilder.getId());
        assertEquals("Sucursal Mall", sucursalFromBuilder.getNombre());
        assertEquals("Mall Plaza 987", sucursalFromBuilder.getDireccion());
        assertEquals("Coquimbo", sucursalFromBuilder.getRegion());
    }

    @Test
    void testBuilderToString() {
        Sucursal.SucursalBuilder builder = Sucursal.builder()
                .id(1L)
                .nombre("Test");
        
        String builderToString = builder.toString();
        assertNotNull(builderToString);
        assertTrue(builderToString.contains("SucursalBuilder"));
    }

    @Test
    void testCanEqual() {
        Sucursal sucursal1 = new Sucursal();
        Sucursal sucursal2 = new Sucursal();
        
        assertTrue(sucursal1.canEqual(sucursal2));
        assertFalse(sucursal1.canEqual("string"));
        assertFalse(sucursal1.canEqual(null));
    }

    @Test
    void testNullValues() {
        sucursal.setNombre(null);
        sucursal.setDireccion(null);
        sucursal.setRegion(null);

        assertNull(sucursal.getNombre());
        assertNull(sucursal.getDireccion());
        assertNull(sucursal.getRegion());
    }

    @Test
    void testEmptyValues() {
        sucursal.setNombre("");
        sucursal.setDireccion("");
        sucursal.setRegion("");

        assertEquals("", sucursal.getNombre());
        assertEquals("", sucursal.getDireccion());
        assertEquals("", sucursal.getRegion());
    }

    @Test
    void testLongValues() {
        String longNombre = "Sucursal con un nombre muy largo que podria contener mucha informacion";
        String longDireccion = "Calle con una direccion muy larga numero 12345 departamento 678 comuna de ejemplo region metropolitana";
        String longRegion = "Region con nombre muy largo";

        sucursal.setNombre(longNombre);
        sucursal.setDireccion(longDireccion);
        sucursal.setRegion(longRegion);

        assertEquals(longNombre, sucursal.getNombre());
        assertEquals(longDireccion, sucursal.getDireccion());
        assertEquals(longRegion, sucursal.getRegion());
    }

    @Test
    void testSpecialCharacters() {
        sucursal.setNombre("Sucursal Ñuñoa");
        sucursal.setDireccion("Calle José María 123");
        sucursal.setRegion("Región Metropolitana");

        assertEquals("Sucursal Ñuñoa", sucursal.getNombre());
        assertEquals("Calle José María 123", sucursal.getDireccion());
        assertEquals("Región Metropolitana", sucursal.getRegion());
    }

    @Test
    void testNumericValues() {
        sucursal.setNombre("Sucursal 001");
        sucursal.setDireccion("Calle 123 #456");
        sucursal.setRegion("Region 15");

        assertEquals("Sucursal 001", sucursal.getNombre());
        assertEquals("Calle 123 #456", sucursal.getDireccion());
        assertEquals("Region 15", sucursal.getRegion());
    }

    @Test
    void testRegionValues() {
        String[] regiones = {
            "Arica y Parinacota", "Tarapacá", "Antofagasta", "Atacama",
            "Coquimbo", "Valparaíso", "Metropolitana", "O'Higgins",
            "Maule", "Ñuble", "Biobío", "Araucanía", "Los Ríos",
            "Los Lagos", "Aysén", "Magallanes"
        };

        for (String region : regiones) {
            sucursal.setRegion(region);
            assertEquals(region, sucursal.getRegion());
        }
    }

    @Test
    void testEqualsWithNullFields() {
        Sucursal sucursal1 = new Sucursal();
        Sucursal sucursal2 = new Sucursal();

        // Both null
        assertEquals(sucursal1, sucursal2);

        // One has id, other doesn't
        sucursal1.setId(1L);
        assertNotEquals(sucursal1, sucursal2);
        assertNotEquals(sucursal2, sucursal1);

        // Both have same id
        sucursal2.setId(1L);
        assertEquals(sucursal1, sucursal2);

        // Different nombre
        sucursal1.setNombre("Test nombre");
        assertNotEquals(sucursal1, sucursal2);

        // Same nombre
        sucursal2.setNombre("Test nombre");
        assertEquals(sucursal1, sucursal2);

        // Different direccion
        sucursal1.setDireccion("Test direccion");
        assertNotEquals(sucursal1, sucursal2);

        // Same direccion
        sucursal2.setDireccion("Test direccion");
        assertEquals(sucursal1, sucursal2);

        // Different region
        sucursal1.setRegion("Test region");
        assertNotEquals(sucursal1, sucursal2);

        // Same region
        sucursal2.setRegion("Test region");
        assertEquals(sucursal1, sucursal2);
    }

    @Test
    void testHashCodeWithNullFields() {
        Sucursal sucursal1 = new Sucursal();
        Sucursal sucursal2 = new Sucursal();

        // Both null - should have same hash
        assertEquals(sucursal1.hashCode(), sucursal2.hashCode());

        // Add fields one by one
        sucursal1.setId(1L);
        sucursal2.setId(1L);
        assertEquals(sucursal1.hashCode(), sucursal2.hashCode());

        sucursal1.setNombre("Test nombre");
        sucursal2.setNombre("Test nombre");
        assertEquals(sucursal1.hashCode(), sucursal2.hashCode());

        sucursal1.setDireccion("Test direccion");
        sucursal2.setDireccion("Test direccion");
        assertEquals(sucursal1.hashCode(), sucursal2.hashCode());

        sucursal1.setRegion("Test region");
        sucursal2.setRegion("Test region");
        assertEquals(sucursal1.hashCode(), sucursal2.hashCode());
    }

    @Test
    void testEqualsEdgeCases() {
        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Test nombre")
                .direccion("Test direccion")
                .region("Test region")
                .build();

        // Test with different types
        assertNotEquals(sucursal1, new Object());
        assertNotEquals(sucursal1, 123);
        assertNotEquals(sucursal1, "string");

        // Test with null
        assertNotEquals(sucursal1, null);

        // Test reflexivity
        assertEquals(sucursal1, sucursal1);

        // Test with different field values
        Sucursal sucursalDifferentId = Sucursal.builder()
                .id(2L)
                .nombre("Test nombre")
                .direccion("Test direccion")
                .region("Test region")
                .build();
        assertNotEquals(sucursal1, sucursalDifferentId);

        Sucursal sucursalDifferentNombre = Sucursal.builder()
                .id(1L)
                .nombre("Otro nombre")
                .direccion("Test direccion")
                .region("Test region")
                .build();
        assertNotEquals(sucursal1, sucursalDifferentNombre);

        Sucursal sucursalDifferentDireccion = Sucursal.builder()
                .id(1L)
                .nombre("Test nombre")
                .direccion("Otra direccion")
                .region("Test region")
                .build();
        assertNotEquals(sucursal1, sucursalDifferentDireccion);

        Sucursal sucursalDifferentRegion = Sucursal.builder()
                .id(1L)
                .nombre("Test nombre")
                .direccion("Test direccion")
                .region("Otra region")
                .build();
        assertNotEquals(sucursal1, sucursalDifferentRegion);
    }
}
