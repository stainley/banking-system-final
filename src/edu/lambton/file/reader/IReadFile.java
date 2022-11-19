package edu.lambton.file.reader;

import java.io.IOException;
import java.io.Reader;

public interface IReadFile<T extends Reader> {
    void closeFile(T file) throws IOException;
}
