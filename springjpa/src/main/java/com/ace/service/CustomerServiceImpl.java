package com.ace.service;

import java.util.List;

import com.ace.ds.Customer;
import com.ace.repo.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired 
	private CustomerRepository repo;
	
	@Override
	public Customer save(Customer customer) {
		return repo.save(customer);
	}
	@Override
	public List<Customer> listAll() {
		return repo.findAll();
	}
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}
	@Override
	public List<Customer> search(String keyword) {
		return repo.search(keyword);
	}
	@Override
	public Customer findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	@Override
	public Boolean existsById(Long id) {
		return repo.existsById(id);
	}
}
