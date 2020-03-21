package com.hronosf.dto.requests;

import com.hronosf.dto.APIRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePersonDetailsRequestDTO extends APIRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
}
