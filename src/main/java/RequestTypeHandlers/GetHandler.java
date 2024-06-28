package RequestTypeHandlers;

import Commands.FilesCommand;
import BussinessObjects.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public class GetHandler implements TypeHandler{
    private String echoCommand(String path) {
        if (path.startsWith("/echo/")) {
            return path.split("/echo/")[1];
        }
        return "";
    }
    @Override
    public void handle(BufferedReader in, Writer out, Request clientReq) throws IOException {
        if (clientReq.getPath().equals("/")) {
            out.write("HTTP/1.1 200 OK\r\n\r\n");
            out.flush();
        } else {
            if (clientReq.getPath().startsWith("/echo")) {
                String echo = echoCommand(clientReq.getPath());
                if (!echo.isEmpty()) {
                    out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", echo.length(), echo));
                    out.flush();
                }
            } else if (clientReq.getPath().startsWith("/user-agent")) {
                out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", clientReq.getUserAgent().length(), clientReq.getUserAgent()));
                out.flush();
            } else if (clientReq.getPath().startsWith("/files")) {
                FilesCommand filesCommand = FilesCommand.getInstance("");
                try {
                    String content = filesCommand.getFileContent(clientReq.getPath().split("/files/")[1]);
                    out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\nContent-Length: %d\r\n\r\n%s", content.length(), content));
                    out.flush();
                } catch (Exception e) {
                    out.write("HTTP/1.1 404 Not Found\r\n\r\n");
                    out.flush();
                }
            }
            else {
                out.write("HTTP/1.1 404 Not Found\r\n\r\n");
                out.flush();
            }
        }
    }
}
