package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccounts userAccounts;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUserSuccessfully() {
        RegisterUserAccountRequest request = new RegisterUserAccountRequest();
        request.setUsername("john_doe");
        request.setPassword("passwordqwerty123");
        request.setEmail("johndoe@gmail.com");
        request.setGender("MALE");
        request.setFirstName("John");
        request.setLastName("Doe");


        MockMultipartFile mockFile = new MockMultipartFile(
                "profilePicture",
                "avatar.jpg",
                "image/jpeg",
                "some-image-bytes".getBytes()
        );
        request.setProfilePicture(mockFile);


    }
}