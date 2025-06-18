package com.fiapx.usermanagement.core.domain.services.ValidatePasswordUseCase;

import com.fiapx.usermanagement.core.application.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ValidatePasswordUseCase implements IValidatePasswordUseCase {

    private final String ALPHA_UPPER = "alpha-upper";
    private final String ALPHA_LOWER = "alpha-lower";
    private final String NUMERIC = "numeric";
    private final String SPECIAL = "special";

    @Value("${spring.application.security.password-rules-exempt}")
    private boolean exempt;

    @Value("${spring.application.security.password-min-length}")
    private int minLength;

    @Value("${spring.application.security.password-min-alpha-upper}")
    private int minAlphaUpper;

    @Value("${spring.application.security.password-min-alpha-lower}")
    private int minAlphaLower;

    @Value("${spring.application.security.password-min-numeric}")
    private int minNumeric;

    @Value("${spring.application.security.password-min-special}")
    private int minSpecial;

    @Value("${spring.application.security.password-special-chars}")
    private String validSpecialChars;

    public void execute(String password) {
        if(exempt) return;

        if(Objects.isNull(password) || password.length() < minLength)
            throw new ValidationException(String.format("Password must have at least %d characters.", minLength));

        Map<String, Integer> charactersCount = new HashMap<>();

        for(Character c : password.toCharArray()){
            if(Character.isDigit(c)){
                charactersCount.put(NUMERIC, charactersCount.getOrDefault(NUMERIC, 0) + 1);
            }else if(Character.isAlphabetic(c)){
                if(Character.isLowerCase(c)){
                    charactersCount.put(ALPHA_LOWER, charactersCount.getOrDefault(ALPHA_LOWER, 0) + 1);
                }else{
                    charactersCount.put(ALPHA_UPPER, charactersCount.getOrDefault(ALPHA_UPPER, 0) + 1);
                }
            }else{
                List<Character> charList = validSpecialChars
                        .chars()
                        .mapToObj(item -> (char) item)
                        .toList();

                if(charList.contains(c)){
                    charactersCount.put(SPECIAL, charactersCount.getOrDefault(SPECIAL, 0) + 1);
                }else{
                    throw new ValidationException(String.format("Invalid character found on password: '%c'.", c));
                }
            }
        }

        if(charactersCount.getOrDefault(ALPHA_UPPER, 0) < minAlphaUpper)
            throw new ValidationException(String.format("Password must have at least %d upper case alphabetic " +
                    "characters.", minAlphaUpper));

        if(charactersCount.getOrDefault(ALPHA_LOWER, 0) < minAlphaLower)
            throw new ValidationException(String.format("Password must have at least %d lower case alphabetic " +
                    "characters.", minAlphaLower));

        if(charactersCount.getOrDefault(NUMERIC, 0) < minNumeric)
            throw new ValidationException(String.format("Password must have at least %d numeric characters.", minNumeric));

        if(charactersCount.getOrDefault(SPECIAL, 0) < minSpecial)
            throw new ValidationException(String.format("Password must have at least %d special characters among the " +
                    "following list of valid special characters: '%s'.", minAlphaUpper, validSpecialChars));
    }
}
