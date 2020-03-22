package service;

import dto.payload.request.SaveBookRequestDTO;
import model.entity.Book;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface BookService {

    Book getInformationAboutBook(Long id);

    List<Book> searchBook(String searchOption, String query);

    void saveBook(HttpServletRequest request, SaveBookRequestDTO data) throws IOException;
}
