package com.bilgeadam.service;

import com.bilgeadam.converter.AuthConverter;
import com.bilgeadam.repository.IAuthRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private IAuthRepository authRepository;

    @Mock
    private AuthConverter authConverter;

}