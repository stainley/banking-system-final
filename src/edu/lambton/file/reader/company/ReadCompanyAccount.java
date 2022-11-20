package edu.lambton.file.reader.company;

import edu.lambton.file.reader.IReadFile;

import java.io.BufferedReader;
import java.util.List;

public interface ReadCompanyAccount<T> extends IReadFile<BufferedReader> {
    List<T> readAllCompanyAccount();
}
