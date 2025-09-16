package server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMT extends Thread {

    int clientNumber;
    boolean isServerActive = true;
    boolean isConversationActive = true;

    public static void main (String[] args) {
      new ServerMT().start();
    }

    @Override
    public void run () {
        try {
            ServerSocket serverSocket = new ServerSocket(3030);
            System.out.println("Starting server ...");
            while (isServerActive) {
                Socket socket = serverSocket.accept();
                ++clientNumber;
                new Conversation(socket , clientNumber).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class Conversation extends Thread {
        private Socket socket;
        private int clientNumber = 0;
        public Conversation (Socket s ,int  number) {
            this.socket = s;
            this.clientNumber = number;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream , true);
                String IPClient = socket.getRemoteSocketAddress().toString();
                System.out.println("Client connected, number : " + clientNumber + " Ip address : " + IPClient);
                printWriter.println("Welcome you are the client number : " + clientNumber);

                while (isConversationActive) {
                    String request = bufferedReader.readLine();
                    printWriter.write(request.length());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }



}
