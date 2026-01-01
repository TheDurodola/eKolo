package com.thedurodola.ekolo.util;

import com.thedurodola.ekolo.data.models.enums.Gender;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Validator {

    private static final Tika tika = new Tika();
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg",
            "image/png"
    );

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
        validateName(request.getFirstName(), "Firstname");
        validateName(request.getLastName(), "Lastname");
    }

    private static void validateName(String request, String message) {
        if (request == null || request.isBlank()) {
            throw new InvalidNameException(message + " field cannot be null or empty");
        }
        if (request.chars().anyMatch(Character::isDigit)) {
            throw new InvalidNameException(message + " cannot contain digits");
        };
    }

    private static void validateProfilePicture(RegisterUserAccountRequest request)  {
        if (request.getProfilePicture() == null || request.getProfilePicture().isEmpty()){
            throw new InvalidProfilePictureException("The Profile Picture field cannot be null");
        }

        try {
            String fileType = tika.detect(request.getProfilePicture().getInputStream());

            if (!ALLOWED_MIME_TYPES.contains(fileType)) {
                log.warn("User {} attempted to upload a invalid file type: {}",request.getEmail(), fileType);
                throw new InvalidProfilePictureException("Invalid file type: " + fileType);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validateDateOfBirth(RegisterUserAccountRequest request) {
        if (request.getDateOfBirth() == null){
            throw new InvalidDateOfBirthException("The Date of Birth field cannot be null");
        }
        Period period = Period.between(request.getDateOfBirth(), LocalDate.now());
        if (period.getYears() <= 12){
            throw new InvalidDateOfBirthException("Must be older than 12 years old");
        }
        if (period.getYears() >= 100){
            throw new InvalidDateOfBirthException("Must be younger than 100 years old");
        }
    }

    private static void validateUsername(RegisterUserAccountRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()){
            throw new InvalidUsernameException("Username field cannot be null or empty");
        }
        if (request.getUsername().length() < 4){
            throw new InvalidUsernameException("Username length should be at least 4");
        }

        if (!request.getUsername().matches("^[a-zA-Z][a-zA-Z0-9_]{2,15}$")) {
            throw new InvalidUsernameException("Invalid Username");
        }

    }

    private static void validateGender(RegisterUserAccountRequest request) {
        if (request.getGender() == null || request.getGender().isBlank()){
            throw new InvalidGenderException("Gender field cannot be null or empty");
        }

        if (Arrays.stream(Gender.values())
                .anyMatch(g -> g.name().equalsIgnoreCase(request.getGender()))){
            throw new InvalidGenderException("Invalid Gender");
        };
    }

    private static void validateEmail(RegisterUserAccountRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()){
            throw new InvalidEmailException("Email Address field cannot be null or empty");
        }

        if (!request.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
            throw new InvalidEmailException("Invalid Email Address format");
        };

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
