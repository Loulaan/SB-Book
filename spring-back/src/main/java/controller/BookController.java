package controller;

import dto.payload.response.RandomBooksResponseDTO;
import lombok.RequiredArgsConstructor;
import model.BookUploadWrapper;
import model.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/books/")
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/search")
    public ResponseEntity<RandomBooksResponseDTO> searchBooks(@RequestParam("query") String query) {
        return ResponseEntity.ok(bookService.searchBook(query));
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
