package RequestTypeHandlers;

import Commands.FilesCommand;
import BussinessObjects.Request;
import Encodings.GzipEncoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

public class GetHandler implements ITypeHandler {
    private String echoCommand(String path) {
        if (path.startsWith("/echo/")) {
            return path.split("/echo/")[1];
        }
        return "";
    }
    @Override
    public void handle(BufferedReader in, OutputStream out, Request clientReq) throws IOException {
        String encoding = "";
        boolean isGzip = clientReq.getEncoding() != null && clientReq.getEncoding().contains("gzip");
        if (isGzip) {
            encoding = "Content-Encoding: gzip\r\n";
        }
        if (clientReq.getPath().equals("/")) {
            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            out.flush();
        } else {
            if (clientReq.getPath().startsWith("/echo")) {
                String echo = echoCommand(clientReq.getPath());
                if (!echo.isEmpty()) {
                    byte[] contentBytes = echo.getBytes("UTF-8");

                    if (isGzip) {
                        contentBytes = new GzipEncoding().encode(contentBytes);
                    }
                    out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n%sContent-Length: %d\r\n\r\n", encoding, contentBytes.length).getBytes());
                    out.flush();
                    out.write(contentBytes);
                    out.flush();
                }
            } else if (clientReq.getPath().startsWith("/user-agent")) {
                out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n%sContent-Length: %d\r\n\r\n%s", encoding ,clientReq.getUserAgent().length(), clientReq.getUserAgent()).getBytes());
                out.flush();
            } else if (clientReq.getPath().startsWith("/files")) {
                FilesCommand filesCommand = FilesCommand.getInstance("");
                try {
                    String content = filesCommand.getFileContent(clientReq.getPath().split("/files/")[1]);
                    out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\n%sContent-Length: %d\r\n\r\n%s", encoding ,content.length(), content).getBytes());
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
}
