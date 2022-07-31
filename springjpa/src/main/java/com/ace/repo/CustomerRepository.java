package com.ace.repo;

import java.util.List;

import com.ace.ds.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "SELECT c FROM Customer c WHERE c.name LIKE '%' || :keyword || '%'"
			+ " OR c.email LIKE '%' || :keyword || '%'"
			+ " OR c.address LIKE '%' || :keyword || '%'")
	public List<Customer> search(@Param("keyword") String keyword);
}
