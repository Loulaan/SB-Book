package repository;

import model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);

    @Query("SELECT u FROM Book u WHERE u.title LIKE CONCAT('%',?1,'%')")
    Collection<Book> findByTitle(String title);

    @Query("SELECT u FROM Book u WHERE u.author LIKE CONCAT('%',?1,'%')")
    Collection<Book> findAllByAuthor(String authorName);

    @Query("SELECT u FROM Book u WHERE u.publishingHouse LIKE CONCAT('%',?1,'%')")
    Collection<Book> findAllByPublishingHouse(String publishingHouse);
}
