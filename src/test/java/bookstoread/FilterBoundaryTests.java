package bookstoread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import bookstoread.filter.BookFilter;

import static org.assertj.core.api.Assertions.assertThat;

// TODO p.086-7 - common behaviour (me not understand within this context...)
public interface FilterBoundaryTests {
    BookFilter get();

    @Test
    @DisplayName("should not fail for null book")
    default void nullTest(){
        assertThat(get().apply(null)).isFalse();
    }

}
