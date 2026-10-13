package ru.itmo.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.itmo.catalog.domain.Book;

/**
 * Репозиторий книг с поддержкой динамической фильтрации через {@link org.springframework.data.jpa.domain.Specification}.
 */
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
