package bookstoread;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
//import org.junit.platform.runner.JUnitPlatform;
import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;

import bookstoread.extensions.MyParameterResolution;
import bookstoread.extensions.MyExceptionHandling;
import bookstoread.extensions.MyLifeCycleCallbacks;
import bookstoread.filter.BookFilter;
import bookstoread.filter.BookPublishedYearFilter;
import bookstoread.filter.CompositeFilter;

//TODO p.093-4 - testing exceptions, using Rule (NOT for new tests...!!!)
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;

// TODO p.069 - dependency injection
//@RunWith(JUnitPlatform.class)
@DisplayName("Bookshelf test")
@ExtendWith(MyParameterResolution.class) // TODO p.127 - extensions, parameter resolution
public class BookShelfTest implements FilterBoundaryTests {
	private BookShelf shelf;
	private Book effectiveJava;
	private Book codeComplete;
	private Book mythicalManMonth;
	private Book cleanCode;
	private Book designPatterns;
	// TODO p.086-7 - common behaviour
	private BookFilter filter;

	// private BookShelfObjectTest(TestInfo testInfo) {
	// System.out.println("Test: " + testInfo.getDisplayName());
	// }
	// @BeforeEach
	// void init() {
	// shelf = new BookShelfObject();
	// effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008,
	// Month.MAY, 8));
	// codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004,
	// Month.JUNE, 9));
	// mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips
	// Brooks",
	// LocalDate.of(1975, Month.JANUARY, 1));
	// cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008,
	// Month.AUGUST, 1));
	// }

	@BeforeEach
	// TODO p.069 - dependency injection
	void init(Map<String, Book> books) {
		// TODO 00 specifics of books defined in
		// "bookstoread.BooksParameterResolver.getBooks()"
		shelf = new BookShelf();
		this.effectiveJava = books.get("Effective Java");
		this.codeComplete = books.get("Code Complete");
		this.mythicalManMonth = books.get("The Mythical Man-Month");
		this.cleanCode = books.get("Clean Code");
		this.designPatterns = books.get("Design Patterns");
	}

	static boolean isEven(int number) {
		return number % 2 == 0;
	}

	@Override
	// TODO p.086-7 - common behaviour
	public BookFilter get() {
		return filter;
	}

	@Nested
	@Disabled("Disabled : other")
	@DisplayName("No bookshelf")
	// TODO p.064 - nested tests
	public class Other {
		private Other(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@Test
		// @Disabled("Disabled : string should be null")
		@DisplayName("string should be null")
		void someNullAssertionTest(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			String str = null;
			assertNull(str);
			assertNull(str, "str should be null");
			assertNull(str, () -> "str should be null");
		}

		@Test
		// @Disabled("Disabled : no even number found in list")
		@DisplayName("no even number found in list")
		void someShouldCheckForEvenNumbers(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			int number = new Random(10).nextInt();
			assertTrue(() -> number % 2 == 0, number + " is not an even number.");
			BiFunction<Integer, Integer, Boolean> divisible = (x, y) -> x % y == 0;
			Function<Integer, Boolean> multipleOf2 = (x) -> divisible.apply(x, 2);
			assertTrue(() -> multipleOf2.apply(number), () -> " 2 is not factor of " + number);
			List<Integer> numbers = Arrays.asList(1, 1, 1, 1, 2);
			assertTrue(() -> numbers.stream().distinct().anyMatch(BookShelfStringTest::isEven),
					"Did not find an even number in the list");
		}

		@Test
		// @Disabled("Disabled : failing test")
		@DisplayName("failing test")
		void someTestShouldFail(TestInfo testInfo) {
			// p.034
			System.out.println("\t" + testInfo.getDisplayName());
			fail(() -> "This test should fail");
		}
	}

	@Nested
	@DisplayName("Bookshelf: is empty")
	// TODO p.064 - nested tests
	public class IsEmpty {
		private IsEmpty(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@Test
		@DisplayName("when no book is added")
		void bookshelfEmptyWhenAddCalledWithoutBooks(TestInfo testInfo) throws Exception {
			System.out.println("\t" + testInfo.getDisplayName());
			shelf.add();
			List<Book> books = shelf.books();
			assertTrue(books.isEmpty(), () -> "Bookshelf: should be empty when add no book");
		}

		@Test
		@DisplayName("initially")
		void bookshelfEmptyWhenNoBooksAdded(TestInfo testInfo) throws Exception {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.books();
			assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
		}
	}

