package bookstoread;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import bookstoread.filter.BookFilter;

public class BookShelf {
	private List<Book> books = new ArrayList<Book>();
	private int capacity = 2; // TODO p.089 - testing exceptions, context setting

	public static void main(String[] args) throws BookShelfCapacityReached {
		BookShelf shelf = new BookShelf();
		List<Book> books = shelf.books();
		shelf.add(new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
		shelf.add(new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9)));
		System.out.println("Count: " + books.size());
		System.out.println("toString: " + shelf.toString());
	}

	// TODO p.089 - testing exceptions, context setting
	public BookShelf() {
	}

	// TODO p.089 - testing exceptions, context setting
	public BookShelf(int capacity) {
		this.capacity = capacity;
	}

	public List<Book> books() {
		return Collections.unmodifiableList(this.books);
	}

	/**
	 * Book is confusing and may be wrong @ p.089.
	 * 
	 * 1 - Check is described as being in BookShelf var-arg method (for multiple
	 * books). In test adding multiple books is outside try-catch, suggesting
	 * var-arg method should not be used to add books.
	 * 
	 * 2 - Compiler forces var-arg method to differ from book. Complains about
	 * unhandled exception, where declare throws is already in place.
	 * 
	 * My conclusion - Code is most similar to book when applied to single-arg
	 * method. Since capacity may already be exceeded at time of check, I use larger
	 * or equal (>=) instead just equal (==).
	 * 
	 * @param book
	 * @throws BookShelfCapacityReached
	 */
	public void add(Book book) throws BookShelfCapacityReached {
		// books.add(book);
		// TODO p.089 - testing exceptions, context setting
		if (books.size() >= capacity) {
			throw new BookShelfCapacityReached(
					String.format("BookShelf capacity of %d is reached. You can't add more books.", this.capacity));
		}
		books.add(book);
	}

	public void add(Book... booksToAdd) {
		Arrays.stream(booksToAdd).forEach(books::add);
	}

	public int size() {
		return this.books.size();
	}

	public List<Book> arrange() {
		// p.057 - refactor
		// return books.stream().sorted().collect(Collectors.toList());
		return arrange(Comparator.naturalOrder());
	}

	public List<Book> arrange(Comparator<Book> criteria) {
		return books.stream().sorted(criteria).collect(toList());
	}

	// TODO 03 group books on certain criteria - p.062 : group Books Inside BookShelf
	// By Publication Year
	public Map<Year, List<Book>> groupByPublicationYear() {
		return books.stream().collect(groupingBy(book -> Year.of(book.getPublishedOn().getYear())));
	}

	// TODO 03 group books on certain criteria - p.063 : generify
	public <K> Map<K, List<Book>> groupBy(Function<Book, K> criteria) {
		return books.stream().collect(groupingBy(criteria));
	}

	public Progress progress() {
		// TODO 04 track bookshelf progress - p.073
		// return new Progress(0, 100, 0);
		// TODO 04 track bookshelf progress - p.075
		int allBooks = books.size();
		int booksCompleted = Long.valueOf(books.stream().filter(Book::isCompleted).count()).intValue();
		int booksInProgress = Long.valueOf(books.stream().filter(Book::inProgress).count()).intValue();
		int booksToRead = allBooks - booksCompleted - booksInProgress;
		int completed = booksCompleted * 100 / allBooks;
		int inProgress = booksInProgress * 100 / allBooks;
		int toRead = booksToRead * 100 / allBooks;
		return new Progress(completed, toRead, inProgress);
	}

	public String toString() {
		return books.size() + " books found, " + books.toString();
	}

	// TODO 05 search bookshelf - p.079
	public List<Book> findBooksByTitle(String text) {
		Predicate<Book> criteria = book -> true;
		return findBooksByTitle(text, criteria);
	}

	// TODO 05 search bookshelf - p.079
	public List<Book> findBooksByTitle(String text, Predicate<Book> criteria) {
		Predicate<Book> how = book -> book.getTitle().toLowerCase().contains(text);
		return books.stream().filter(how).filter(criteria).collect(toList());
	}

	// TODO 05 search bookshelf - p.080
	public List<Book> findBooksByTitle(String text, BookFilter filter) {
		Predicate<Book> how = book -> book.getTitle().toLowerCase().contains(text);
		return books.stream().filter(how).filter(book -> filter.apply(book)).collect(toList());
	}
}
