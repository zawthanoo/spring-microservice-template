package com.mutu.spring.rest.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

@RestController
public class ApiController {
	
	private Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@RequestMapping(value = "/health-check", method = RequestMethod.GET)
	public Result hello() {
		return new Result("OK", "API-1 is started....");
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public Employee get(@PathVariable("id") String id) {
		return new Employee(id, "Mutu", "Middleware", "mutu@gmail.com");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Result getBillerFieldDetailList(@Valid @RequestBody Employee reqDto) {
		return new Result("OK", reqDto.getName() + " is updated.");
	}
}
