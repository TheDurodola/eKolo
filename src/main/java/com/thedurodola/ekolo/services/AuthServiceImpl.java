package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.models.enums.Authority;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import com.thedurodola.ekolo.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.thedurodola.ekolo.util.Validator.validate;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserAccounts userAccounts;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(UserAccounts userAccounts) {
        this.userAccounts = userAccounts;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public RegisterUserAccountResponse registerTierOneUser(RegisterUserAccountRequest request) {
        validate(request);
        log.info("Inputs validated for user account with username '{}'", request.getUsername());

        UserAccount userAccount = modelMapper.map(request,UserAccount.class);
        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(Authority.TIER1);
        userAccount.setAuthorities(authorities);
        userAccounts.save(userAccount);
        log.info("User account with username '{}' has been registered", request.getUsername());
        return modelMapper.map(userAccount,RegisterUserAccountResponse.class);
    }

}
