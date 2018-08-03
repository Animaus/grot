package bookstoread;

public class BookShelfCapacityReached extends Exception {
	private static final long serialVersionUID = 1L;

	BookShelfCapacityReached() {
		super();
	}

	BookShelfCapacityReached(String s) {
		super(s);
	}
}