	@Nested
	@DisplayName("Bookshelf: after adding")
	// TODO p.064 - nested tests
	public class AfterAdding {
		private AfterAdding(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@BeforeEach
		void setup() throws BookShelfCapacityReached {
			shelf.add(effectiveJava, codeComplete);
		}

		@Test
		@DisplayName("contains two books")
		void bookshelfContainsTwoBooksWhenTwoBooksAdded(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.books();
			assertEquals(2, books.size(), () -> "Bookshelf should have two books");
		}

		@Test
		@DisplayName("immutable collection")
		void bookshelfReturnsAreImmutable(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.books();
			try {
				books.add(mythicalManMonth);
				fail(() -> "Should not be able to add book to books");
			} catch (Exception e) {
				assertTrue(e instanceof UnsupportedOperationException,
						() -> "Should throw UnsupportedOperationException");
			}
		}
	}

	@Nested
	@DisplayName("Bookshelf: selecting")
	// TODO p.064 - nested tests
	public class Selecting {
		private Selecting(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@BeforeEach
		void setup() throws BookShelfCapacityReached {
			// Missing argument "cleanCode" - Book?
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
		}

		@Test
		@DisplayName("title or count missing (1)")
		void bookshelfToStringShouldPrintBookCountAndTitles1(TestInfo testInfo) throws Exception {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.books();
			String shelfStr = shelf.toString();
			// System.out.println("shelfStr: " + shelfStr);
			assertTrue(shelfStr.contains(effectiveJava.getTitle()), "1st book title missing");
			assertTrue(shelfStr.contains(codeComplete.getTitle()), "2nd book title missing");
			assertTrue(shelfStr.contains(books.size() + " books found"), "Book count missing");
		}

		@Test
		@DisplayName("title or count missing (2)")
		void bookshelfToStringShouldPrintBookCountAndTitles2(TestInfo testInfo) throws Exception {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.books();
			String shelfStr = shelf.toString();
			assertAll(() -> assertTrue(shelfStr.contains(effectiveJava.getTitle()), "1st book title missing"),
					() -> assertTrue(shelfStr.contains(codeComplete.getTitle()), "2nd book title missing"),
					() -> assertTrue(shelfStr.contains(books.size() + " books found"), "Book count missing"));
		}

		// TODO 03 group books on certain criteria - p.062 : group Books Inside BookShelf
		// By Publication Year
		@Test
		@DisplayName("by publication year")
		void bookshelfBooksByPublicationYear(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			// Rename
			// *** group Books Inside Book Shelf By Publication Year
			// to :
			// *** bookshelf Books By Publication Year
			Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();
			assertThat(booksByPublicationYear).containsKey(Year.of(2008))
					.containsValue(Arrays.asList(effectiveJava, cleanCode));
			assertThat(booksByPublicationYear).containsKey(Year.of(2004)).containsValue(Arrays.asList(codeComplete));
			assertThat(booksByPublicationYear).containsKey(Year.of(1975))
					.containsValue(Arrays.asList(mythicalManMonth));
		}
	}

	@Nested
	@DisplayName("Bookshelf: arranging by system")
	// TODO p.064 - nested tests
	public class ArrangingBySystem {
		private ArrangingBySystem(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@BeforeEach
		void setup() throws BookShelfCapacityReached {
			// Missing argument "cleanCode" - Book?
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
		}

		@Test
		@DisplayName("in insertion order")
		void bookshelfInInsertionOrderAfterArrangedByTitle(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			shelf.arrange();
			List<Book> books = shelf.books();
			assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth, cleanCode), books,
					() -> "Books on a shelf are in insertion order");
		}

		@Test
		@DisplayName("lexicographically by book title")
		void bookshelfArrangedByTitle(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.arrange();
			assertEquals(Arrays.asList(cleanCode, codeComplete, effectiveJava, mythicalManMonth), books,
					() -> "Books on a shelf should be arranged lexicographically by book title");
		}
	}

