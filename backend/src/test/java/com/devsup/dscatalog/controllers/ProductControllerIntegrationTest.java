package com.devsup.dscatalog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.devsup.dscatalog.dto.ProductDTO;
import com.devsup.dscatalog.tests.Factory;
import com.devsup.dscatalog.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIntegrationTest {

		@Autowired
		private MockMvc mockMvc;
		
		@Autowired
		private ObjectMapper mapper;
		
		@Autowired
		private TokenUtil tokenUtil;
		
		private String username;
		private String password;
		private Long existingId;
		private Long nonExistingId;
		private Long countTotalProducts;

		@BeforeEach
		void setup() throws Exception {
			
			username = "alex@gmail.com";
			password = "123456";
			
			existingId = 1L;
			nonExistingId = 1000L;
			countTotalProducts = 25L;
		}
		
		@Test
		public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
			
			mockMvc.perform(get("/products?page=0&size=3&sort=name,asc")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.totalElements").value(countTotalProducts))
						.andExpect(jsonPath("$.content").exists())
						.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
						.andExpect(jsonPath("$.content[1].name").value("PC Gamer"))
						.andExpect(jsonPath("$.content[2].name").value("PC Gamer Alfa"));
		}
		
		@Test
		public void updateShouldReturnObjectDTOWhenIdExists() throws Exception {
			
			String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
			
			ProductDTO prodDto = Factory.createProductDTO();
			
			String json = mapper.writeValueAsString(prodDto);
			
			String name = prodDto.getName();
			
			mockMvc.perform(put("/products/{id}", existingId)
					.header("Authorization", "Bearer " + accessToken)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name").value(name));
		}
		
		@Test
		public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
			
			String accessToken = tokenUtil.obtainAccessToken(mockMvc, username, password);
			
			ProductDTO prodDto = Factory.createProductDTO();
			
			String json = mapper.writeValueAsString(prodDto);
			
			mockMvc.perform(put("/products/{id}", nonExistingId)
					.header("Authorization", "Bearer " + accessToken)
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		}
		
}
