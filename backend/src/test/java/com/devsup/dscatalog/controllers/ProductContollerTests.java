package com.devsup.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.exceptions.DataBaseException;
import com.devsup.dscatalog.exceptions.RegisterNotFoundException;
import com.devsup.dscatalog.services.ProductService;
import com.devsup.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductContollerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	@Autowired
	private ObjectMapper mapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private ProductDTO productDto;
	private PageImpl<ProductDTO> page;
	
	@BeforeEach
	void setup() throws Exception {
		
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		productDto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDto));
		
		when(productService.findAll(any(), any())).thenReturn(page);
		
		when(productService.findById(existingId)).thenReturn(productDto);
		when(productService.findById(nonExistingId)).thenThrow(RegisterNotFoundException.class);
		
		when(productService.update(eq(existingId), any())).thenReturn(productDto);
		when(productService.update(eq(nonExistingId), any())).thenThrow(RegisterNotFoundException.class);
		
		doNothing().when(productService).delete(existingId);
		doThrow(RegisterNotFoundException.class).when(productService).delete(nonExistingId);
		doThrow(DataBaseException.class).when(productService).delete(dependentId);
		
		when(productService.insert(any())).thenReturn(productDto);
		
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		
		mockMvc.perform(get("/products")).andExpect(status().isOk());
		
/*or this
 * 		ResultActions result = mockMvc.perform(get("/products")
									.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());*/
	}
	
	@Test
	public void findByIdShouldReturnObjectWhenIdExists() throws Exception {
		
		mockMvc.perform(get("/products/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		mockMvc.perform(get("/products/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateShouldReturnObjectDTOWhenIdExists() throws Exception {
		
		String json = mapper.writeValueAsString(productDto);
		
		mockMvc.perform(put("/products/{id}", existingId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists());
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String json = mapper.writeValueAsString(productDto);
		
		mockMvc.perform(put("/products/{id}", nonExistingId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnObjectDTO() throws Exception {
		
		String json = mapper.writeValueAsString(productDto);
		
		mockMvc.perform(post("/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists());
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		
		mockMvc.perform(delete("/products/{id}", existingId)
				 .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		mockMvc.perform(delete("/products/{id}", nonExistingId)
				 .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isNotFound());
	}
}
