package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import org.springframework.stereotype.Service;


public interface AuthService {
    RegisterUserAccountResponse register(RegisterUserAccountRequest request);
}
