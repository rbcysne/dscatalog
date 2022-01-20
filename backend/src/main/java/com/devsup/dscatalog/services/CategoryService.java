package com.devsup.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsup.dscatalog.dto.CategoryDTO;
import com.devsup.dscatalog.entities.Category;
import com.devsup.dscatalog.repositories.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	
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

}
