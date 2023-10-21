# Hangman Game

This is a simple implementation of the classic Hangman game using Java sockets for client-server communication for OS mini-project.


## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Docker (optional, for containerized deployment)

### Running the Server

1. Ensure you have the JDK installed.
2. Open a terminal and navigate to the directory containing `server.java`.
3. Compile the server code:
   ```bash
   javac server.java
   ```
4. Start the server:
   ```bash
   java server
   ```

### Running the Client

1. Ensure you have the JDK installed.
2. Open a terminal and navigate to the directory containing `client.java`.
3. Compile the client code:
   ```bash
   javac client.java
   ```
4. Start the client:
   ```bash
   java client
   ```

## Usage

1. The server will start and wait for client connections on port 12345.
2. Run the client, which will attempt to connect to the server at `127.0.0.1:12345`.
3. Once connected, the client can start guessing letters to complete the hidden word.
4. The server will provide feedback on the guessed letters and remaining attempts.
5. The game ends when the client either guesses the word correctly or exhausts all attempts.

## Code Structure

### server.java

- `chooseWord()`: Randomly selects a word from a predefined list.
- `ClientHandler`: Handles communication with a connected client.
  - Initializes game state (word, guessed word, attempts).
  - Listens for client guesses, updates game state, and sends responses.
- `main`: Waits for client connections and creates a new thread for each client.

### client.java

- `UserInputHandler`: Manages user input for sending guesses to the server.
  - Ensures one character input and enforces a 1-second delay between guesses.
- `main`: Establishes a connection with the server and sets up input/output streams.
  - Starts a separate thread for handling user input.
  - Receives and displays server responses until the game ends.

## Dockerfile

- Uses the `openjdk:latest` base image.
- Sets the working directory to `/usr/src/app`.
- Copies all files to the container.
- Compiles the server code.
- Exposes port `12345`.
- Specifies the command to start the server.

## Authors

- [Pun Thammanant Thamtaranon]

## Acknowledgments

- This project is based on the classic Hangman game and uses Java sockets for client-server communication.
