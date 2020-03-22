package service.implementation;

import dto.payload.request.SaveBookRequestDTO;
import exception.ApiException.ApiException;
import lombok.RequiredArgsConstructor;
import model.entity.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BooksRepository;
import service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;

    @Override
    public Book getInformationAboutBook(Long id) {
        return booksRepository.findById(id).orElseThrow(ApiException::new);
    }

    @Override
    public List<Book> searchBook(String searchOption, String query) {
        List<Book> bookCollection = null;
        switch (searchOption) {
            case "author": {
                bookCollection = (ArrayList<Book>) booksRepository.findAllByAuthor(query);
                break;
            }
            case "title": {
                bookCollection = (ArrayList<Book>) booksRepository.findAllByTitle(query);
                break;
            }
            case "publishingHouse": {
                bookCollection = (ArrayList<Book>) booksRepository.findAllByPublishingHouse(query);
                break;
            }
            default:
                throw new ApiException();
        }
        return bookCollection;
    }

    @Transactional
    @Override
    public void saveBook(HttpServletRequest request, SaveBookRequestDTO data) throws IOException {
        Book newBook = new Book();
        BeanUtils.copyProperties(data, newBook);
        String path = "src/main/resources/images/" + newBook.getTitle();
        Files.readAllBytes(Paths.get(path));
        newBook.setImageUrl(path);
    }

}
