import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static String getPathFromAddress(String address) {
    return address.split(" ")[1];
  }

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(4221)) {
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      System.out.println("Server is listening on port 4221");

      while (true) {
        try {
          Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
          new Thread(new ClientConnectionHandler(clientSocket)).start();
        } catch (IOException e) {
          System.err.println("Error accepting client connection: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
}
}
