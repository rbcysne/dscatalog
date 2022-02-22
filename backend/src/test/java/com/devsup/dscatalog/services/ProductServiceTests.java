package com.devsup.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.entities.Category;
import com.devsup.dscatalog.entities.Product;
import com.devsup.dscatalog.exceptions.DataBaseException;
import com.devsup.dscatalog.exceptions.RegisterNotFoundException;
import com.devsup.dscatalog.repositories.CategoryRepository;
import com.devsup.dscatalog.repositories.ProductRepository;
import com.devsup.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private MessageSource messageSource;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private Product product;
	private Category category;
	private PageImpl<Product> page;
	private ProductDTO productDto;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		productDto = Factory.createProductDTO();
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
		
		Mockito.when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(productRepository.findByName(ArgumentMatchers.anyString(), (Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(productRepository.getById(existingId)).thenReturn(product);
		Mockito.when(productRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getById(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.mock(MessageSource.class).getMessage("product-deleting-error-id-not-found", null, null);
		Mockito.mock(MessageSource.class).getMessage("product-deleting-error", null, null);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			productService.delete(existingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowRegisterNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(RegisterNotFoundException.class, () -> {
			productService.delete(nonExistingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenDependentId() {
		Assertions.assertThrows(DataBaseException.class, () -> {
			productService.delete(dependentId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void findAllShouldReturnPage() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> page = productService.findAll(null, pageRequest);
		
		Assertions.assertNotNull(page);
		Mockito.verify(productRepository, Mockito.times(1)).findAll(pageRequest);
	}
	
	@Test
	public void findAllWithSearchArgumentShouldReturnPage() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		String search = "name";
		
		Page<ProductDTO> page = productService.findAll(search, pageRequest);
		
		Assertions.assertNotNull(page);
		Mockito.verify(productRepository, Mockito.times(1)).findByName(search, pageRequest);
	}
	
	@Test
	public void findByIdShouldReturnObjectDTOWhenIdExists() {
		
		ProductDTO result = productService.findById(existingId);
		
		Assertions.assertNotNull(result);
		Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
	}
	
	@Test
	public void findByIdShouldThrowRegisterNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(RegisterNotFoundException.class, () -> {
			productService.findById(nonExistingId);
		});
	}
	
	@Test
	public void updateShouldReturnObjectDTOWhenIdExists() {
		
		ProductDTO result = productService.update(existingId, productDto);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldThrowRegisterNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(RegisterNotFoundException.class, () -> {
			productService.update(nonExistingId, productDto);
		});
	}
}
