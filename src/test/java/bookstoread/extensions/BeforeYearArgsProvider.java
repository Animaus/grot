package bookstoread.extensions;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import bookstoread.Book;
import bookstoread.filter.BookPublishedYearFilter;

// TODO p.136 - extensions, parameterized test
public class BeforeYearArgsProvider implements ArgumentsProvider {
	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		Book effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
		Book codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
		Book mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks",
				LocalDate.of(1975, Month.JANUARY, 1));
		Book cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
		Book designPatterns = new Book("Design Patterns", "Eric Freeman", LocalDate.of(2004, Month.OCTOBER, 1));
		int year = 2007;
		return Stream.of(Arguments.of(BookPublishedYearFilter.Before(year), effectiveJava),
				Arguments.of(BookPublishedYearFilter.Before(year), codeComplete),
				Arguments.of(BookPublishedYearFilter.Before(year), mythicalManMonth),
				Arguments.of(BookPublishedYearFilter.Before(year), cleanCode),
				Arguments.of(BookPublishedYearFilter.Before(year), designPatterns));
	}
}
