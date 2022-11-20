package edu.lambton.file;

import java.io.File;

public class ValidateFile {
    public boolean validateIfFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }
}
