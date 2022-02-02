package com.devsup.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsup.dscatalog.entities.Product;
import com.devsup.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	ProductRepository productRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		
		product.setId(null);
		product = productRepository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		productRepository.deleteById(existingId);
		
		Optional<Product> obj = productRepository.findById(existingId);
		
		Assertions.assertFalse(obj.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyRestultExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() {
		
		Optional<Product> obj = productRepository.findById(existingId);
		
		Assertions.assertTrue(obj.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyObjectWhenIdDoesNotExist() {
		
		Optional<Product> obj = productRepository.findById(nonExistingId);
		
//		Assertions.assertFalse(obj.isPresent());
		Assertions.assertTrue(obj.isEmpty());
	}
	
	
}
