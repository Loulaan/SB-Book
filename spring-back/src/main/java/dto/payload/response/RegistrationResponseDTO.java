package dto.payload.response;

import model.entity.Role;
import model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.AccountStatus;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class RegistrationResponseDTO {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
    private AccountStatus status;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private Date deletedTimestamp;
    private boolean isEnabled;

    public RegistrationResponseDTO(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
