package service.implementation;

import model.entity.User;
import dto.payload.request.RegistrationRequestDTO;
import exception.ApiException.AccountNotActivatedException;
import exception.ApiException.UserNotFoundException;
import exception.ExceptionFactoryHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.entity.Role;
import model.enums.AccountStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.RoleRepository;
import repository.UserRepository;
import service.UserService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ExceptionFactoryHelper exceptionFactoryHelper;

    @Override
    public User createUser(RegistrationRequestDTO request) {
        throwIfNewUserDataCollisionWithDbData(request.getUserName(), request.getEmail());
        User newUser = new User(request);

        Role roleUser = roleRepository.findByName("ROLE_USER").get();
        newUser.setRoles(Collections.singletonList(roleUser));

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setStatus(AccountStatus.ACTIVE);
        newUser.setCreatedTimestamp(new Date());
        newUser.setUpdatedTimestamp(new Date());
        newUser.setEnabled(false);
        return newUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String username) throws UserNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() ->
                new UserNotFoundException(exceptionFactoryHelper.buildMessage("error.user.not.found")));
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(exceptionFactoryHelper.buildMessage("error.user.not.found")));
    }

    @Override
    public void delete(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(exceptionFactoryHelper.buildMessage("error.user.not.found")));
        user.setEnabled(false);
        user.setStatus(AccountStatus.DELETED);
        user.setDeletedTimestamp(new Date());
        save(user);
    }

    @Override
    public User getUserIfAccountActivated(String email) throws UserNotFoundException {
        User userFromBase = findUserByEmail(email);
        if (!userFromBase.isEnabled()) {
            throw new AccountNotActivatedException(exceptionFactoryHelper.buildMessage("errors.account.not.activated"));
        }
        return userFromBase;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(exceptionFactoryHelper.buildMessage("error.user.not.found")));
    }

    @Override
    public void changePassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setStatus(AccountStatus.DELETED);
        save(user);
    }

    @Override
    public void changePassword(String newPassword, String userName) throws UserNotFoundException {
        changePassword(newPassword, findByUserName(userName));
    }

    @Override
    public boolean isPasswordValid(String encodedPassword, String currentPassword) {
        return BCrypt.checkpw(currentPassword, encodedPassword);
    }

    private void throwIfNewUserDataCollisionWithDbData(String userName, String email) throws BadCredentialsException {
        if (userRepository.isEmailAlreadyExist(email)) {
            throw new BadCredentialsException(exceptionFactoryHelper.buildMessage("error.credentials.email.in.use"));
        }
        if (userRepository.isUsernameAlreadyExist(userName)) {
            throw new BadCredentialsException(exceptionFactoryHelper.buildMessage("error.credentials.username.in.use"));
        }
    }
}
