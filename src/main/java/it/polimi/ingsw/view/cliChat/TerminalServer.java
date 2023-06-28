package it.polimi.ingsw.view.cliChat;


import it.polimi.ingsw.view.cliChat.Printer.PrintMsg;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class TerminalServer {
    private static String players = "";
    private static final List<Integer> codes = new ArrayList<>();
    private static boolean userIsWriting = false;
    private static List<String> bufferMessages = new ArrayList<>();

    //getter

    /**
     * This method returns the
     * @return
     */
    public static String getPlayers() {
        return players;
    }

    /**
     * Returns the flag indicating if the user is currently writing a message.
     *
     * @return True if the user is writing, false otherwise.
     */
    public static boolean getUserIsWriting() {
        return userIsWriting;
    }

    /**
     * Returns the Buffer of messages.
     *
     * @return The list of buffered messages.
     */
    public static List<String> getBufferMessages() {
        return bufferMessages;
    }

    //setter

    /**
     * Sets the list of players.
     *
     * @param listPlayers The list of players to set.
     */

    public static void setPlayers(String listPlayers) {
        players = listPlayers;
        //System.out.println(players);
    }

    /**
     * Sets the list of players' codes.
     *
     * @param listPlayers The list of players' codes to set.
     */
    public static void setPlayersCode(String listPlayers) {
        String tmp = listPlayers.replace(" ", "");
        tmp = tmp.replace("\n", "");
        String[] codesArr = tmp.split("-");

        int j = 0;
        for(int i = 0; i < codesArr.length; i++ ){
            if(i == j*2){
                codes.add(Integer.parseInt(codesArr[i]));
                j++;
            }
        }
    }

    /**
     * Sets the flag indicating if the user is currently writing a message.
     *
     * @param isWriting The flag value to set.
     */
    public static void setUserIsWriting(Boolean isWriting) {
        userIsWriting = isWriting;
    }

    /**
     * Sets the buffer of messages.
     *
     * @param list The list of messages to set as the buffer.
     */
    public static void setBufferMessages(List<String> list) {
        bufferMessages = list;
    }

    /**
     * Checks if a given nickname is a valid code.
     *
     * @param nickname The nickname to check.
     * @return True if the nickname is a valid code, false otherwise.
     */
    public static boolean isValidCode(int nickname) {
        for(int c: codes){
            if(nickname == c)
                return true;
        }
        return false;
    }

    //main function

    /**
     * The main function of the program.
     * It initializes the server socket with a free port, starts the ConnectionHandler and ThreadSend threads,
     * and keeps accepting new connections indefinitely.
     *
     * @param args The command-line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        ConnectionHandler handler;
        ThreadSend userInput;

        ServerSocket serverSocket = new ServerSocket(0);                                    //Create a socket with the first free port found by the system
        int port = serverSocket.getLocalPort();                                                  //Get the port value
        new PrintMsg().printMsgServer("Port: " + port);
        ConnectionHandler.setPort(0);

        while (true) {
            handler = new ConnectionHandler(serverSocket, port);
            userInput = new ThreadSend(handler.getSocket());

            handler.start();
            userInput.start();
        }
    }
}
