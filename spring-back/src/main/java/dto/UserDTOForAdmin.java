package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTOForAdmin {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Date created;
}
