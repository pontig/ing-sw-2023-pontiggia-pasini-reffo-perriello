package it.polimi.ingsw.view.cliChat;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendChatMessage;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

import static it.polimi.ingsw.enums.State.CHAT_MESSAGE;

/**
 * This class represents the thread that reads the messages from the server
 */
public class ThreadRead extends Thread{
    private final Socket socket;
    private View cli;
    public ThreadRead(Socket socket, View cli){
        this.socket = socket;
        this.cli = cli;
    }

    /**
     * Reads incoming messages from the socket's input stream and processes them accordingly.
     * Each incoming message is split into segments using the "-" delimiter.
     * If the first segment is "P", it creates a private chat message with the sender's nickname,
     * recipient's nickname, and the message content. Otherwise, it creates a public chat message
     * with the sender's nickname and the message content.
     * The created message object is then passed to the client's view to be displayed.
     */
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            Message msg;
            while ((message = reader.readLine()) != null) {
                String[] split = message.split("-");

                if(split[0].equals("P"))
                    msg = new SendChatMessage(CHAT_MESSAGE, cli.getNickname(), split[1], split[2]);
                else
                    msg = new SendChatMessage(CHAT_MESSAGE, cli.getNickname(), null, split[1]);

                cli.setChangedView();
                cli.notifyObserversView(msg);
                //System.out.println("Message from chat terminal: -> " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
