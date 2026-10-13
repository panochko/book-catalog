package ru.itmo.catalog.dto;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;
import ru.itmo.catalog.domain.Book;

import java.math.BigDecimal;

/**
 * Параметры фильтрации каталога; незаполненные параметры игнорируются.
 *
 * @param title    подстрока названия без учёта регистра
 * @param author   подстрока автора без учёта регистра
 * @param genre    точный жанр без учёта регистра
 * @param minPrice минимальная цена
 * @param maxPrice максимальная цена
 * @param yearFrom минимальный год издания
 * @param yearTo   максимальный год издания
 */
public record BookFilter(
        String title,
        String author,
        String genre,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Integer yearFrom,
        Integer yearTo) {

    /**
     * Собирает спецификацию из заполненных параметров.
     *
     * @return спецификация для {@code findAll}
     */
    public Specification<Book> toSpecification() {
        return Specification.allOf(
                contains("title", title),
                contains("author", author),
                equalsIgnoreCase("genre", genre),
                range("price", minPrice, maxPrice),
                range("publicationYear", yearFrom, yearTo));
    }

    private static Specification<Book> contains(String field, String value) {
        return blank(value) ? null
                : (root, query, cb) -> cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private static Specification<Book> equalsIgnoreCase(String field, String value) {
        return blank(value) ? null
                : (root, query, cb) -> cb.equal(cb.lower(root.get(field)), value.toLowerCase());
    }

    private static <T extends Comparable<? super T>> Specification<Book> range(String field, T from, T to) {
        return from == null && to == null ? null : (root, query, cb) -> {
            Path<T> path = root.get(field);
            return cb.and(
                    from == null ? cb.conjunction() : cb.greaterThanOrEqualTo(path, from),
                    to == null ? cb.conjunction() : cb.lessThanOrEqualTo(path, to));
        };
    }

    private static boolean blank(String value) {
        return value == null || value.isBlank();
    }
}
