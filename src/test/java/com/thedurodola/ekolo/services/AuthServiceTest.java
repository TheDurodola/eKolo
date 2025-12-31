package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import com.thedurodola.ekolo.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;


import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccounts userAccounts;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterUserAccountRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterUserAccountRequest();
        request.setEmail("johndoe@gmail.com");
        request.setUsername("johndoe");
        request.setPassword("password");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setDateOfBirth(LocalDate.of(1980, 1, 1));
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "vacation.png",
                MediaType.IMAGE_PNG_VALUE,
                "some-binary-data".getBytes()
        );
        request.setProfilePicture(file);

    }


    @Test
    void thatMethodSavesAUserAccount() {
        when(userAccounts.save(Mockito.any(UserAccount.class))).thenReturn(Mockito.mock(UserAccount.class));
        RegisterUserAccountResponse register = authService.register(request);
        verify(userAccounts,  Mockito.times(1)).save(Mockito.any(UserAccount.class));
    }

    @Test
    void thatMethodThrowsExceptionWhenAnInvalidFirstnameIsSent(){
        request.setFirstName(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidNameException.class);

    }





}