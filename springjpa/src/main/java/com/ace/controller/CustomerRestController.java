package com.ace.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.ace.ds.Customer;
import com.ace.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Customer findById(@PathVariable long id) {
		return customerService.findById(id);
	}

	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Customer> courseList() {
		System.out.println("MAYSU::::::::");
		return customerService.listAll();
	}

	@RequestMapping(value = "/customers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> createCourse(@RequestBody @Valid Customer customer, BindingResult result) {
		if (!result.hasErrors()) {
			Customer savedCustomer = customerService.save(customer);
			return ResponseEntity.created(URI.create("/customers/" + savedCustomer.getId())).body(savedCustomer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Request contains incorect data = [%s]", getErrors(result)));
		}
	}

	// consume means accept data
	// @PutMapping("/customers/{id}")
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> update(@RequestBody @Valid Customer customer, @PathVariable long id,
			BindingResult result) {
		if (!result.hasErrors()) {

			if (customerService.existsById(id)) {
				customer.setId(id);
				Customer savedCustomer = customerService.save(customer);
				return ResponseEntity.ok(savedCustomer);
			} else {
				return ResponseEntity.notFound().build();
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Request contains incorect data = [%s]", getErrors(result)));
		}
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (customerService.existsById(id)) {
			customerService.delete(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	private String getErrors(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
	}

}
