package edu.lambton.file.reader.account;

import edu.lambton.file.reader.IReadFile;

import java.io.BufferedReader;
import java.util.List;

public interface ReadAccountInformation extends IReadFile<BufferedReader> {
    List<Long> getAllAccountInformation();
}
