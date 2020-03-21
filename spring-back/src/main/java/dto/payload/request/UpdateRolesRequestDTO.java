package dto.payload.request;

import lombok.Data;

import java.util.Collection;

@Data
public class UpdateRolesRequestDTO {

    Collection<String> newRoles;
}
