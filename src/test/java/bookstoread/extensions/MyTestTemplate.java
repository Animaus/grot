package bookstoread.extensions;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import bookstoread.Book;
import bookstoread.filter.BookFilter;
import bookstoread.filter.BookPublishedYearFilter;

//TODO p.130-3 - extensions, test template
public class MyTestTemplate implements TestTemplateInvocationContextProvider {

	@Override
	public boolean supportsTestTemplate(ExtensionContext context) {
		return true;
	}

	@Override
	public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
		Book cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
		Book codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
		return Stream.of(
				bookFilterTestContext("Before filter", BookPublishedYearFilter.Before(2007), cleanCode, codeComplete),
				bookFilterTestContext("After filter", BookPublishedYearFilter.After(2007), cleanCode, codeComplete));
	}

	private TestTemplateInvocationContext bookFilterTestContext(String testName, BookFilter bookFilter, Book... array) {
		return new TestTemplateInvocationContext() {
			@Override
			public String getDisplayName(int invocationIndex) {
				return testName;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public List<Extension> getAdditionalExtensions() {
				return Lists.newArrayList(new TypedParameterResolver(bookFilter), new TypedParameterResolver(array));
			}
		};

	}
}
