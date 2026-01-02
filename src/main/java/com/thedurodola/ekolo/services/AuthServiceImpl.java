package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.models.enums.Authority;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import com.thedurodola.ekolo.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.thedurodola.ekolo.util.Mutator.mutate;
import static com.thedurodola.ekolo.util.Validator.validate;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserAccounts userAccounts;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserAccounts userAccounts) {
        this.userAccounts = userAccounts;
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public RegisterUserAccountResponse registerTierOneUser(RegisterUserAccountRequest request) {
        validate(request);
        log.info("Inputs validated for user account with username '{}'", request.getUsername());
        mutate(request);
        log.info("user '{}'' input have been prepared for Database saving", request.getUsername());
        UserAccount mapRequestToUserAccount = mapRequestToUserAccount(request);
        UserAccount userAccount = userAccounts.save(mapRequestToUserAccount);
        log.info("User account with username '{}' has been registered", request.getUsername());
        return modelMapper.map(mapRequestToUserAccount,RegisterUserAccountResponse.class);
    }

    private @NonNull UserAccount mapRequestToUserAccount(RegisterUserAccountRequest request) {
        UserAccount userAccount = modelMapper.map(request,UserAccount.class);
        userAccount.setAuthority(Authority.TIER1);
        userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        return userAccount;
    }

}
