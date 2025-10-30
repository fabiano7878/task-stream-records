package com.br.training.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.br.training.dto.ProductDTO;
import com.br.training.model.Product;
import com.br.training.repository.InMemoryProductRepository;

class ProductServiceTest {

    private ProductService productService;
    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        productService = new ProductService(repository);
    }

    @Test
    void testGetAllProducts() {
        productService.createProduct(new ProductDTO(1L, "Product1", "R$", 10, 99.99));
        
        List<Product> products = productService.getAllProducts();

        assertNotNull(products, "The product list should not be null");
        assertEquals(1, products.size(), "The product list size should be 1");
        assertEquals("Product1", products.get(0).name(), "The product name should match");
    }

    @Test
    void testGetProductById() {
    	ProductDTO product = new ProductDTO(1L, "Product1", "R$", 10, 99.99);
        productService.createProduct(product);

        Product result = productService.getProductById(1L);

        assertNotNull(result, "The product should not be null");
        assertEquals(1L, result.id(), "The product id should match");
    }

    @Test
    void testCreateProduct() {
        ProductDTO dto = new ProductDTO(1L, "Product1", "Description1", 10, 100.0);

        boolean result = productService.createProduct(dto);

        assertTrue(result, "The product should be created successfully");
        assertNotNull(productService.getProductById(1L), "The product should exist in the repository");
    }

    @Test
    void testDeleteProductById() {
    	  ProductDTO dto = new ProductDTO(1L, "Product1", "Description1", 10, 100.0);

          productService.createProduct(dto);
          boolean resultDelete = productService.deleteProductById(1L);

        assertTrue(resultDelete, "The product should be deleted successfully");
        assertNull(productService.getProductById(1L), "The product should no longer exist in the repository");
    }

    @Test
    void testGetProductsWithPriceHigherThanValue() {
    	
    	ProductDTO dto1 = new ProductDTO(1L, "Product1", "Description1", 10, 100.0);
    	ProductDTO dto2 = new ProductDTO(2L, "Product2", "Description2", 5, 40.0);
		List<ProductDTO> products = List.of(dto1, dto2);
		
		products.forEach(productService::createProduct);      
    	
        ProductDTO dto = new ProductDTO(null, "", "", 0, 50.0);
        List<Product> allProducts = productService.getProductsWithPriceHigthThenPriceValue(dto);

        assertNotNull(allProducts, "The product list should not be null");
        assertEquals(1, allProducts.size(), "The product list size should be 1");
        assertEquals("Product1", allProducts.get(0).name(), "The product name should match");
    }

    @Test
    void testGetProductsWithPriceLowerThanValue() {
        repository.save(new Product(1L, "Product1", "Coffe", 10, 100.0));
        repository.save(new Product(2L, "Product2", "Milk", 5, 40.0));

        ProductDTO dto = new ProductDTO(null, "product3", "Milk", 1, 40.0);        
        List<Product> products = productService.getProductsWithPriceMinorThenPriceValue(dto);

        assertNotNull(products, "The product list should not be null");
        assertEquals(1, products.size(), "The product list size should be 1");
        assertEquals("Product2", products.get(0).name(), "The product name should match");
    }
}