	@Nested
	@DisplayName("Bookshelf: arranging by user provided criteria")
	// TODO p.064 - nested tests
	public class ArrangingByUserCriteria {
		private ArrangingByUserCriteria(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@BeforeEach
		void setup() throws BookShelfCapacityReached {
			// Missing argument "cleanCode" - Book?
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
		}

		@Test
		@DisplayName("descending order of book title")
		void bookshelfArrangedByUserProvidedCriteria1(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
			List<Book> books = shelf.arrange(reversed);
			// assertEquals(Arrays.asList(book3, book1, book2), books,
			// () -> "Books on a shelf are arranged in descending order of book title");
			// p.059 - assertj
			// System.out.println(books.toString());
			assertThat(books).isSortedAccordingTo(reversed);
		}

		@Test
		@DisplayName("group by author name")
		// TODO 03 group books on certain criteria - p.063 : generify
		void bookshelfArrangedByUserProvidedCriteria2(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);
			assertThat(booksByAuthor).containsKey("Joshua Bloch").containsValues(singletonList(effectiveJava));
			assertThat(booksByAuthor).containsKey("Steve McConnel").containsValues(singletonList(codeComplete));
			assertThat(booksByAuthor).containsKey("Frederick Phillips Brooks")
					.containsValues(singletonList(mythicalManMonth));
			assertThat(booksByAuthor).containsKey("Robert C. Martin").containsValues(singletonList(cleanCode));
		}
	}

	// TODO 04 track bookshelf progress - p.073
	@Nested
	@DisplayName("Bookshelf: progress")
	public class ShowProgress {
		private ShowProgress(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@BeforeEach
		void setup() throws BookShelfCapacityReached {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode, designPatterns);
		}

		@Test
		@DisplayName("0% completed and 100% to read when no book read")
		void progressAllUnread(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			Progress progress = shelf.progress();
			assertThat(progress.completed()).isEqualTo(0);
			assertThat(progress.toRead()).isEqualTo(100);
		}

		// TODO 04 track bookshelf progress - p.074
		@Test
		@DisplayName("40% completed and 60% to read when 2 books read and 3 not read")
		void progressCompletedAndToRead(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
			effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
			cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
			cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
			Progress progress = shelf.progress();
			assertThat(progress.completed()).isEqualTo(40);
			assertThat(progress.toRead()).isEqualTo(60);
		}

		// TODO 04 track bookshelf progress - p.074
		@Test
		@DisplayName("40% completed, 20% in progress and 40% to read when 2 books read, 1 reading and 2 not read")
		void progressCompletedAndToReadAndInProgress(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
			effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
			cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
			cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
			designPatterns.startedReadingOn(LocalDate.of(2018, Month.JULY, 18));
			Progress progress = shelf.progress();
			assertThat(progress.completed()).isEqualTo(40);
			assertThat(progress.inProgress()).isEqualTo(20);
			assertThat(progress.toRead()).isEqualTo(40);
		}
	}

