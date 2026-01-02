package com.thedurodola.ekolo.util;

import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;


public class Mutator {

    public static void mutate(RegisterUserAccountRequest request) {
        request.setLastName(request.getLastName().toLowerCase());
        request.setFirstName(request.getFirstName().toLowerCase());
        request.setUsername(request.getUsername().toLowerCase());
        request.setEmail(request.getEmail().toLowerCase());
    }
}
