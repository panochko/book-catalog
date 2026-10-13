package ru.itmo.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Книга в каталоге.
 */
@Entity
@Table(name = "books", indexes = {
        @Index(name = "idx_books_author", columnList = "author"),
        @Index(name = "idx_books_genre", columnList = "genre")
})
@Getter
@Setter
@NoArgsConstructor
public class Book {

    /** Идентификатор. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название. */
    @Column(nullable = false)
    private String title;

    /** Автор. */
    @Column(nullable = false)
    private String author;

    /** Уникальный ISBN. */
    @Column(unique = true, length = 20)
    private String isbn;

    /** Жанр. */
    private String genre;

    /** Год издания. */
    private Integer publicationYear;

    /** Цена. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** Остаток на складе. */
    @Column(nullable = false)
    private int stock;
}
