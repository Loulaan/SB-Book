package service.implementation;

import exception.ApiException.RoleNotFoundException;
import exception.ApiException.UserNotFoundException;
import exception.ExceptionFactoryHelper;
import model.entity.Role;
import model.entity.User;
import service.UserService;
import lombok.RequiredArgsConstructor;
import repository.RoleRepository;
import service.AdminService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ExceptionFactoryHelper exceptionFactoryHelper;

    @Override
    public void changeAccountRole(Long id, Collection<String> rolesToSet) throws UserNotFoundException {
        User user = userService.findById(id);
        ArrayList<Role> newRoles = new ArrayList<>();
        ArrayList<String> failedToFindInBase = new ArrayList<>();

        for (String role : convertNames(rolesToSet)) {
            Optional<Role> optionalRole = roleRepository.findByName(role);
            if (optionalRole.isPresent()) {
                newRoles.add(optionalRole.get());
                continue;
            }
            failedToFindInBase.add(role);
        }
        user.setRoles(newRoles);
        userService.save(user);

        if (!failedToFindInBase.isEmpty()) {
            throw exceptionFactoryHelper.buildDetailedMessage(new RoleNotFoundException(),
                    String.join(",", failedToFindInBase));
        }
    }

    private List<String> convertNames(Collection<String> roleNames) {
        return roleNames.stream().map(role -> "ROLE_" + role.toUpperCase()).collect(Collectors.toList());
    }
}
