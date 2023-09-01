package com.project.test.securitygateway.securityJwt.component;

import com.project.test.securitygateway.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Log4j2
@Component
public class Encoder implements PasswordEncoder {

    @Value("${springbootwebfluxjjwt.password.encoder.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.password.encoder.iteration}")
    private Integer iteration;

    @Value("${springbootwebfluxjjwt.password.encoder.keylength}")
    private Integer keylength;

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            log.error("------ ERRROR -> Encoder");
            log.error(ex.getMessage());
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
