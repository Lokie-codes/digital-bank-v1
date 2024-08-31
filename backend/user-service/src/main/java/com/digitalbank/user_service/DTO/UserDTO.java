package com.digitalbank.user_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
}
