package bookstoread.filter;

import bookstoread.Book;

// TODO 05 search bookshelf - p.080
public interface BookFilter {
	boolean apply(Book book);
}
