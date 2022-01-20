package com.devsup.dscatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="5") Integer size,
			@RequestParam(value="sort", defaultValue="name") String sort,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), sort);
		
		Page<CategoryDTO> categories = this.categoryService.findAll(search, pageRequest);
		
		
		return ResponseEntity.ok().body(categories);
	}
}
