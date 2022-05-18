package com.devsup.dscatalog.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	ProductService productService;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findProductsByNameAndCategory(
			@RequestParam(value = "categoryId", defaultValue = "0", required = false) Long categoryId,
			@RequestParam(value = "search", defaultValue = "", required = false) String search,
			Pageable pageable) {
		
		Page<ProductDTO> products = this.productService.findProductsByNameAndCategory(search.trim(), categoryId, pageable);
		
		return ResponseEntity.ok().body(products);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
		
		ProductDTO productDto = this.productService.findById(id);
		
		return ResponseEntity.ok().body(productDto);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO productDto, 
				UriComponentsBuilder uriBuilder) {
		
		productDto = this.productService.insert(productDto);
		
		URI uri = uriBuilder
					.path("/categories/{id}")
					.buildAndExpand(productDto.getId())
					.toUri();
		
//another way to do the same thing
//		URI uri = ServletUriComponentsBuilder
//					.fromCurrentRequest()
//					.path("/{id}")
//					.buildAndExpand(productDto.getId())
//					.toUri();
		
		return ResponseEntity.created(uri).body(productDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id, 
				@RequestBody ProductDTO productDto) {
		
		productDto = this.productService.update(id, productDto);
		
		return ResponseEntity.ok().body(productDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		
		this.productService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
