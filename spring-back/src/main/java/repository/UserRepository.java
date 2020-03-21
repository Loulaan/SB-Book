package repository;

import model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String name);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email=:email")
    boolean isEmailAlreadyExist(@Param(value = "email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userName=:userName")
    boolean isUsernameAlreadyExist(@Param(value = "userName") String userName);

    Optional<User> findByEmail(String email);
}
