package com.devsup.dscatalog.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsup.dscatalog.dto.CategoryDTO;
import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.entities.Category;
import com.devsup.dscatalog.entities.Product;
import com.devsup.dscatalog.exceptions.DataBaseException;
import com.devsup.dscatalog.exceptions.RegisterNotFoundException;
import com.devsup.dscatalog.repositories.CategoryRepository;
import com.devsup.dscatalog.repositories.ProductRepository;


@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findProductsByNameAndCategory(String search, Long categoryId, Pageable pageable) {
		
		Page<Product> page;
		
		List<Category> categories = (categoryId == 0) ? null : Arrays.asList(categoryRepository.getById(categoryId));
		
		try {
			page = this.productRepository.findProductsByNameAndCategory(search, categories, pageable);
			this.productRepository.findProductsAndCategories(page.getContent());
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return page.map(x -> new ProductDTO(x, x.getCategories())); //ProductDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		
		Product product = new Product();
		
		Optional<Product> obj = this.productRepository.findById(id);
		
		product = obj.orElseThrow(() -> new RegisterNotFoundException(this.messageSource.getMessage("product-not-found-with-id", null, null) + " " + id));
		
		return new ProductDTO(product, product.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO productDto) {
		
		Product product = new Product();
		
		try {
			this.converter(productDto, product);
			product = this.productRepository.save(product);
		} catch(Exception e) {
			throw new DataBaseException(e.getMessage());
		}
		return new ProductDTO(product);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO productDto) {
		
		Product product = new Product();
		
		try {
			product = this.productRepository.getById(id);
			this.converter(productDto, product);
			
			product = this.productRepository.save(product);
		} catch(EntityNotFoundException e) {
			throw new RegisterNotFoundException(this.messageSource.getMessage("product-updating-register-not-found", null, null) + " " + id);
		}
		return new ProductDTO(product);
	}
	
	public void delete(Long id) {
		try {
			this.productRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new RegisterNotFoundException(this.messageSource.getMessage("product-deleting-error-id-not-found", null, null) + " " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DataBaseException(this.messageSource.getMessage("product-deleting-error", null, null) + " " + id);
		}
	}
	
	/*
	 * Method to convert a ProductDTO into a Product entity
	 */
	private void converter(ProductDTO productDto, Product product) {
		product.setName(productDto.getName().trim());
		product.setDescription(productDto.getDescription().trim());
		product.setPrice(productDto.getPrice());
		product.setImgUrl(productDto.getImgUrl().trim());
		product.setDate(productDto.getDate());
		
		product.getCategories().clear();
		for(CategoryDTO catDto : productDto.getCategories()) {
			Category category = this.categoryRepository.getById(catDto.getId());
			product.getCategories().add(category);
		}
	}
	

	
	
}


