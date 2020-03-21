package service;

import exception.ApiException.UserNotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;

public interface AdminService {
    void changeAccountRole(Long id, Collection<String> newRoles) throws UserNotFoundException, RoleNotFoundException;
}
