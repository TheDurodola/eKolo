package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import com.thedurodola.ekolo.exceptions.InvalidNameException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccounts userAccounts;
    private ModelMapper modelMapper;

    @Override
    public RegisterUserAccountResponse register(RegisterUserAccountRequest request) {
        if (request.getFirstName() == null || request.getFirstName().isEmpty()){
            throw new InvalidNameException();
        }



        UserAccount userAccount = modelMapper.map(request,UserAccount.class);
        userAccounts.save(userAccount);
        return modelMapper.map(userAccount,RegisterUserAccountResponse.class);
    }
}
