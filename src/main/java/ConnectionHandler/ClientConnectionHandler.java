package ConnectionHandler;

import BussinessObjects.Request;
import RequestTypeHandlers.GetHandler;
import RequestTypeHandlers.PostHandler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientConnectionHandler implements Runnable {

    private final Socket clientSocket;

    public ClientConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Accepted new connection");

        // Reading data from the client
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream(), StandardCharsets.UTF_8));
            OutputStream out = clientSocket.getOutputStream();
            List<String> request = new ArrayList<>();
            // Read data sent by the client
            String clientData;
            while ((clientData = in.readLine()) != null) {
                if (!clientData.isEmpty()) {
                    request.add(clientData);
                }
                if (clientData.isEmpty()) {
                    break;
                }
            }
            Request clientReq = new Request(request);

            if (clientReq.getContentLength() > 0) {
                char[] body = new char[clientReq.getContentLength()];
                in.read(body, 0, clientReq.getContentLength());
                clientReq.setBody(new String(body));
            }

            switch (clientReq.getRequestType()) {
                case "GET" -> new GetHandler().handle(in, out, clientReq);
                case "POST" -> new PostHandler().handle(in, out, clientReq);
            }

        } catch (IOException e) {
            System.err.println("Error in client communication: " + e.getMessage());
        }
    }
}
