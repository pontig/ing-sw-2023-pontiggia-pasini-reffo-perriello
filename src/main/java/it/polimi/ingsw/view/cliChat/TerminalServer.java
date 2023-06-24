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
    public static String getPlayers() {
        return players;
    }
    public static boolean getUserIsWriting() {
        return userIsWriting;
    }
    public static List<String> getBufferMessages() {
        return bufferMessages;
    }

    //setter
    public static void setPlayers(String listPlayers) {
        players = listPlayers;
        //System.out.println(players);
    }
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
    public static void setUserIsWriting(Boolean isWriting) {
        userIsWriting = isWriting;
    }
    public static void setBufferMessages(List<String> list) {
        bufferMessages = list;
    }

    public static boolean isValidCode(int nickname) {
        for(int c: codes){
            if(nickname == c)
                return true;
        }
        return false;
    }

    //main function
    public static void main(String[] args) throws IOException {
        ConnectionHandler handler;
        ThreadSend userInput;

        ServerSocket serverSocket = new ServerSocket(0);                                    //Create a socket with the first free port found by the syestem
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
