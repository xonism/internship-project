package com.twoday.internshipwarehouse.services;

import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService underTest;

    private final String username = "user";

    @Test
    void givenValidUsername_whenGetByUsername_thenUserIsReturned() {
        User expectedResult = new User(1, username, "password");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedResult));

        User actualResult = underTest.getByUsername(username);

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(userRepository).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void givenInvalidUsername_whenGetByUsername_thenUsernameNotFoundExceptionIsThrown() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        String actualMessage = catchThrowableOfType(() ->
                underTest.getByUsername(username), UsernameNotFoundException.class).getMessage();

        String expectedMessage = new UsernameNotFoundException(username).getMessage();

        assertThat(actualMessage).isNotNull().isEqualTo(expectedMessage);

        verify(userRepository).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }
}