	// TODO 05 search bookshelf - p.079
	@Nested
	@DisplayName("Bookshelf: search")
	public class Search {
		private Search(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		// TODO 05 search bookshelf - p.081 user exercise (helper method)
		private String getMessage(Book book, int year, boolean isBefore) {
			String time = ", not after ";
			if (isBefore) {
				time = ", not before ";
			}
			return book.getTitle() + " published on " + book.getPublishedOn() + time + year;
		}

		@BeforeEach
		void setup() throws BookShelfCapacityReached {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
		}

		@Test
		@DisplayName("should find books with text in title")
		void searchWithTitle(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			List<Book> books = shelf.findBooksByTitle("code");
			assertThat(books.size()).isEqualTo(2);
		}

		@Test
		@DisplayName("should find books with text in title and published before")
		void searchWithTitleAndBeforePublishedDate(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			Predicate<Book> criteria = b -> b.getPublishedOn().isBefore(LocalDate.of(2018, 7, 20));
			List<Book> books = shelf.findBooksByTitle("code", criteria);
			assertThat(books.size()).isEqualTo(2);
		}

		// TODO 05 search bookshelf - p.081 filter (test case)
		@Test
		@DisplayName("should find books with published after")
		@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
		void searchAfterPublishedDate(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			int year = 2007;

			// TODO p.086-7 - common behaviour *
			// BookFilter filter = BookPublishedYearFilter.After(year);
			filter = BookPublishedYearFilter.After(year);

			assertTrue(filter.apply(cleanCode), getMessage(cleanCode, year, false));
			assertFalse(filter.apply(codeComplete), getMessage(codeComplete, year, false));
			// assertTrue(filter.apply(designPatterns), getMessage(designPatterns, year,
			// false));
		}

		// TODO 05 search bookshelf - p.081 user exercise (test case)
		@Test
		@DisplayName("should find books with published before")
		@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
		void searchBeforePublishedDate(TestInfo testInfo) {
			System.out.println("\t" + testInfo.getDisplayName());
			int year = 2007;

			// TODO p.086-7 - common behaviour *
			// BookFilter filter = BookPublishedYearFilter.Before(year);
			filter = BookPublishedYearFilter.Before(year);

			assertFalse(filter.apply(cleanCode), getMessage(cleanCode, year, true));
			assertTrue(filter.apply(codeComplete), getMessage(codeComplete, year, true));
		}

		@Nested
		@DisplayName("composite criteria")
		public class Composite {
			private Composite(TestInfo testInfo) {
				System.out.println("  " + testInfo.getDisplayName());
			}

			// TODO 05 search bookshelf - p.082 CompositeFilter, combine multiple filters
			// (test case)
			@Test
			@DisplayName("based on multiple filters")
			@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
			void searchOnMultipleFilters(TestInfo testInfo) {
				System.out.println("\t" + testInfo.getDisplayName());

				CompositeFilter compositeFilter = new CompositeFilter();
				compositeFilter.addFilter(book -> false);

				assertFalse(compositeFilter.apply(cleanCode));
			}

			// TODO 05 search bookshelf - p.083 CompositeFilter, invoking stopped (test case)
			@Test
			@DisplayName("no invoke after first failure")
			@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
			void noInvokeAfterFirstFailure(TestInfo testInfo) {
				System.out.println("\t" + testInfo.getDisplayName());
				CompositeFilter compositeFilter = new CompositeFilter();
				compositeFilter.addFilter(book -> false);
				compositeFilter.addFilter(book -> true);
				assertFalse(compositeFilter.apply(cleanCode));
			}

			// TODO 05 search bookshelf - p.083 CompositeFilter, invoking all (test case)
			@Test
			@DisplayName("invoke all filters")
			@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
			void invokeAllFilters(TestInfo testInfo) {
				System.out.println("\t" + testInfo.getDisplayName());
				CompositeFilter compositeFilter = new CompositeFilter();
				compositeFilter.addFilter(book -> true);
				compositeFilter.addFilter(book -> true);
				assertFalse(compositeFilter.apply(cleanCode));
			}

			// TODO mocking - p.083 CompositeFilter, mocking (test case)
			@Test
			@DisplayName("criteria invokes multiple filters")
			@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
			void searchOnMultipleCriteria(TestInfo testInfo) {
				System.out.println("\t" + testInfo.getDisplayName());
				CompositeFilter compositeFilter = new CompositeFilter();
				final Map<Integer, Boolean> invocationMap = new HashMap<Integer, Boolean>();
				compositeFilter.addFilter(book -> {
					invocationMap.put(1, true);
					return true;
				});
				assertFalse(compositeFilter.apply(cleanCode));
			}

			// TODO mocking - p.085 CompositeFilter with mock, invoking stopped (test case)
			@Test
			@DisplayName("(mocking) no invoke after first failure")
			@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
			void noInvokeAfterFirstFailureWithMock(TestInfo testInfo) {
				System.out.println("\t" + testInfo.getDisplayName());
				// Instantiate composite filters
				CompositeFilter compositeFilter = new CompositeFilter();
				// Generate mocks and record invocations - first filter
				BookFilter invokedMockFilter = Mockito.mock(BookFilter.class);
				Mockito.when(invokedMockFilter.apply(cleanCode)).thenReturn(false);
				compositeFilter.addFilter(invokedMockFilter);
				// Generate mocks and record invocations - first filter
				BookFilter nonInvokedMockFilter = Mockito.mock(BookFilter.class);
				Mockito.when(nonInvokedMockFilter.apply(cleanCode)).thenReturn(true);
				compositeFilter.addFilter(nonInvokedMockFilter);
				// Apply composite filters to book
				assertFalse(compositeFilter.apply(cleanCode));
				// Verify interactions with mock
				Mockito.verify(invokedMockFilter).apply(cleanCode);
				// Mockito.verifyZeroInteractions(nonInvokedMockFilter);
			}

			// TODO mocking - p.085 CompositeFilter with mock, invoking all (test case)
			@Test
			@DisplayName("(mocking) invoke all filters")
			@Tag("Filter") // TODO p.087 - grouping by tags, not supported by all IDE (yet)
			void invokeAllFiltersWithMock(TestInfo testInfo) {
				System.out.println("\t" + testInfo.getDisplayName());
				// Instantiate composite filters
				CompositeFilter compositeFilter = new CompositeFilter();
				// Generate mocks and record invocations - first filter
				BookFilter firstInvokedMockFilter = Mockito.mock(BookFilter.class);
				Mockito.when(firstInvokedMockFilter.apply(cleanCode)).thenReturn(true);
				compositeFilter.addFilter(firstInvokedMockFilter);
				// Generate mocks and record invocations - second filter
				BookFilter secondInvokedMockFilter = Mockito.mock(BookFilter.class);
				Mockito.when(secondInvokedMockFilter.apply(cleanCode)).thenReturn(true);
				compositeFilter.addFilter(secondInvokedMockFilter);
				// Apply composite filters to book
				assertFalse(compositeFilter.apply(cleanCode));
				// Verify interactions with mock
				Mockito.verify(firstInvokedMockFilter).apply(cleanCode);
				Mockito.verify(secondInvokedMockFilter).apply(cleanCode);
			}

		}
	}

