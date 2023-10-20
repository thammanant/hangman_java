import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    private static final String[] words = {"hangman", "java", "computer", "programming", "socket"};

    private static String chooseWord() {
        Random random = new Random();
        return words[random.nextInt(words.length)];
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Waiting for client connection...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String word;
        private char[] guessedWord;
        private int attempts;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.word = chooseWord();
            this.guessedWord = new char[word.length()];
            Arrays.fill(guessedWord, '_');
            this.attempts = 10;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out.println("You have " + attempts + " guess left\nYour next guess: ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    char guess = inputLine.charAt(0);

                    if (attempts > 0) {
                        if (word.contains(String.valueOf(guess))) {
                            for (int i = 0; i < word.length(); i++) {
                                if (word.charAt(i) == guess) {
                                    guessedWord[i] = guess;
                                }
                            }
                            if (!String.valueOf(guessedWord).contains("_")) {
                                out.println("You won! The word is " + word + ", you have " + attempts + " guess left");
                                break;
                            }
                        } else {
                            attempts--;
                        }

                        out.println(String.valueOf(guessedWord) + ", you have " + attempts + " guess left\nYour next guess: ");
                    } else {
                        out.println("You lost. The word is " + word + ", you have " + attempts + " guess left");
                        break;
                    }
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
