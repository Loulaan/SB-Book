package dto.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.entity.Book;

import java.util.List;

@Data
@AllArgsConstructor
public class RandomBooksResponseDTO {
    List<Book> bookList;
}
