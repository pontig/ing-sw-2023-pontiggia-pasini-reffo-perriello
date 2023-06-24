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

public class ThreadRead extends Thread{
    private final Socket socket;
    private View cli;
    public ThreadRead(Socket socket, View cli){
        this.socket = socket;
        this.cli = cli;
    }

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