	@Nested
	@DisplayName("testing exceptions")
	@EnableRuleMigrationSupport // TODO p.093-4 - testing exceptions, using Rule
	@ExtendWith(MyExceptionHandling.class) // TODO p.094-5 - exception handling extension
	public class TestingExceptions {

		private TestingExceptions(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@Test
		@DisplayName("exception on adding after capacity is reached (try-catch-fail)")
		// TODO p.089-91 - testing exceptions, try-catch-fail
		void throwsExceptionWhenBooksAreAddedAfterCapacityIsReachedTryCatchFail() {
			shelf = new BookShelf(2);
			shelf.add(effectiveJava, codeComplete);
			try {
				shelf.add(mythicalManMonth);
				fail("should throw exception as more books are added than shelf capacity");
			} catch (BookShelfCapacityReached expected) {
				assertEquals("BookShelf capacity of 2 is reached. You can't add more books.", expected.getMessage());
			}
		}

		@Test
		@DisplayName("exception on adding after capacity is reached (assertThrows)")
		// TODO p.092 - testing exceptions, using assertThrows
		void throwsExceptionWhenBooksAreAddedAfterCapacityIsReachedAssertThrows() {
			shelf = new BookShelf(2);
			shelf.add(effectiveJava, codeComplete);
			// TODO p.093 - testing exceptions, Executable interface
			BookShelfCapacityReached throwException = assertThrows(BookShelfCapacityReached.class,
					() -> shelf.add(mythicalManMonth));
			assertEquals("BookShelf capacity of 2 is reached. You can't add more books.", throwException.getMessage());
		}

		@Rule
		// TODO p.093-4 - testing exceptions, using Rule (NOT for new tests...!!!)
		public ExpectedException expectedException = ExpectedException.none();

		@Test
		@DisplayName("exception on adding after capacity is reached (Rule)")
		// TODO p.093-4 - testing exceptions, using Rule (NOT for new tests...!!!)
		@Disabled("code from book, does not work")
		void throwsExceptionWhenBooksAreAddedAfterCapacityIsReachedRule() {
			shelf = new BookShelf(1);
			expectedException.expect(BookShelfCapacityReached.class);
			expectedException.expectMessage("BookShelf capacity of 1 is reached. You can't add more books.");
			shelf.add(effectiveJava, codeComplete);
		}

	}

	@Nested
	@DisplayName("testing other features")
	@ExtendWith(MyLifeCycleCallbacks.class) // TODO p.124-6 - extensions, test life cycle callbacks
	public class TestingOtherFeatures {

		private TestingOtherFeatures(TestInfo testInfo) {
			System.out.println("- " + testInfo.getDisplayName());
		}

		@Test
		@DisplayName("should complete in one second")
		// TODO p.095 - working with timeout
		void shouldCompleteInOneSecond() {
			// Should run 1 second, but sleeps 2 seconds... So: fail...!
			assertTimeout(Duration.of(1, ChronoUnit.SECONDS), () -> Thread.sleep(2000));
		}

		@Test
		@DisplayName("should complete in one second (preemptively)")
		// TODO p.096 - working with timeoutPreemptively
		void shouldCompleteInOneSecondPreemptively() {
			assertTimeoutPreemptively(Duration.of(1, ChronoUnit.SECONDS), () -> Thread.sleep(2000));
		}

		// @RepeatedTest(10) // simple annotation
		@RepeatedTest(value = 10, name = "this is a repeated test")
		@DisplayName("this is a repeated test")
		// TODO p.096-7 - repeated tests
		void repeatedTest() {
			assertTrue(true);
		}

	}

}
