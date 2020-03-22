package repository;

import model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Collection<Book> findAllByTitle(String title);

    Collection<Book> findAllByAuthor(String authorName);

    Collection<Book> findAllByPublishingHouse(String publishingHouse);
}
