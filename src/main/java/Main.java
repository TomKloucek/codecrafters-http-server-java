import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static String getPathFromAddress(String address) {
    return address.split(" ")[1];
  }
  public static String echoCommand(String path) {
    if (path.startsWith("/echo/")) {
      return path.split("/echo/")[1];
    }
    return "";
  }
  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(4221)) {
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      System.out.println("Server is listening on port 4221");


      try (Socket clientSocket = serverSocket.accept()) { // Wait for connection from client.
        System.out.println("Accepted new connection");

        // Reading data from the client
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream(), StandardCharsets.UTF_8));
             Writer out = new BufferedWriter(new OutputStreamWriter(
                     new BufferedOutputStream(clientSocket.getOutputStream()), StandardCharsets.UTF_8))) {

          // Read data sent by the client
          String clientData;
          while ((clientData = in.readLine()) != null) {

            if (clientData.strip().startsWith("GET")) {
              String path = getPathFromAddress(clientData);

              switch (path) {
                case "/" -> {
                  out.write("HTTP/1.1 200 OK\r\n\r\n");
                  out.flush();
                }
                case null, default -> {
                  String echo = echoCommand(path);
                  if (!echo.isEmpty()) {
                    out.write(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", echo.length(), echo));
                    out.flush();
                  } else {
                    out.write("HTTP/1.1 404 Not Found\r\n\r\n");
                    out.flush();
                  }
                }
              }
            }

            if (clientData.isEmpty()) { // End of headers
              break;
            }
          }

        } catch (IOException e) {
          System.err.println("Error in client communication: " + e.getMessage());
        }
      } catch (IOException e) {
        System.err.println("Error accepting client connection: " + e.getMessage());
      }
  } catch(
  IOException e)

  {
    System.out.println("IOException: " + e.getMessage());
  }
}
}
