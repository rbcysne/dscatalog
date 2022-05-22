package com.devsup.dscatalog.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.exceptions.RegisterNotFoundException;
import com.devsup.dscatalog.repositories.ProductRepository;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
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
	public void findAllShouldReturnPageAccordingToPageAndSize() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> page = productService.findProductsByNameAndCategory("", 0L, pageRequest);
		
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals(0, page.getNumber());
		Assertions.assertEquals(10, page.getSize());
		Assertions.assertEquals(countTotalProducts, page.getTotalElements());
	}
	
	@Test
	public void findAllShouldReturnEmptyPageWhenPageDoesNotExist() {
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<ProductDTO> page = productService.findProductsByNameAndCategory("", 0L, pageRequest);
		
		Assertions.assertTrue(page.isEmpty());
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> page = productService.findProductsByNameAndCategory("", 0L, pageRequest);
		
		Assertions.assertFalse(page.isEmpty());
		Assertions.assertEquals("Macbook Pro", page.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", page.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", page.getContent().get(2).getName());
	}
	
	@Test
	public void deleteShouldDeleteRegisterWhenIdExists() {
		productService.delete(existingId);
		
		Assertions.assertEquals(countTotalProducts -1, productRepository.count());
	}
	
	@Test
	public void deleteShouldThrowRegisterNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(RegisterNotFoundException.class, () -> {
			productService.delete(nonExistingId);
		});
	}
}
