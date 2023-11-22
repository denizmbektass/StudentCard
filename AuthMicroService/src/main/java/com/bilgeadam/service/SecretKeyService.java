package com.bilgeadam.service;

import com.bilgeadam.dto.request.LoginQrRequestDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.exceptions.AuthServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.repository.ISecretKeyRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.entity.SecretKey;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class SecretKeyService extends ServiceManager<SecretKey,String> {
    private final JwtTokenManager jwtTokenManager;
    private final ISecretKeyRepository secretKeyRepository;
    private final AuthService authService;

    public SecretKeyService(JwtTokenManager jwtTokenManager,
                            ISecretKeyRepository secretKeyRepository,
                            AuthService authService) {
        super(secretKeyRepository);
        this.jwtTokenManager = jwtTokenManager;
        this.secretKeyRepository = secretKeyRepository;
        this.authService = authService;
    }


    public String generateSecretKey(String authId) {
        String secretKey = CodeGenerator.generateSecretKey();
        Optional<SecretKey> optionalSecretKey = secretKeyRepository.findByAuthId(authId);
        if (optionalSecretKey.isEmpty()) {
            save(SecretKey.builder()
                    .authId(authId)
                    .secretKey(secretKey)
                    .build());
        } else {
            optionalSecretKey.get().setSecretKey(secretKey);
            update(optionalSecretKey.get());
        }
        return secretKey;
    }

    public String generateTotpCode(String token) {
        String authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> {
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        });
        SecretKey secretKey = secretKeyRepository.findByAuthId(authId).orElseThrow(() -> {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        });
        return CodeGenerator.getTOTPCode(secretKey.getSecretKey());
    }

    public String generateQRCode(String token)
            throws WriterException, IOException {
        String authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> {
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        });
        String barCodeData = "";
        String secretKey = "";
        Optional<SecretKey> optionalSecretKey = secretKeyRepository.findByAuthId(authId);
        if(optionalSecretKey.isEmpty()){
            secretKey = generateSecretKey(authId);
            barCodeData = CodeGenerator.getGoogleAuthenticatorBarCode(secretKey,authId);
        }else{
            secretKey = optionalSecretKey.get().getSecretKey();
        }
        barCodeData = CodeGenerator.getGoogleAuthenticatorBarCode(secretKey,authId);
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
                200, 200);
        /*
        try (FileOutputStream out = new FileOutputStream("C:\\Users\\Erguv\\OneDrive\\Desktop\\example.png")) {
                MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
         */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }

    public LoginResponseDto loginWithQrCode(LoginQrRequestDto dto){
        String authId = jwtTokenManager.getIdFromToken(dto.getToken()).orElseThrow(() -> {
            throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);
        });
        SecretKey secretKey = secretKeyRepository.findByAuthId(authId).orElseThrow(()->{
            throw new AuthServiceException(ErrorType.SECRET_KEY_NOT_FOUND);
        });
        String totpCode = CodeGenerator.getTOTPCode(secretKey.getSecretKey());
        if(totpCode.equals(dto.getUserKey())) {
            Auth auth = authService.findById(authId).orElseThrow(() -> {
                throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
            });
            if (auth.getStatus().equals(EStatus.DELETED))
                throw new AuthServiceException(ErrorType.USER_DELETED);
            List<ERole> role = auth.getRole();
            List<String> roles = role.stream().map(x -> x.name()).toList();
            Optional<String> newToken = jwtTokenManager.createToken(auth.getAuthId(), roles, auth.getStatus(), auth.getEmail(), auth.getUserId());
            String authStatus = auth.getStatus().toString();
            return LoginResponseDto.builder().token(newToken.get()).message("Login Successfully").role(roles).status(authStatus).build();
        }else{
            throw new AuthServiceException(ErrorType.CODE_NO_VALID);
        }

    }



}