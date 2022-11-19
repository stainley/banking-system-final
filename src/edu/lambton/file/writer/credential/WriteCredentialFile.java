package edu.lambton.file.writer.credential;

import edu.lambton.file.writer.IWriteFile;

import java.io.BufferedWriter;
import java.io.IOException;

public interface WriteCredentialFile extends IWriteFile<BufferedWriter> {
    boolean writePasswordFile(String passwordInfo) throws IOException;
}
