package it.polimi.ingsw.view.cliChat.Printer;

public class PrintMsg {
    //ANSI Colors
    public static final String RESET = "\033[0m";               //Reset to normal color
    public static final String RED = "\033[38;5;196m";          //Errors
    public static final String GREEN = "\033[38;5;83m";         //Messages from server
    public static final String PINK = "\033[38;5;164m";         //Messages from players
    public static final String WHITE = "\033[38;5;231m";        //Content of player message

    //Printer for errors
    public void printError(String error){
        System.out.println(RED + error + RESET);
    }

    //Printer for messages from server
    public void printMsgServer(String msg){
        System.out.println(GREEN + msg + RESET);
    }

    //Printer for messages from players
    public void printMsgPlayers(String message){
        String[] msg = message.split("-");

        if (msg[0].equals("P"))
            System.out.print(PINK + "Message from " + msg[1] + " to you -> " + WHITE +  msg[2] + "\n" + RESET);
        else if (msg[0].equals("A"))
            System.out.print(PINK + "Message from " + msg[1] + " to everybody -> " + WHITE +  msg[2] + "\n" + RESET);
        else
            printError("Message format is not accepted");
    }
}
