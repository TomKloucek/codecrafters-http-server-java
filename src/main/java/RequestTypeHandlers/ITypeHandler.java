package RequestTypeHandlers;

import BussinessObjects.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface ITypeHandler {
    public void handle(BufferedReader in, OutputStream out, Request clientReq) throws IOException;
}
