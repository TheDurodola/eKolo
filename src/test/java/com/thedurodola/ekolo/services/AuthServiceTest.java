package com.thedurodola.ekolo.services;

import com.thedurodola.ekolo.data.models.UserAccount;
import com.thedurodola.ekolo.data.repositories.UserAccounts;
import com.thedurodola.ekolo.dtos.requests.RegisterUserAccountRequest;
import com.thedurodola.ekolo.dtos.responses.RegisterUserAccountResponse;
import com.thedurodola.ekolo.exceptions.*;
import com.thedurodola.ekolo.proxy.cloud.CloudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccounts userAccounts;

    @Mock
    private CloudService cloudService;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterUserAccountRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterUserAccountRequest();
        request.setEmail("johndoe@gmail.com");
        request.setUsername("johndoe");
        request.setPassword("Password123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setGender("M");
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
        RegisterUserAccountResponse register = authService.registerTierOneUser(request);
        verify(userAccounts,  Mockito.times(1)).save(Mockito.any(UserAccount.class));
    }

    @Test
    void thatMethodThrowsExceptionWhenAnNullFirstnameIsSent(){
        request.setFirstName(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullLastNameIsSent(){
        request.setLastName(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullDateOfBirthIsSent(){
        request.setDateOfBirth(null);
        assertThatThrownBy(()  -> authService.registerTierOneUser(request)).isInstanceOf(InvalidDateOfBirthException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullProfilePictureIsSent(){
        request.setProfilePicture(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidProfilePictureException.class);
    }


    @Test
    void thatMethodThrowsExceptionWhenANullEmailIsSent(){
        request.setEmail(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullUsernameIsSent(){
        request.setUsername(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
    }


    @Test
    void thatMethodThrowsExceptionWhenANullPasswordIsSent(){
        request.setPassword(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatMethodThrowsExceptionWhenANullGenderIsSent(){
        request.setGender(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidGenderException.class);
    }

    @Test
    void thatMethodPasswordMustBeMoreThanSixDigits(){
        request.setPassword("Passw");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfPasswordDoesntHaveAUpperCaseCharacter(){
        request.setPassword("password123");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfPasswordDoesntHaveANumberCharacter(){
        request.setPassword("Password");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfPasswordConsistOfOnlySpace(){
        request.setPassword("       ");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    void thatExceptionIsThrownIfUsernameIsLessThan4Characters(){
        request.setUsername("jon");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
    }

    @Test
    void thatExceptionIsThrownWhenAUsernameWithAnInvalidCharacter(){
        request.setUsername("john*onestar");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john==onestar");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john#onestar");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john$%5onestar");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("#johnonestar");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidUsernameException.class);
        request.setUsername("john_boj");
        assertDoesNotThrow(()-> authService.registerTierOneUser(request));
    }


    @Test
    void thatUserMustBeAbove12YearsOfAge(){
        request.setDateOfBirth(LocalDate.now().minusYears(11));
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidDateOfBirthException.class);
        request.setDateOfBirth(LocalDate.now().minusYears(12));
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidDateOfBirthException.class);
    }

    @Test
    void thatUserMustBeBelow100YearsOfAge(){
        request.setDateOfBirth(LocalDate.now().minusYears(110));
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidDateOfBirthException.class);
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
        assertThatThrownBy(()->  authService.registerTierOneUser(request)).isInstanceOf(InvalidProfilePictureException.class);
    }

    @Test
    void thatFirstnameCannotConsistOfDigits(){
        request.setFirstName("John1");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void thatLastnameCannotConsistOfDigits(){
        request.setLastName("John2");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    void thatLastnameCanContainHyphen(){
        request.setLastName("Adeniyi-Oso");
        assertDoesNotThrow(()-> authService.registerTierOneUser(request));
    }

    @Test
    void testThatGenderCannotBeNull(){
        request.setGender(null);
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidGenderException.class);
    }

    @Test
    void thatGenderCannotBeEmpty(){
        request.setGender(" ");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidGenderException.class);
    }

    @Test
    void thatEmailMustContainAtSign(){
        request.setEmail("bolajidurodolagmail.com");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidEmailException.class);
    }
    @Test
    void thatEmailCannotContainDoubleAtSign(){
        request.setEmail("bolajidurodola@@gmail.com");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void thatEmailCannotBeEmpty(){
        request.setEmail("bolajidurodola@gmail");
        assertThatThrownBy(()-> authService.registerTierOneUser(request)).isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void thatLastnameIsUpdateToLowercaseBeforeBeenSavedInTheDatabase(){
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        authService.registerTierOneUser(request);
        verify(userAccounts).save(captor.capture());
        UserAccount user = captor.getValue();
        assertThat(user.getLastName()).isEqualTo("doe");
    }

    @Test
    void thatFirstnameIsUpdateToLowercaseBeforeBeenSavedInTheDatabase(){
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        authService.registerTierOneUser(request);
        verify(userAccounts).save(captor.capture());
        UserAccount user = captor.getValue();
        assertThat(user.getFirstName()).isEqualTo("john");
    }

    @Test
    void thatUsernameIsUpdateToLowercaseBeforeBeenSavedInTheDatabase(){
        request.setUsername("JOHN");
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        authService.registerTierOneUser(request);
        verify(userAccounts).save(captor.capture());
        UserAccount user = captor.getValue();
        assertThat(user.getUsername()).isLowerCase();
    }

    @Test
    void thatEmailIsUpdateToLowercaseBeforeBeenSavedInTheDatabase(){
        request.setEmail("BOLAJIdurodola@gmail.com");
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        authService.registerTierOneUser(request);
        verify(userAccounts).save(captor.capture());
        UserAccount user = captor.getValue();
        assertThat(user.getEmail()).isLowerCase();
    }

    @Test
    void thatPasswordIsHashed(){
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        authService.registerTierOneUser(request);
        verify(userAccounts).save(captor.capture());
        UserAccount user = captor.getValue();
        assertThat(user.getPassword()).isNotEqualTo(request.getPassword());
        assertThat(user.getPassword().length()).isNotEqualTo(10);
    }

    @Test
    void thatProfilePictureWasUploadedToTheCloud(){
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        authService.registerTierOneUser(request);
        verify(userAccounts).save(captor.capture());
        verify(userAccounts, Mockito.times(1)).save(captor.capture());
        UserAccount user = captor.getValue();
        assertThat(user.getProfilePictureUrl()).isNotBlank();
    }

}