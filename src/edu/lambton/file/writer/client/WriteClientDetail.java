package edu.lambton.file.writer.client;

import edu.lambton.file.writer.IWriteFile;
import edu.lambton.model.Client;
import edu.lambton.model.PersonalData;

import java.io.BufferedWriter;
import java.io.IOException;

public interface WriteClientDetail extends IWriteFile<BufferedWriter> {
    void writeClientDetail(String username, PersonalData data) throws IOException;
    void writeClientDetail(Client userAccount);
}
