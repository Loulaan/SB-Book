package controller;

import dto.payload.request.SaveBookRequestDTO;
import lombok.RequiredArgsConstructor;
import model.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/books/")
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/search?option={searchOption}")
    public ResponseEntity<List<Book>> searchBooks(@PathVariable String searchOption, @RequestBody String query) {
        return ResponseEntity.ok(bookService.searchBook(searchOption, query));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getInfoAboutBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getInformationAboutBook(id));
    }

    @PostMapping(value = "/upload")
    public void uploadBook(HttpServletRequest request, @RequestBody SaveBookRequestDTO data) throws IOException {
        bookService.saveBook(request, data);
    }
}
