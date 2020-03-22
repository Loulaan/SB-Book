package service;

import dto.payload.response.RandomBooksResponseDTO;
import model.BookUploadWrapper;
import model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {

    Book getInformationAboutBook(Long id);

    RandomBooksResponseDTO searchBook(String query);

    void saveBook(BookUploadWrapper request) throws IOException;

    RandomBooksResponseDTO getAll();
}
