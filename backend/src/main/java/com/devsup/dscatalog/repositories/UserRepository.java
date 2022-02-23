package com.devsup.dscatalog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsup.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Page<User> findByFirstName(@Param("firstName") String firstName, Pageable pageable);

}
