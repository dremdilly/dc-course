package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process() throws IOException {
        // Print request that we received.
        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();

        String requestLine = request.getRequestLine().split(" ")[1];

        // To send response back to the client.
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        // We are returning a simple web page now.

        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Hello</title></head>");
        switch (requestLine) {
            case "/create/itemid" -> {
                output.println("<body><p>Create item</p></body>");
            }
            case "/delete/itemid" -> {
                output.println("<body><p>Delete item</p></body>");
            }
            case "/exec/params" -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                output.println("<body><p>Execute parameters</p></body>");
            }
            default -> {
                output.println("<body><p>Hello, world!</p></body>");
            }
        }
        output.println("</html>");
        output.flush();

        socket.close();
    }
}
