package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BookShelfStringTest {

	private static final String TITLE2 = "Java 8 in Action";
	private static final String TITLE1 = "The Phoenix Project";

	// p.051 - using BeforeEach
	private BookShelfString shelf;

	@BeforeEach
	void init(){
		// p.051 - using BeforeEach
		shelf = new BookShelfString();
	}

	// p.027 - JUnit5 constructors can be private and can have arguments: ParameterResolver
	private BookShelfStringTest(TestInfo testInfo){
		System.out.println("Test: " + testInfo.getDisplayName());
	}

	@Test
	void someNullAssertionTest(TestInfo testInfo){
		// p.032 - Test case makes use of all assertNull methods
		System.out.println("*** Method: " + testInfo.getDisplayName());
		String str = null;
		assertNull(str);
		assertNull(str, "str should be null");
		assertNull(str, () -> "str should be null");
	}

	@Test
	void someShouldCheckForEvenNumbers(TestInfo testInfo){
		// p.033 - assertTrue & assertFalse have overloaded method taking java.util.function.BooleanSupplier
		System.out.println("*** Method: " + testInfo.getDisplayName());

		int number = new Random(10).nextInt();
		assertTrue( () -> number%2 ==0, number+" is not an even number." );

		BiFunction<Integer, Integer, Boolean> divisible = (x, y) -> x % y ==0;
		Function<Integer, Boolean> multipleOf2 = (x) -> divisible.apply(x,2);
		assertTrue( () -> multipleOf2.apply(number), () -> " 2 is not factor of " + number);

		List<Integer> numbers = Arrays.asList(1,1,1,1,2);
		assertTrue(() -> numbers.stream().distinct().anyMatch(BookShelfStringTest:: isEven), "Did not find an even number in the list");
	}
	static boolean isEven(int number){
		return number % 2 == 0;
	}

	@Test
	@Disabled
	void someTestShouldFail(TestInfo testInfo){
		// p.034
		System.out.println("*** Method: " + testInfo.getDisplayName());
		fail(() -> "This test should fail");
	}

	@Test
	void bookshelfEmptyWhenNoBooksAdded(TestInfo testInfo) throws Exception {
		// p.026 - JUnit5 test classes and -methods are not required to be public; least typing preferred
		System.out.println("*** Method: " + testInfo.getDisplayName());
		List<String> books = shelf.books();
		assertTrue(books.isEmpty(), () -> "BookShelfString should be empty");
	}

	@Test
	@Disabled
	void bookshelfToStringShouldPrintBookCountAndTitles1(TestInfo testInfo) throws Exception {
		// p.035 - will break on just one assertion
		System.out.println("*** Method: " + testInfo.getDisplayName());
		List<String> books = shelf.books();
		shelf.add(TITLE2);
		String shelfStr = shelf.toString();
		System.out.println("shelfStr: " + shelfStr);
		assertTrue(shelfStr.contains(TITLE1), "1st book title missing");
		assertTrue(shelfStr.contains(TITLE2), "2nd book title missing");
		assertTrue(shelfStr.contains(books.size() + " books found"), "Book count missing");
	}

	@Test
	@Disabled
	void bookshelfToStringShouldPrintBookCountAndTitles2(TestInfo testInfo) throws Exception {
		// p.035 - run as single assertion to report just failed ones
		System.out.println("*** Method: " + testInfo.getDisplayName());
		List<String> books = shelf.books();
		// shelf.add("The Phoenix Project");
		shelf.add(TITLE2);
		String shelfStr = shelf.toString();
		assertAll(
				() -> assertTrue(shelfStr.contains(TITLE1), "1st book title missing"),
				() -> assertTrue(shelfStr.contains(TITLE2), "2nd book title missing"),
				() -> assertTrue(shelfStr.contains(books.size() + " books found"), "Book count missing")
		);
	}

	@Test
	void bookshelfContainsTwoBooksWhenTwoBooksAdded(TestInfo testInfo){
		// p.046 - write a failing test & make test pass
		System.out.println("*** Method: " + testInfo.getDisplayName());
		// p.048 - refactor the code - before
		// shelf.add("The Phoenix Project");
		// shelf.add("Java 8 in Action");
		// p.048 - refactor the code - after
		shelf.add(TITLE1, TITLE2);
		// some more useful code
		List<String> books = shelf.books();
		assertEquals(2, books.size(), () -> "Bookshelf should have two books");
	}

	@Test
	void bookshelfEmptyWhenAddCalledWithoutBooks(TestInfo testInfo) throws Exception {
		// p.050 - add testcases for exception scenarios
		System.out.println("*** Method: " + testInfo.getDisplayName());
		shelf.add();
		List<String> books = shelf.books();
		assertTrue(books.isEmpty(), () -> "BookShelfString should be empty");
	}

	@Test
	void bookshelfReturnsAreImmutable(TestInfo testInfo){
		// p.050 - add testcases for exception scenarios
		System.out.println("*** Method: " + testInfo.getDisplayName());
		shelf.add(TITLE1, TITLE2);
		List<String> books = shelf.books();
		try{
			books.add("The Mythical Man-Month");
			fail(() -> "Should not be able to add book to books");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
		}
	}

	@Test
	void bookshelfArrangedByTitle(TestInfo testInfo){
		// p.052 - second feature - failing & passing testcase
		System.out.println("*** Method: " + testInfo.getDisplayName());
		shelf.add(TITLE1, TITLE2);
		List<String> books = shelf.arrange();
		assertEquals(Arrays.asList(TITLE2, TITLE1), books , () -> "Books on a shelf should be arranged lexicographically by book title");
	}

	@Test
	void bookshelfInInsertionOrderAfterArrangedByTitle(TestInfo testInfo){
		// p.052 - second feature - failing & passing testcase
		System.out.println("*** Method: " + testInfo.getDisplayName());
		shelf.add(TITLE1, TITLE2);
		shelf.arrange();
		List<String> books = shelf.books();
		assertEquals(Arrays.asList(TITLE1, TITLE2), books , () -> "Books on a shelf are in insertion order");
	}
}
