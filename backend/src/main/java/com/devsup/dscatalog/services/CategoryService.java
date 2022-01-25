package com.devsup.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsup.dscatalog.dto.CategoryDTO;
import com.devsup.dscatalog.entities.Category;
import com.devsup.dscatalog.exceptions.DataBaseException;
import com.devsup.dscatalog.exceptions.RegisterNotFoundException;
import com.devsup.dscatalog.repositories.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(String search, PageRequest pageRequest) {
		
		Page<Category> page;
		
		try {
			if(!"".equals(search)) {
				page = this.categoryRepository.findByName(search, pageRequest);
			} else {
				page = this.categoryRepository.findAll(pageRequest);
			}
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return page.map(CategoryDTO::new);
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		
		Category category = new Category();
		
		Optional<Category> obj = this.categoryRepository.findById(id);
		
		category = obj.orElseThrow(() -> new RegisterNotFoundException(this.messageSource.getMessage("category-not-found-with-id", null, null) + " " + id));
		
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO categoryDto) {
		
		Category category = new Category();
		
		try {
			this.converter(categoryDto, category);
			category = this.categoryRepository.save(category);
		} catch(Exception e) {
			throw new DataBaseException(e.getMessage());
		}
		return new CategoryDTO(category);
	}

	private void converter(CategoryDTO categoryDto, Category category) {
		category.setName(categoryDto.getName().trim());
	}
	
	
}


