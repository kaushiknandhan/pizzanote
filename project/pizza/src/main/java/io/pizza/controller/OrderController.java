package io.pizza.controller;
/**
 * REST Web service to mock the Functionality by passing input and output paths 
 */
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pizza.domain.FileInfo;
import io.pizza.service.OrderService;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.POST)
	public String sortLexically(@RequestBody FileInfo fileInfo) throws IOException, ParseException {

		return orderService.sortLexically(fileInfo.getInputPath(), fileInfo.getDestinationPath());
	}
}
