package com.devsup.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsup.dscatalog.dto.RoleDTO;
import com.devsup.dscatalog.dto.UserDTO;
import com.devsup.dscatalog.dto.UserInsertDTO;
import com.devsup.dscatalog.entities.Role;
import com.devsup.dscatalog.entities.User;
import com.devsup.dscatalog.exceptions.DataBaseException;
import com.devsup.dscatalog.exceptions.RegisterNotFoundException;
import com.devsup.dscatalog.repositories.RoleRepository;
import com.devsup.dscatalog.repositories.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(String search, Pageable pageable) {
		
		Page<User> page;
		
		try {
			if(search != null && !search.isBlank()) {
				page = this.userRepository.findByFirstName(search, pageable);
			} else {
				page = this.userRepository.findAll(pageable);
			}
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return page.map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		
		User user = new User();
		
		Optional<User> obj = this.userRepository.findById(id);
		
		user = obj.orElseThrow(() -> new RegisterNotFoundException(this.messageSource.getMessage("user-not-found-with-id", null, null) + " " + id));
		
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO insert(UserInsertDTO userInsertDto) {
		
		User user = new User();
		
		try {
			this.converter(userInsertDto, user);
			user.setPassword(passwordEncoder.encode(userInsertDto.getPassword()));
			user = this.userRepository.save(user);
		} catch(Exception e) {
			throw new DataBaseException(e.getMessage());
		}
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO userDto) {
		
		User user = new User();
		
		try {
			user = this.userRepository.getById(id);
			this.converter(userDto, user);
			
			user = this.userRepository.save(user);
		} catch(EntityNotFoundException e) {
			throw new RegisterNotFoundException(this.messageSource.getMessage("user-updating-register-not-found", null, null) + " " + id);
		}
		return new UserDTO(user);
	}
	
	public void delete(Long id) {
		try {
			this.userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new RegisterNotFoundException(this.messageSource.getMessage("user-deleting-error-id-not-found", null, null) + " " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DataBaseException(this.messageSource.getMessage("user-deleting-error", null, null) + " " + id);
		}
	}
	
	/*
	 * Method to convert a UserDTO into a User entity
	 */
	private void converter(UserDTO userDto, User user) {
		user.setFirstName(userDto.getFirstName().trim());
		user.setLastName(userDto.getLastName().trim());
		user.setEmail(userDto.getEmail());
		
		user.getRoles().clear();
		for(RoleDTO roleDto : userDto.getRoles()) {
			Role role = this.roleRepository.getById(roleDto.getId());
			user.getRoles().add(role);
		}
	}
	

	
	
}


