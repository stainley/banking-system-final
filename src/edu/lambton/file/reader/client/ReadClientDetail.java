package edu.lambton.file.reader.client;

import edu.lambton.file.reader.IReadFile;
import edu.lambton.model.PersonalData;

import java.io.BufferedReader;

public interface ReadClientDetail extends IReadFile<BufferedReader> {
    PersonalData getClientInformationByUsername(String username);
}
