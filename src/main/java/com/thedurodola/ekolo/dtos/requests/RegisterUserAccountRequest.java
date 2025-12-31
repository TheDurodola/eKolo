package com.thedurodola.ekolo.dtos.requests;

import com.thedurodola.ekolo.data.models.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class RegisterUserAccountRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private MultipartFile profilePicture;
    private String gender;
}
