import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    try {
      serverSocket = new ServerSocket(4221);
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);
      clientSocket = serverSocket.accept(); // Wait for connection from client.

      try (Writer out = new BufferedWriter(new OutputStreamWriter(
              new BufferedOutputStream(clientSocket.getOutputStream()), StandardCharsets.UTF_8))) {
        // Your code to handle client communication goes here
        out.write("HTTP/1.1 200 OK\r\n\r\n\n");
        out.flush();
      } catch (IOException e) {
        System.err.println("Error in client communication: " + e.getMessage());
      }

      System.out.println("accepted new connection");
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
