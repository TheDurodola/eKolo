package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.repositories.UserAccounts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccounts userAccounts;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUserSuccessfully() {


    }
}