package bookstoread;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import bookstoread.extensions.AfterYearArgsProvider;
import bookstoread.extensions.BeforeYearArgsProvider;
import bookstoread.extensions.MyArgumentsProvider;

//import org.junit.jupiter.params.provider.ValueSource;

import bookstoread.extensions.MyTestTemplate;
import bookstoread.filter.BookFilter;
import bookstoread.filter.BookPublishedYearFilter;

public class ExtensionsTest {

	// TODO p.130-3 - extensions, test template
	@TestTemplate
	@ExtendWith(MyTestTemplate.class)
	void validateFilters(BookFilter filter, Book[] books) {
		assertNotNull(filter);
		assertFalse(filter.apply(books[0]),
				"Book \"" + books[0].getTitle() + "\" was published in " + books[0].getPublishedOn());
		assertTrue(filter.apply(books[1]),
				"Book \"" + books[1].getTitle() + "\" was published in " + books[1].getPublishedOn());
	}

	// TODO p.133 - extensions, parameterized test
	@ParameterizedTest
	@ValueSource(strings = { "Effective Java", "Code Complete", "Clean Code" })
	void shouldGiveBackBooksForTitle(String title) {
		Book effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
		Book codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
		Book mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks",
				LocalDate.of(1975, Month.JANUARY, 1));
		Book cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
		Book designPatterns = new Book("Design Patterns", "Eric Freeman", LocalDate.of(2004, Month.OCTOBER, 1));

		BookShelf shelf = new BookShelf();
		shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode, designPatterns);

		List<Book> foundBooks = shelf.findBooksByTitle(title.toLowerCase());
		assertNotNull(foundBooks);
		assertEquals(1, foundBooks.size());

		foundBooks = shelf.findBooksByTitle(title.toUpperCase());
		assertNotNull(foundBooks);
		assertEquals(0, foundBooks.size());
	}

	// TODO p.135 - extensions, parameterized test
	static Stream<BookFilter> bookFilterProvider() {
		int year = 2007;
		return Stream.of(BookPublishedYearFilter.Before(year), BookPublishedYearFilter.After(year));
	}

	// TODO p.135 - extensions, parameterized test
	@ParameterizedTest
	@MethodSource("bookFilterProvider")
	void validateFilterWithNullData(BookFilter filter) {
		assertThat(filter.apply(null)).isFalse();
	}

	// TODO p.135 - extensions, parameterized test
	// @ParameterizedTest
	@ParameterizedTest(name = "{index} : Validating {1}")
	@ArgumentsSource(MyArgumentsProvider.class)
	void validateFiltersWithBooks1(BookFilter filter, Book[] books) {
		assertNotNull(filter);
		assertFalse(filter.apply(books[0]));
		assertTrue(filter.apply(books[1]));
	}
	
	// TODO p.136 - extensions, parameterized test
	@ParameterizedTest(name = "{index} : Validating {1}")
	@DisplayName("Filter validates a passing book")
	@ArgumentsSource(BeforeYearArgsProvider.class)
	@ArgumentsSource(AfterYearArgsProvider.class)
	void validateFiltersWithBooks2(BookFilter filter, Book book) {
		assertNotNull(filter);
		assertTrue(filter.apply(book));
	}
}
