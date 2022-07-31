package com.ace.service;

import java.util.List;

import com.ace.ds.Customer;


public interface CustomerService {
	Customer save(Customer customer);
	List<Customer> listAll();
	void delete(Long id);
	List<Customer> search(String keyword);
	Customer findById(Long id);
	Boolean existsById(Long id);
}
