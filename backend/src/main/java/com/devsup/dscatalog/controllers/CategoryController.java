package com.devsup.dscatalog.controllers;

import java.net.URI;

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

import com.devsup.dscatalog.dto.CategoryDTO;
import com.devsup.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(
			@RequestParam(value="search", defaultValue="", required = false) String search,
			Pageable pageable
			) {

		Page<CategoryDTO> categories = this.categoryService.findAll(search, pageable);
		
		return ResponseEntity.ok().body(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long id) {
		
		CategoryDTO categoryDto = this.categoryService.findById(id);
		
		return ResponseEntity.ok().body(categoryDto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDto, 
				UriComponentsBuilder uriBuilder) {
		
		categoryDto = this.categoryService.insert(categoryDto);
		
		URI uri = uriBuilder
					.path("/categories/{id}")
					.buildAndExpand(categoryDto.getId())
					.toUri();
		
//another way to do the same thing
//		URI uri = ServletUriComponentsBuilder
//					.fromCurrentRequest()
//					.path("/{id}")
//					.buildAndExpand(categoryDto.getId())
//					.toUri();
		
		return ResponseEntity.created(uri).body(categoryDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable("id") Long id, 
				@RequestBody CategoryDTO categoryDto) {
		
		categoryDto = this.categoryService.update(id, categoryDto);
		
		return ResponseEntity.ok().body(categoryDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		
		this.categoryService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
