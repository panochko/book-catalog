package ru.itmo.catalog.dto;

import ru.itmo.catalog.domain.Book;

import java.math.BigDecimal;

/**
 * Представление книги в ответе API.
 *
 * @param id              идентификатор
 * @param title           название
 * @param author          автор
 * @param isbn            ISBN
 * @param genre           жанр
 * @param publicationYear год издания
 * @param price           цена
 * @param stock           остаток на складе
 */
public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        String genre,
        Integer publicationYear,
        BigDecimal price,
        int stock) {

    /**
     * Строит ответ из сущности.
     *
     * @param b сущность книги
     * @return DTO для клиента
     */
    public static BookResponse from(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(),
                b.getGenre(), b.getPublicationYear(), b.getPrice(), b.getStock());
    }
}
