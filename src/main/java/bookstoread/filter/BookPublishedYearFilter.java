package bookstoread.filter;

import java.time.LocalDate;

import bookstoread.Book;

// TODO 05 search bookshelf - p.081
public class BookPublishedYearFilter implements BookFilter {
	private LocalDate afterDate;
	// TODO 05 search bookshelf - p.081 user exercise
	private LocalDate beforeDate;

	public static BookPublishedYearFilter After(final int year) {
		BookPublishedYearFilter filter = new BookPublishedYearFilter();
		filter.afterDate = LocalDate.of(year, 12, 31);
		return filter;
	}

	// TODO 05 search bookshelf - p.081 user exercise
	public static BookPublishedYearFilter Before(final int year) {
		BookPublishedYearFilter filter = new BookPublishedYearFilter();
		filter.beforeDate = LocalDate.of(year, 1, 1);
		return filter;
	}

	@Override
	public boolean apply(final Book book) {
		// return book.getPublishedOn().isAfter(startDate);
		// TODO 05 search bookshelf - p.081 user exercise
		boolean result = false;
		if (afterDate == null && beforeDate == null) {
		} else if (afterDate != null && beforeDate != null) {
		} else if (afterDate == null && beforeDate != null) {
			result = book.getPublishedOn().isBefore(beforeDate);
		} else if (afterDate != null && beforeDate == null) {
			result = book.getPublishedOn().isAfter(afterDate);
		}
		return result;
	}

}
