package io.pizza.service;

import java.io.IOException;
import java.text.ParseException;

public interface OrderService {

	public String sortLexically(String sourcePath, String destinationPath) throws IOException, ParseException;

}
