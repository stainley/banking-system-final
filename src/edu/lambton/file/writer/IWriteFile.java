package edu.lambton.file.writer;

import java.io.IOException;
import java.io.Writer;

public interface IWriteFile<T extends Writer> {
    void closeFile(T file) throws IOException;
}
