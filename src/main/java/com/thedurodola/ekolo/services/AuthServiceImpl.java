package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccounts userAccounts;


    @Override
    public RegisterUserAccountResponse register(RegisterUserAccountRequest request) {
        ModelMapper modelMapper = new ModelMapper();

        UserAccount userAccount = modelMapper.map(request,UserAccount.class);
        userAccounts.save(userAccount);
        return modelMapper.map(userAccount,RegisterUserAccountResponse.class);
    }
}
