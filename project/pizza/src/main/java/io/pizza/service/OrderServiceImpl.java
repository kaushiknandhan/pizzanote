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

import org.springframework.stereotype.Service;

import io.pizza.domain.Order;
import io.pizza.exception.NoContentPresent;

@Service
public class OrderServiceImpl implements OrderService {

	public String sortLexically(String sourcePath, String destinationPath) throws IOException, ParseException {
		
		// BufferReader Object to Read the Inputfile
		BufferedReader readerInput = new BufferedReader(new FileReader(sourcePath));
		// Initialize List of Order type to add Orders
		List<Order> orderlist = new ArrayList<Order>();
		// Reading the firstLine of the input file as
		String firstLine = readerInput.readLine();

		// If the Input file is empty Throw an exception
		if (firstLine == null || firstLine.length() == 0) {
			throw new NoContentPresent("There is no Content Available to proceed");
		}

		// If input file has some content, get the line
		String row = readerInput.readLine();

		// Loop through the text file and read records present
		while (row != null && row.length() != 0) {
			String[] order = row.trim().split("\\s+");
			String productName = order[0];
			Date orderedOn = new Date(Long.parseLong(order[1]));
			orderlist.add(new Order(productName, orderedOn.getTime()));
			row = readerInput.readLine();
		}

		// Sort the Collection using comparator in lexical order
		Collections.sort(orderlist, (o1, o2) -> {
			return o1.getProductName().compareToIgnoreCase(o2.getProductName());
		});
		// Buffer Object to write in output file
		BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath));
		// Write the Header of the file
		writer.write(firstLine);
		// Go to the next line
		// writer.newLine();
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
				// create a new line
				// writer.newLine();
			} catch (Exception e) {
				// Catch exception if present
				e.printStackTrace();
			}
		});

		// Closing the resources
		readerInput.close();
		writer.close();
		return "Sucess";
	}
}
