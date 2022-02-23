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

import com.devsup.dscatalog.dto.UserDTO;
import com.devsup.dscatalog.dto.UserInsertDTO;
import com.devsup.dscatalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(
			@RequestParam(value="search", defaultValue="", required = false) String search,
			Pageable pageable) {
		
		Page<UserDTO> users = this.userService.findAll(search, pageable);
		
		return ResponseEntity.ok().body(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
		
		UserDTO userDto = this.userService.findById(id);
		
		return ResponseEntity.ok().body(userDto);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO userInsertDto, 
				UriComponentsBuilder uriBuilder) {
		
		UserDTO newUserDto = this.userService.insert(userInsertDto);
		
		URI uri = uriBuilder
					.path("/categories/{id}")
					.buildAndExpand(newUserDto.getId())
					.toUri();
		
//another way to do the same thing
//		URI uri = ServletUriComponentsBuilder
//					.fromCurrentRequest()
//					.path("/{id}")
//					.buildAndExpand(userDto.getId())
//					.toUri();
		
		return ResponseEntity.created(uri).body(newUserDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, 
				@RequestBody UserDTO userDto) {
		
		userDto = this.userService.update(id, userDto);
		
		return ResponseEntity.ok().body(userDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		
		this.userService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
