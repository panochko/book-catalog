package ru.itmo.catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ru.itmo.catalog.domain.Book;

import java.math.BigDecimal;

/**
 * Тело запроса на создание или полное обновление книги.
 *
 * @param title           название
 * @param author          автор
 * @param isbn            ISBN, уникальный
 * @param genre           жанр
 * @param publicationYear год издания
 * @param price           цена
 * @param stock           остаток на складе
 */
public record BookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @Size(max = 20) String isbn,
        String genre,
        @Min(0) Integer publicationYear,
        @NotNull @PositiveOrZero BigDecimal price,
        @PositiveOrZero int stock) {

    /**
     * Переносит значения запроса в сущность.
     *
     * @param book целевая сущность
     * @return та же сущность
     */
    public Book applyTo(Book book) {
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setPublicationYear(publicationYear);
        book.setPrice(price);
        book.setStock(stock);
        return book;
    }
}
