package it.polimi.ingsw.view.cliChat.Printer;

/**
 * A utility class for printing messages with ANSI colors
 */
public class PrintMsg {
    //ANSI Colors
    public static final String RESET = "\033[0m";               //Reset to normal color
    public static final String RED = "\033[38;5;196m";          //Errors
    public static final String GREEN = "\033[38;5;83m";         //Messages from server
    public static final String PINK = "\033[38;5;164m";         //Messages from players
    public static final String WHITE = "\033[38;5;231m";        //Content of player message

    /**
     * Prints an error message in red color
     *
     * @param error the error message to print
     */
    public void printError(String error){
        System.out.println(RED + error + RESET);
    }

    /**
     * Prints a message from the server in green color
     *
     * @param msg the message to print
     */
    public void printMsgServer(String msg){
        System.out.println(GREEN + msg + RESET);
    }

    /**
     * Prints a message from a player in pink color
     *
     * @param message the message to print (Type-From-Text-Type)
     */
    public void printMsgPlayers(String message){            //Type-From-Text-Type
        String[] msg = message.split("-");

        if (msg[0].equals("P"))
            System.out.print(PINK + "Message from " + msg[1] + " to you -> " + WHITE +  msg[2] + "\n" + RESET);
        else if (msg[0].equals("A"))
            System.out.print(PINK + "Message from " + msg[1] + " to everybody -> " + WHITE +  msg[2] + "\n" + RESET);
        else
            printError("Message format is not accepted");
    }

    /**
     * Prints a player's own message in pink color
     *
     * @param message the message to print (Type-From-To-Text-Type)
     */
    public void printOwnMsg(String message){            //Type-From-To-Text-Type
        String[] msg = message.split("-");

        if (msg[0].equals("P"))
            System.out.print(PINK + "Message from " + msg[1] + " to " + msg[2] + " -> " + WHITE +  msg[3] + "\n" + RESET);
        else if (msg[0].equals("A"))
            System.out.print(PINK + "Message from " + msg[1] + " to everybody -> " + WHITE +  msg[2] + "\n" + RESET);
        else
            printError("Message format is not accepted");
    }
}
