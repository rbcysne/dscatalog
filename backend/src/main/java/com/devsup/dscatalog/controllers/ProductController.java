package com.devsup.dscatalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value="search", defaultValue="", required = false) String search,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="sort", defaultValue="name") String sort
			) {
		
		PageRequest pageRequest = PageRequest.of(page, pageSize, Direction.valueOf(direction), sort);
		
		Page<ProductDTO> categories = this.productService.findAll(search, pageRequest);
		
		
		return ResponseEntity.ok().body(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
		
		ProductDTO productDto = this.productService.findById(id);
		
		return ResponseEntity.ok().body(productDto);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDto, 
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
