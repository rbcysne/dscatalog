package com.devsup.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsup.dscatalog.entities.Category;
import com.devsup.dscatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

//	Page<Product> findByName(@Param("name") String name, Pageable pageable);
//	
//	@Query("SELECT p FROM Product p "
//			+ "INNER JOIN p.categories cts "
//			+ "WHERE (:category IS NULL OR :category IN cts")
//	Page<Product> findByCategory(Category category, Pageable pageable);
	
	@Query("SELECT DISTINCT p FROM Product p "
			+ "INNER JOIN p.categories cts "
			+ "WHERE (:name = '' OR UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%')) "
			+ "AND (COALESCE(:categories) IS NULL OR cts IN :categories)")
	Page<Product> findProductsByNameAndCategory(String name, List<Category> categories, Pageable pageable);
	
	@Query("SELECT p FROM Product p JOIN FETCH p.categories WHERE p IN :products")
	List<Product> findProductsAndCategories(List<Product> products);

}
