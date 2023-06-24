package it.polimi.ingsw.view.cliChat;


import it.polimi.ingsw.view.cliChat.Printer.PrintMsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadSend extends Thread {
    private final Scanner chat = new Scanner(System.in);
    private final PrintMsg printer = new PrintMsg();
    private final Socket socket;
    public ThreadSend(Socket socket){
        this.socket = socket;
    }

    //Read the input from the user in the chat
    @Override
    public void run() {
        try {
            String nicknameReciever;
            String textMessage;
            String chatSender = "";

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = userInputReader.readLine()) != null) {
                boolean notValid;
                if(userInput.equals("+privateMessage")){                                        //Syntax: P-N° player to send-Message-P
                    TerminalServer.setUserIsWriting(true);
                    chatSender += "P-";
                    printer.printMsgServer("This is the list of players with their code:\n" +
                                            TerminalServer.getPlayers());
                    do {
                        notValid = false;
                        printer.printMsgServer("Send message to (enter code): ");
                        nicknameReciever = chat.nextLine();
                        try {
                            if (!TerminalServer.isValidCode(Integer.parseInt(nicknameReciever)))
                                printer.printError("No player has this code");
                        } catch(NumberFormatException e){
                            printer.printError("Not a valid code");
                            notValid = true;
                        }
                    } while(notValid || !TerminalServer.isValidCode(Integer.parseInt(nicknameReciever)));

                    int index = Integer.parseInt(nicknameReciever);
                    index -= 1;
                    String to = TerminalServer.getPlayers().split("-")[index*2+1].replace(" ", "");
                    chatSender += to + "-";

                    printer.printMsgServer("Enter the message you wanna send: ");
                    textMessage = chat.nextLine();
                    chatSender += textMessage + "-P";
                    writer.println(chatSender);
                    printer.printOwnMsg("P-you-" + to + "-" + textMessage + "-P");
                } else if(userInput.equals("+message")){                                         //Syntax: A-Message-A
                    TerminalServer.setUserIsWriting(true);
                    chatSender += "A-";
                    printer.printMsgServer("Enter the message you wanna send: ");
                    textMessage = chat.nextLine();
                    chatSender += textMessage + "-A";
                    writer.println(chatSender);
                    printer.printOwnMsg("A-you-" + textMessage + "-A");
                } else {
                    printer.printError("This is not a valid input!!");
                    printer.printMsgServer("For private messages type \"+privateMessage\" " +
                                           "- for public messages type \"+message\"" +
                                           " then follow the instruction");
                }
                TerminalServer.setUserIsWriting(false);
                //Print all the messages arrived while the player was typing
                if(!TerminalServer.getUserIsWriting() && !TerminalServer.getBufferMessages().isEmpty()) {
                    for(String s: TerminalServer.getBufferMessages())
                        printer.printMsgPlayers(s);

                    TerminalServer.setBufferMessages(new ArrayList<>());
                }
                chatSender = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
