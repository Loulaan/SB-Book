package controller;

import dto.payload.request.UpdateRolesRequestDTO;
import exception.ApiException.UserNotFoundException;
import service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/admin/")
public class AdminController {

    private final AdminService adminService;

    @PostMapping(value = "update_roles/{id}")
    public ResponseEntity<String> changeRole(@PathVariable Long id, @RequestBody UpdateRolesRequestDTO request) throws UserNotFoundException, RoleNotFoundException {
        adminService.changeAccountRole(id, request.getNewRoles());
        return ResponseEntity.ok("User roles successfully updated.");
    }
}
