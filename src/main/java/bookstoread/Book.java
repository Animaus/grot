package bookstoread;

import java.time.LocalDate;

// p.054 - refactor
public class Book implements Comparable<Book> {
	private final String title;
	private final String author;
	private final LocalDate publishedOn;
	private LocalDate startedReadingOn;
	private LocalDate finishedReadingOn;

	public Book(String title, String author, LocalDate publishedOn) {
		this.title = title;
		this.author = author;
		this.publishedOn = publishedOn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDate getPublishedOn() {
		return publishedOn;
	}

	// TODO 04 track bookshelf progress - p.075
	public LocalDate startedReadingOn() {
		return startedReadingOn;
	}

	// TODO 04 track bookshelf progress - p.075
	public void startedReadingOn(LocalDate startedReadingOn) {
		this.startedReadingOn = startedReadingOn;
	}

	// TODO 04 track bookshelf progress - p.075
	public LocalDate finishedReadingOn() {
		return finishedReadingOn;
	}

	// TODO 04 track bookshelf progress - p.075
	public void finishedReadingOn(LocalDate finishedReadingOn) {
		this.finishedReadingOn = finishedReadingOn;
	}

	// TODO 04 track bookshelf progress - p.075
	public boolean isCompleted() {
		return startedReadingOn != null && finishedReadingOn != null;
	}

	// TODO 04 track bookshelf progress - p.075
	public boolean inProgress() {
		return startedReadingOn != null && finishedReadingOn == null;
	}

	@Override
	public String toString() {
		return "Book{" + "title=\"" + title + "\", author=\"" + author + "\", publishedOn=\"" + publishedOn + "\"}";
	}

	@Override
	public int compareTo(Book that) {
		return this.title.compareTo(that.title);
	}
}
