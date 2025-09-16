package exercices;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerGame extends Thread{

    boolean isGameActive = true;
    boolean isConversationActive = true;
    int clientNumbre = 0;
    static final int PORT = 1234;
    int randomNumber = 0;
    String winner;
    boolean gameOver;

    public static void main(String[] args) {
        new ServerGame().start();
    }

    @Override
    public void run () {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            randomNumber =  new Random().nextInt(100);
            System.out.println("Server listening on port .. " + PORT);
            while (isGameActive){
                Socket socket = serverSocket.accept();
                ++clientNumbre;
                new Conversation(socket , clientNumbre).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class Conversation extends Thread {

        private Socket socket;
        private int clientNumber;

        public Conversation (Socket s , int clientNumber) {
            this.socket = s;
            this.clientNumber = clientNumber;
        }

        @Override
        public void run () {
            String clientIP = socket.getRemoteSocketAddress().toString();
            System.out.println("Client number " + clientNumbre + "connected with address IP : " + clientIP);
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream , true);

                printWriter.println("Guess the secret number : ");

                while (isConversationActive) {
                    String req = bufferedReader.readLine();
                    int number = Integer.parseInt(req);
                    System.out.printf("Client %s attempt with number : %d " , clientIP , number);
                    System.out.println();
                    if (!gameOver) {
                        if (number > randomNumber) {
                            printWriter.println("Your number is greater then the secret one !");
                        } else if (number < randomNumber) {
                            printWriter.println("Your number is less then the secret one !");
                        } else {
                            printWriter.println("Congratulation you win !");
                            winner = clientIP;
                            gameOver = true;
                        }
                    }else {
                        printWriter.write("Game Over. winner is : " + winner);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
