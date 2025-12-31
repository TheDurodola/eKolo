package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccounts userAccounts;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void ThatUserIsSavedSuccessfullyInTheDB() {
        RegisterUserAccountRequest request = new RegisterUserAccountRequest();

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(request.getUsername());
        userAccount.setPassword(request.getPassword());
        userAccount.setEmail(request.getEmail());
        userAccount.setFirstName(request.getFirstName());
        userAccount.setLastName(request.getLastName());
        userAccount.setDateOfBirth(request.getDateOfBirth());
        userAccount.setImageUrl("www.cloudinary.com");
        when(userAccounts.save(Mockito.any(UserAccount.class))).thenReturn(userAccount);
        RegisterUserAccountResponse register = authService.register(request);
        verify(userAccounts,  Mockito.times(1)).save(Mockito.any(UserAccount.class));
    }


    @Test
    void testThatPasswordIsHashedBeforeBeingSaved((){
        RegisterUserAccountRequest request = new RegisterUserAccountRequest();
        UserAccount userAccount = new UserAccount();

        userAccount.setUsername(request.getUsername());
        userAccount.setPassword(request.getPassword());
        userAccount.setEmail(request.getEmail());
        userAccount.setFirstName(request.getFirstName());
        userAccount.setLastName(request.getLastName());
        userAccount.setDateOfBirth(request.getDateOfBirth());
        userAccount.setImageUrl("www.cloudinary.com");
        when(userAccounts.save(Mockito.any(UserAccount.class))).thenReturn(userAccount);
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);


    }



}