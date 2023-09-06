package com.bilgeadam.utility;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base32;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class CodeGenerator {

    public static  String generateCode(){
        String code = UUID.randomUUID().toString();
        String [] data = code.split("-");
        String newCode = "";
        for(String string:data){
            newCode+=string.charAt(0);
            newCode+=string.charAt(1);
        }
        return newCode;
    }

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String encodedSecretKey = new String(base32.encode(bytes)); //32 karaktere sağlamak için yaptık
        return encodedSecretKey;
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public static String getGoogleAuthenticatorBarCode(String secretKey, String account) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode("Bilgeadam Technology" + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode("Bilgeadam Technology", "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
