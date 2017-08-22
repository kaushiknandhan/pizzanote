package io.pizza.controller;

/**
 * REST Web service to mock the Functionality by passing input and output paths 
 */
import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;
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

	final static Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.POST)
	public String sortLexically(@RequestBody FileInfo fileInfo) throws ParseException {
		logger.info("Entered Sort Lexically method in order controller");
		String result = "";
		try {
			result = orderService.sortLexically(fileInfo.getInputPath(), fileInfo.getDestinationPath());
			logger.info("Sorted the file successfully");
		} catch (IOException e) {

			logger.error(e);
		}
		return result;
	}
}
