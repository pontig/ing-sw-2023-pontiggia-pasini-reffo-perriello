package it.polimi.ingsw.view.cliChat;

import java.io.*;
import java.net.Socket;

public class GameClient {
    public static void main(String[] args) {
        try {
            int port = -1;
            String command = "x-terminal-emulator -e java -jar /home/tommi/Scrivania/ChatProva/TerminalServer.jar";
            Process process = Runtime.getRuntime().exec(command);

            Thread.sleep(2000);

            try (BufferedReader reader = new BufferedReader(new FileReader("port.txt"))) {
                String line = " ";
                do {
                    line = reader.readLine();
                } while (line != null && line.equals("-"));

                port = Integer.parseInt(line);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread.sleep(2000);

            System.out.println("Connecting to server on port: " + port);
            Socket socket = new Socket("localhost", port);
            String players = "╬1 - Tommi - 2 - Ale - 3 - Sere - 4 - Eli\n";                           //Send the list of players to the chat so it can show it
            socket.getOutputStream().write(players.getBytes());

            //Print messages from server
            Thread serverReaderThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Message from server: -> " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverReaderThread.start();

            //Send messages to server
            Thread userInputThread = new Thread(() -> {
                try {
                    BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
                    OutputStream outputStream = socket.getOutputStream();

                    String userInput;
                    while ((userInput = userInputReader.readLine()) != null) {
                        outputStream.write((userInput + "\n").getBytes());
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            userInputThread.start();

            while(true){
                System.out.println("Gioco va avanti");
                Thread.sleep(30000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
