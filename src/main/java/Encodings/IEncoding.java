package Encodings;

import java.io.IOException;

public interface IEncoding {
    byte[] encode(byte[] content) throws IOException;
}
