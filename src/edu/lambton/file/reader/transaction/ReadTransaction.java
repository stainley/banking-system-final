package edu.lambton.file.reader.transaction;

import edu.lambton.file.reader.IReadFile;
import edu.lambton.model.transaction.Bank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;

public interface ReadTransaction<T extends Bank> extends IReadFile<BufferedReader> {
    List<T> readAllTransaction() throws FileNotFoundException;
}
