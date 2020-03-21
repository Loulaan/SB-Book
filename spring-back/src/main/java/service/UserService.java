package service;

import exception.ApiException.UserNotFoundException;
import model.entity.User;
import dto.payload.request.RegistrationRequestDTO;

import java.util.List;

public interface UserService {

    User createUser(RegistrationRequestDTO request);

    List<User> getAllUsers();

    User findByUserName(String username) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    void delete(Long id) throws UserNotFoundException;

    User save(User user);

    User getUserIfAccountActivated(String email) throws UserNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;

    void changePassword(String newPassword, User user);

    void changePassword(String newPassword, String userName) throws UserNotFoundException;

    boolean isPasswordValid(String encodedPassword, String currentPassword);
}
