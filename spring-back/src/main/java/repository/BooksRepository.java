package repository;

import model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);

    Collection<Book> findAllByTitle(String title);

    Collection<Book> findAllByAuthor(String authorName);

    Collection<Book> findAllByPublishingHouse(String publishingHouse);
}
