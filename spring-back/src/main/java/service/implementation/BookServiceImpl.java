package service.implementation;

import dto.payload.response.RandomBooksResponseDTO;
import exception.ApiException.ApiException;
import lombok.RequiredArgsConstructor;
import model.BookUploadWrapper;
import model.entity.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import repository.BooksRepository;
import service.BookService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;

    @Override
    public Book getInformationAboutBook(Long id) {
        return booksRepository.findById(id).orElseThrow(ApiException::new);
    }

    @Override
    public RandomBooksResponseDTO searchBook(String query) {

        List<Book> bookCollection = (ArrayList<Book>) booksRepository.findAllByAuthor(query);

        if (bookCollection.isEmpty()) {
            bookCollection = (ArrayList<Book>) booksRepository.findByTitle(query);
        }

        if (bookCollection.isEmpty()) {
            bookCollection = (ArrayList<Book>) booksRepository.findAllByPublishingHouse(query);
        }

        return new RandomBooksResponseDTO(bookCollection);
    }

    @Transactional
    @Override
    public void saveBook(BookUploadWrapper request) throws IOException {
        Book newBook = new Book();
        BeanUtils.copyProperties(request, newBook);

        for (CommonsMultipartFile file : request.getFiles()) {
            String path = "src/main/resources/images/" + newBook.getTitle();
        }
    }

    @Override
    public RandomBooksResponseDTO getAll() {
        List<Book> books = booksRepository.findAll();
        Collections.shuffle(books);
        return new RandomBooksResponseDTO(books.subList(0, 4));
    }
}
