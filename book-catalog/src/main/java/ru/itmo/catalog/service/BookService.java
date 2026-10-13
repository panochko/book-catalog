package ru.itmo.catalog.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.catalog.domain.Book;
import ru.itmo.catalog.dto.BookFilter;
import ru.itmo.catalog.dto.BookRequest;
import ru.itmo.catalog.dto.BookResponse;
import ru.itmo.catalog.repository.BookRepository;

/**
 * Бизнес-логика каталога книг.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository repository;

    /**
     * Возвращает страницу книг, подходящих под фильтр.
     *
     * @param filter   параметры фильтрации
     * @param pageable параметры пагинации и сортировки
     * @return страница книг
     */
    public Page<BookResponse> search(BookFilter filter, Pageable pageable) {
        return repository.findAll(filter.toSpecification(), pageable).map(BookResponse::from);
    }

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param id идентификатор
     * @return книга
     * @throws EntityNotFoundException если книга не найдена
     */
    public BookResponse get(Long id) {
        return BookResponse.from(find(id));
    }

    /**
     * Создаёт книгу.
     *
     * @param request данные книги
     * @return созданная книга
     */
    @Transactional
    public BookResponse create(BookRequest request) {
        return BookResponse.from(repository.save(request.applyTo(new Book())));
    }

    /**
     * Полностью обновляет книгу.
     *
     * @param id      идентификатор
     * @param request новые данные
     * @return обновлённая книга
     * @throws EntityNotFoundException если книга не найдена
     */
    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        return BookResponse.from(request.applyTo(find(id)));
    }

    /**
     * Удаляет книгу.
     *
     * @param id идентификатор
     * @throws EntityNotFoundException если книга не найдена
     */
    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Book find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с id=" + id + " не найдена"));
    }
}
