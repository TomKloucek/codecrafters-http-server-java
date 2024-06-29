package RequestTypeHandlers;

import BussinessObjects.Request;
import Commands.FilesCommand;

import java.io.*;

public class PostHandler implements ITypeHandler {
    @Override
    public void handle(BufferedReader in, OutputStream out, Request clientReq) throws IOException {
        System.out.println(clientReq);
        if (clientReq.getPath().startsWith("/files")) {
            FilesCommand filesCommand = FilesCommand.getInstance("");
            try {
                System.out.println(filesCommand.writeToFile(clientReq.getPath().split("/files/")[1], clientReq.getBody()));
                out.write("HTTP/1.1 201 Created\r\n\r\n".getBytes());
                out.flush();
            } catch (Exception e) {
                out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
                out.flush();
            }
        }
        else {
            out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            out.flush();
        }
    }
}

