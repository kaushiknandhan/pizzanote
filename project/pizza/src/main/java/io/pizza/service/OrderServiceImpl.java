package io.pizza.service;

/**
 * Business Logic Layer to sort the data in the file and write in a new text file
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import io.pizza.controller.OrderController;
import io.pizza.domain.Order;
import io.pizza.exception.NoContentPresent;

@Service
public class OrderServiceImpl implements OrderService {

	final static Logger logger = Logger.getLogger(OrderServiceImpl.class);

	public String sortLexically(String sourcePath, String destinationPath) throws ParseException, IOException {

		// Initialize List of Order type to add Orders
		List<Order> orderlist = new ArrayList<Order>();
		// BufferReader Object to Read the Inputfile
		BufferedReader readerInput = new BufferedReader(new FileReader(sourcePath));
		// Buffer Object to write in output file
		BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath));

		// get the Header of the file
		String header = getHeader(sourcePath, readerInput);
		orderlist = readDataFromFile(sourcePath, readerInput);

		logger.info("Sorting the Data present in the List");
		// Sort the Collection using comparator in lexical order
		Collections.sort(orderlist, (order1, order2) -> {
			return order1.getProductName().compareToIgnoreCase(order2.getProductName());
		});

		writeDataInFile(destinationPath, header, orderlist, writer);
		
		logger.info("Closing buffered Input read and writes");
		// Closing the resources
		readerInput.close();
		writer.close();
		return "Sucess";
	}

	private String getHeader(String sourcePath, BufferedReader readerInput) throws IOException {
		logger.info("Entered in Get Header methd");
		// Reading the firstLine of the input file as
		String firstLine = readerInput.readLine();
		// If the Input file is empty Throw an exception
		if (firstLine == null || firstLine.length() == 0) {
			throw new NoContentPresent("There is no Content Available to proceed Please check the input file");
		}
		return firstLine;
	}

	private void writeDataInFile(String destinationPath, String header, List<Order> orderlist, BufferedWriter writer)
			throws IOException {
		logger.info("Entered in Get WriteDataFile method");
		// Write the Header of the file
		writer.write(header);

		// Write Each line to the destination file one by one
		orderlist.forEach(order -> {
			try {
				writer.newLine();
				// if the length of the order is more than 6 use a single tab
				// space
				if (order.getProductName().length() > 6)
					writer.write(order.getProductName() + "\t" + order.getOrderedOn());
				// if the length of the order is less than 6 use double tab
				// space
				else
					writer.write(order.getProductName() + "\t\t" + order.getOrderedOn());
			} catch (Exception e) {
				// Catch exception if present
				logger.error(e.getMessage());
			}
		});

	}

	private List<Order> readDataFromFile(String sourcePath, BufferedReader readerInput) throws IOException {

		logger.info("Entered in Read Data from File methd");
		// Initialize List to get data from the file
		List<Order> dataList = new ArrayList<Order>();

		// If input file has some content, get the line
		String row = readerInput.readLine();

		// Loop through the text file and read records present
		while (row != null && row.length() != 0) {
			String[] order = row.trim().split("\\s+");
			String productName = order[0];
			Date orderedOn = new Date(Long.parseLong(order[1]));
			dataList.add(new Order(productName, orderedOn.getTime()));
			row = readerInput.readLine();
		}

		return dataList;
	}
}
