package com.hronosf.dto.responses;

import com.hronosf.model.entity.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hronosf.model.entity.Roles;
import com.hronosf.model.enums.AccountStatus;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class RegistrationResponseDTO {

    private Long id;
    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
    protected Date lastLoginTime;
    private List<Roles> roles;
    private AccountStatus accountStatus;
    private boolean isActivated;


    public RegistrationResponseDTO(Person user) {
        BeanUtils.copyProperties(user, this);
    }

}
