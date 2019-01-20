package bookstoread.filter;

import java.util.ArrayList;
import java.util.List;

import bookstoread.Book;

// TODO 05 search bookshelf - p.082 CompositeFilter
public class CompositeFilter implements BookFilter {

	private List<BookFilter> filters;

	public CompositeFilter() {
		filters = new ArrayList<BookFilter>();
	}

	@Override
	public boolean apply(final Book book) {
		filters.stream().map(bookFilter -> bookFilter.apply(book)).reduce(true, (book1, book2) -> book1 && book2);
		return false;
	}

	public void addFilter(final BookFilter filter) {
		filters.add(filter);
	}

}
