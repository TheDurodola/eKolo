package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import com.thedurodola.ekolo.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccounts userAccounts;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterUserAccountRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterUserAccountRequest();
        request.setEmail("johndoe@gmail.com");
        request.setUsername("johndoe");
        request.setPassword("Password123");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setGender("MALE");
        request.setDateOfBirth(LocalDate.of(1980, 1, 1));
        byte[] jpegSignature = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        MockMultipartFile validImageFile = new MockMultipartFile(
                "profilePicture",
                "test-image.jpg",
                "image/jpeg",
                jpegSignature
        );
        request.setProfilePicture(validImageFile);

    }


    @Test
    void thatMethodSavesAUserAccount() {
        when(userAccounts.save(Mockito.any(UserAccount.class))).thenReturn(Mockito.mock(UserAccount.class));
        RegisterUserAccountResponse register = authService.register(request);
        verify(userAccounts,  Mockito.times(1)).save(Mockito.any(UserAccount.class));
    }

    @Test
    void thatMethodThrowsExceptionWhenAnNullFirstnameIsSent(){
        request.setFirstName(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullLastNameIsSent(){
        request.setLastName(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullDateOfBirthIsSent(){
        request.setDateOfBirth(null);
        assertThatThrownBy(()  -> authService.register(request)).isInstanceOf(InvalidDateOfBirthException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullProfilePictureIsSent(){
        request.setProfilePicture(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidProfilePictureException.class);
    }


    @Test
    void thatMethodThrowsExceptionWhenANullEmailIsSent(){
        request.setEmail(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullUsernameIsSent(){
        request.setUsername(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
    }


    @Test
    void thatMethodThrowsExceptionWhenANullPasswordIsSent(){
        request.setPassword(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullGenderIsSent(){
        request.setGender(null);
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidGenderException.class);
    }

    @Test
    void thatMethodPasswordMustBeMoreThanSixDigits(){
        request.setPassword("Passw");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfPasswordDoesntHaveAUpperCaseCharacter(){
        request.setPassword("password123");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfPasswordDoesntHaveANumberCharacter(){
        request.setPassword("Password");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfPasswordConsistOfOnlySpace(){
        request.setPassword("       ");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfUsernameIsLessThan4Characters(){
        request.setUsername("jon");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
    }

    @Test
    void thatExceptionIsThrownWhenAUsernameWithAnInvalidCharacter(){
        request.setUsername("john*onestar");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john==onestar");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john#onestar");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john$%5onestar");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("#johnonestar");
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john_boj");
        assertDoesNotThrow(()-> authService.register(request));
    }


    @Test
    void thatUserMustBeAbove12YearsOfAge(){
        request.setDateOfBirth(LocalDate.now().minusYears(11));
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidDateOfBirthException.class);
        request.setDateOfBirth(LocalDate.now().minusYears(12));
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidDateOfBirthException.class);
    }

    @Test
    void thatUserMustBeBelow100YearsOfAge(){
        request.setDateOfBirth(LocalDate.now().minusYears(110));
        assertThatThrownBy(()-> authService.register(request)).isInstanceOf(InvalidDateOfBirthException.class);
    }


    @Test
    void thatOnlyImageFileAreAccepted(){
        byte[] pdfSignature = new byte[]{ 0x25, 0x50, 0x44, 0x46, 0x2D };
        MockMultipartFile pdfFile = new MockMultipartFile(
                "profilePicture",
                "contract.pdf",
                "application/pdf",
                pdfSignature
        );
        request.setProfilePicture(pdfFile);
    }








}