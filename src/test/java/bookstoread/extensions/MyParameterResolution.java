package bookstoread.extensions;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import bookstoread.Book;

//TODO p.071 - dependency injection
//TODO p.127 - extensions, parameter resolution
public class MyParameterResolution implements ParameterResolver {
	// public class BooksParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Parameter parameter = parameterContext.getParameter();
		return Objects.equals(parameter.getParameterizedType().getTypeName(),
				"java.util.Map<java.lang.String, bookstoread.Book>");
	}

	@Override
	public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
			throws ParameterResolutionException {
		// TODO p.076 - Caching data no use when modified in one or several testcases
		// TODO p.078 - using ExtensionContext Store, serve the books-array from a custom Books namespace
		ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(Book.class));
		return store.getOrComputeIfAbsent("books", k -> getBooks());
	}

	// TODO p.078 - using ExtensionContext Store
	private Map<String, Book> getBooks() {
		// Creation of books moved from method "resolveParameter"
		Map<String, Book> books = new HashMap<String, Book>();
		books.put("Effective Java", new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
		books.put("Code Complete", new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9)));
		books.put("The Mythical Man-Month",
				new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1)));
		books.put("Clean Code", new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1)));
		books.put("Design Patterns", new Book("Design Patterns", "Eric Freeman", LocalDate.of(2004, Month.OCTOBER, 1)));
		return books;
	}

}
