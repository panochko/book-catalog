package ru.itmo.catalog.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.catalog.dto.BookFilter;
import ru.itmo.catalog.dto.BookRequest;
import ru.itmo.catalog.dto.BookResponse;
import ru.itmo.catalog.service.BookService;

/**
 * REST-контроллер каталога книг.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    /**
     * Возвращает книги с фильтрацией, пагинацией и сортировкой.
     *
     * @param filter   параметры фильтрации из query-строки
     * @param pageable параметры {@code page}, {@code size}, {@code sort}
     * @return страница книг
     */
    @GetMapping
    public Page<BookResponse> search(BookFilter filter, @PageableDefault(sort = "id") Pageable pageable) {
        return service.search(filter, pageable);
    }

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param id идентификатор
     * @return книга
     */
    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    /**
     * Создаёт книгу.
     *
     * @param request данные книги
     * @return созданная книга
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody BookRequest request) {
        return service.create(request);
    }

    /**
     * Полностью обновляет книгу.
     *
     * @param id      идентификатор
     * @param request новые данные
     * @return обновлённая книга
     */
    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return service.update(id, request);
    }

    /**
     * Удаляет книгу.
     *
     * @param id идентификатор
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
