package edu.lambton.file.reader.credential;

import edu.lambton.file.reader.IReadFile;

import java.io.BufferedReader;

public interface CredentialReadFile extends IReadFile<BufferedReader> {
    String readPasswordInformation(String username);
}