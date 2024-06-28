package RequestTypeHandlers;

import BussinessObjects.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public interface TypeHandler {
    public void handle(BufferedReader in, Writer out, Request clientReq) throws IOException;
}
