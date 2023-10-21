import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = null;
            boolean connected = false;

            while (!connected) {
                try {
                    socket = new Socket("127.0.0.1", 12345);
                    connected = true;
                } catch (ConnectException e) {
                    System.out.println("Server is not available. Retrying...");
                    Thread.sleep(2000); // Wait for 2 seconds before retrying
                }
            }

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            Thread userInputThread = new Thread(new UserInputHandler(out));
            userInputThread.start();

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (serverResponse.contains("won!") || serverResponse.contains("lost.")) {
                    break;
                }
            }

            userInputThread.interrupt();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class UserInputHandler implements Runnable {
        private PrintWriter out;
        private long lastInputTime;

        public UserInputHandler(PrintWriter out) {
            this.out = out;
            this.lastInputTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            try {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    long currentTime = System.currentTimeMillis();
                    if (userInput.length() == 1 && (currentTime - lastInputTime) >= 1000) {
                        out.println(userInput);
                        lastInputTime = currentTime;
                    } else if (userInput.length() != 1) {
                        System.out.println("Please enter only one character:");
                    } else if ((currentTime - lastInputTime) < 1000) {
                        System.out.println("Please wait before sending another input.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
