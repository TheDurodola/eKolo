package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;


public interface AuthService {
    RegisterUserAccountResponse registerTierOneUser(RegisterUserAccountRequest request);
}
