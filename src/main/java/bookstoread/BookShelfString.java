package bookstoread;

import java.util.*;
import java.util.stream.Collectors;

public class BookShelfString {
    private List<String> books = new ArrayList<String>();

    public static void main(String[] args) {
        BookShelfString shelf = new BookShelfString();
        List<String> books = shelf.books();
        shelf.add("The Phoenix Project");
        shelf.add("Java 8 in Action");
        System.out.println("Count: " + books.size());
        System.out.println("toString: " + shelf.toString());
    }

    public List<String> books(){
		return Collections.unmodifiableList(this.books);
    }

    public void add(String title){
        books.add(title);
    }

    // p.048 - refactor the code
    public void add(String... titlesToAdd){
        // using lambda
        // Arrays.stream(titlesToAdd).forEach(title -> books.add(title));
        // using method reference
        Arrays.stream(titlesToAdd).forEach(books::add);
    }

    public int size(){
        return this.books.size();
    }

    public List<String> arrange(){
        // books.sort(Comparator.naturalOrder());
        // return books;
        return books.stream().sorted().collect(Collectors.toList());
    }

    public String toString(){
        return books.size() + " books found, " + books.toString();
    }
}
