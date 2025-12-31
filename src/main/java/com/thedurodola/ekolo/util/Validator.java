package com.thedurodola.ekolo.util;

import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.exceptions.*;

public  class Validator {

    public static void validate(RegisterUserAccountRequest request) {
        validateNames(request);
        validateEmail(request);
        validatePassword(request);
        validateGender(request);
        validateUsername(request);
        validateDateOfBirth(request);
        validateProfilePicture(request);
    }

    private static void validateNames(RegisterUserAccountRequest request) {
        validateName(request.getFirstName(), "Firstname field cannot be null or empty");
        validateName(request.getLastName(), "Lastname field cannot be null or empty");
    }

    private static void validateName(String request, String message) {
        if (request == null || request.isBlank()) {
            throw new InvalidNameException(message);
        }
    }

    private static void validateProfilePicture(RegisterUserAccountRequest request) {
        if (request.getProfilePicture() == null || request.getProfilePicture().isEmpty()){
            throw new InvalidProfilePictureException("The Profile Picture field cannot be null");
        }
    }

    private static void validateDateOfBirth(RegisterUserAccountRequest request) {
        if (request.getDateOfBirth() == null){
            throw new InvalidDateOfBirthException("The Date of Birth field cannot be null");
        }
    }

    private static void validateUsername(RegisterUserAccountRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()){
            throw new InvalidUsernameException("Username field cannot be null or empty");
        }
        if (request.getUsername().length() < 4){
            throw new InvalidUsernameException("Username length should be at least 4");
        }
    }

    private static void validateGender(RegisterUserAccountRequest request) {
        if (request.getGender() == null || request.getGender().isBlank()){
            throw new InvalidGenderException("Gender field cannot be null or empty");
        }
    }

    private static void validateEmail(RegisterUserAccountRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()){
            throw new InvalidEmailException("Email Address field cannot be null or empty");
        }
    }

    private static void validatePassword(RegisterUserAccountRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()){
            throw new InvalidPasswordException("Password field cannot be null or empty");
        }
        if (request.getPassword().length() < 6){
            throw new InvalidPasswordException("Password length cannot be less than 6 characters");
        }

        if (request.getPassword().chars()
                .noneMatch(Character::isUpperCase)){
            throw new InvalidPasswordException("Password must contain at least one uppercase character");
        }
        if (request.getPassword().chars()
                .noneMatch(Character::isDigit)){
            throw new InvalidPasswordException("Password must contain at least one digit character");
        }
    }
}
