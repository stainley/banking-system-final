package edu.lambton.services.client.credential;

import edu.lambton.file.reader.credential.CredentialReadFile;
import edu.lambton.file.reader.credential.CredentialReadFileImpl;

import java.util.Base64;

public class ValidateCredential {
    private static ValidateCredential instance;

    private ValidateCredential() {
    }

    public static ValidateCredential getInstance() {
        if (instance == null) {
            instance = new ValidateCredential();
        }
        return instance;
    }

    public boolean validatePassword(String username, String password) {
        CredentialReadFile credentialReadFile = new CredentialReadFileImpl();
        String passwordFromFile = credentialReadFile.readPasswordInformation(username);

        byte[] decodedPassword = Base64.getDecoder().decode(passwordFromFile);
        return password.equals(new String(decodedPassword));
    }
}
