package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public  static void main (String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("Waiting for a connection ...");
        Socket s = ss.accept();
        System.out.println("Connection of client : " + s.getRemoteSocketAddress());
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        System.out.println("I'm waiting for the client to send a Character .. ");
        char text = (char) isr.read();
        System.out.println("Input : " + text);
        OutputStream os = s.getOutputStream();
        ss.close();
    }
}
