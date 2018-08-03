package bookstoread.extensions;

import java.util.logging.Logger;
import java.util.logging.Level;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import bookstoread.BookShelfTest.TestingExceptions;

//TODO p.094-5 - exception handling extension
//TODO p.129 - extensions, exception handling
public class MyExceptionHandling implements TestExecutionExceptionHandler {
	// public class LoggingTestExecutionExceptionHandler implements TestExecutionExceptionHandler {
	
	private Logger logger = Logger.getLogger(TestingExceptions.class.getName());

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		logger.log(Level.INFO, "Exception thrown", throwable);
		throw throwable;
	}

}
