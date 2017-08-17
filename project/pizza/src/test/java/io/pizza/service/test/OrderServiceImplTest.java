package io.pizza.service.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;
import org.mockito.InjectMocks;

import io.pizza.exception.NoContentPresent;
import io.pizza.service.OrderService;
import io.pizza.service.OrderServiceImpl;

public class OrderServiceImplTest {

	@InjectMocks
	private OrderService orderService = new OrderServiceImpl();

	// Test case to compare the result sorted file with the expected File
	@Test
	public void testSortLexically() throws IOException, ParseException {
		String inputPath1 = "E:/BigProjects/assignment/pizza/src/test/resources/inputTest.txt";
		String resultPath1 = "E:/BigProjects/assignment/pizza/src/test/resources/resultTest.txt";
		String expected1 = "E:/BigProjects/assignment/pizza/src/test/resources/expectedTest.txt";

		orderService.sortLexically(inputPath1, resultPath1);
		BufferedReader actual = new BufferedReader(new FileReader(resultPath1));
		BufferedReader expected = new BufferedReader(new FileReader(expected1));

		String rowActual = actual.readLine();
		String rowExpected = expected.readLine();
		boolean isEqual = true;

		while (rowActual != null || rowExpected != null) {
			if (rowActual == null || rowExpected == null) {
				isEqual = false;

				break;
			} else if (!rowActual.equalsIgnoreCase(rowExpected)) {
				isEqual = false;
				break;
			}
			rowActual = actual.readLine();
			rowExpected = expected.readLine();
		}
		assertEquals(true, isEqual);
		actual.close();
		expected.close();
	}

	// Test Case to check RunTime Exception No Cntent present if there is no
	// content present in the file
	@Test(expected = NoContentPresent.class)
	public void testSortLexicallyException() throws IOException, ParseException {
		String inputPath2 = "E:/BigProjects/assignment/pizza/src/test/resources/inputTest2.txt";
		String resultPath2 = "E:/BigProjects/assignment/pizza/src/test/resources/resultTest2.txt";
		orderService.sortLexically(inputPath2, resultPath2);
	}

	// Test Case to check IOException if the file is not found
	@Test(expected = IOException.class)
	public void testSortLexicallyIOException() throws IOException, ParseException {
		String inputPath2 = "E:/BigProjects/assignment/pizza/src/test/resources/noFile.txt";
		String resultPath3 = "E:/BigProjects/assignment/pizza/src/test/resources/resultTest3.txt";
		orderService.sortLexically(inputPath2, resultPath3);
	}

}
