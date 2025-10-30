package com.br.training.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductDTOTest {

    @Test
    void testProductDTOGettersAndSetters() {
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Product1");
        dto.setDescription("Description1");
        dto.setQuantity(10);
        dto.setPrice(100.0);

        assertEquals(1L, dto.getId());
        assertEquals("Product1", dto.getName());
        assertEquals("Description1", dto.getDescription());
        assertEquals(10, dto.getQuantity());
        assertEquals(100.0, dto.getPrice());
    }

    @Test
    void testProductDTOConstructor() {
        ProductDTO dto = new ProductDTO(1L, "Product1", "Description1", 10, 100.0);

        assertEquals(1L, dto.getId());
        assertEquals("Product1", dto.getName());
        assertEquals("Description1", dto.getDescription());
        assertEquals(10, dto.getQuantity());
        assertEquals(100.0, dto.getPrice());
    }
}