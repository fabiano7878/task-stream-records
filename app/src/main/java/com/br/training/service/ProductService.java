package com.br.training.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.br.training.dto.ProductDTO;
import com.br.training.model.Product;
import com.br.training.repository.InMemoryProductRepository;

public class ProductService {	
	
	private final InMemoryProductRepository repository;
	
	public ProductService(InMemoryProductRepository repository) {
		this.repository = new InMemoryProductRepository();
	}
	
	public List<Product>getAllProducts(){
		Map<Long, Product> products = Optional.ofNullable(repository.findAll()).orElse(Map.of());		
		return products.values().stream().toList();
	}
	
	public Product getProductById(Long id) {
		Product product = Optional.ofNullable(id)
				.map(p -> repository.findById(p))
				.orElse(null);
		return product;
	}	
	
	public boolean createProduct(ProductDTO product) {			
		return Optional.ofNullable(product)
				.filter(this::validateProductDTO)
				.map(this::trySave)
				.orElse(false);
	}	
	
	private boolean trySave(ProductDTO dto) {
		try {
			Product product = new Product(
					dto.getId(),
					dto.getName(),
					dto.getDescription(),
					dto.getQuantity(),
					dto.getPrice());
			repository.save(product);
			return true;
		} catch (Exception e) {
			System.err.println("Error saving product: " + e.getMessage());
			return false;
		}
	}
	
	public boolean deleteProductById(Long id) {
		return Optional.ofNullable(id)
				.map(p -> {
					repository.deleteById(p);
					return true;
				})
				.orElse(false);
	}
	
	public List<Product> getProductsWithPriceHigthThenPriceValue(ProductDTO dto){		
			if(Objects.nonNull(dto)) {
				Map<Long, Product> products = Optional.ofNullable(repository.findAll()).orElse(Map.of());
			
				try {				
				return (List<Product>) products.values().stream()
						.filter(product -> product.price() > dto.getPrice() && product.quantity() > 0)						
						.collect(Collectors.groupingBy(Product::price))
						.values().stream()
						.flatMap(List::stream) // Achata a as 2 listas e cria uma nova lista com tudo junto 
						.toList();
				} catch (Exception e) {
					System.err.println("Error filtering products: " + e.getMessage());
				}
						
			}
			return List.of();		
	}
	
	public List<Product> getProductsWithPriceMinorThenPriceValue(ProductDTO dto){		
		if(Objects.nonNull(dto) && validateProductDTO(dto)) {
			Map<Long, Product> products = Optional.ofNullable(repository.findAll()).orElse(Map.of());
			
			return products.values().stream()
					.filter(product -> product.price() < dto.getPrice() && product.quantity() > 0)
					.toList();
		}
		return List.of();		
}
	
	private boolean validateProductDTO(ProductDTO dto) {
		return !Objects.isNull(dto.getName()) && !dto.getName().trim().isEmpty() && dto.getQuantity() > 0;
	}

}
