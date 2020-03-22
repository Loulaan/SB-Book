package controller;

import dto.payload.response.RandomBooksResponseDTO;
import lombok.RequiredArgsConstructor;
import model.BookUploadWrapper;
import model.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;

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

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public void uploadBook(@ModelAttribute("file") BookUploadWrapper request) throws IOException {
        bookService.saveBook(request);
    }

    @GetMapping(value = "/random")
    public ResponseEntity<RandomBooksResponseDTO> getRandomFourBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }
}
