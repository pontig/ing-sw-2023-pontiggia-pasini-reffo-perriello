package it.polimi.ingsw.view.cliChat;


import it.polimi.ingsw.view.cliChat.Printer.PrintMsg;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.String.valueOf;

public class ConnectionHandler extends Thread {
    //private ServerSocket serverSocket;
    private final Socket socket;
    private BufferedReader reader;
    private final PrintMsg printer = new PrintMsg();
    private boolean exist = false;
    private static int port;
    public ConnectionHandler(ServerSocket serverSocket, int portSocket) throws IOException {
        if(port == 0) {
            setPort(portSocket);
            portFileHandler();
        }
        this.socket = serverSocket.accept();
        printer.printMsgServer("Connection established with client\n"+
                               "Here you can chat with the other players in the game!\n" +
                               "For private messages type \"+privateMessage\" - for public messages type \"+message\" then follow the instruction");

        //Substitute the current port.txt file with a new one without the used port
        File inputFile = new File("port.txt");
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(valueOf(port))) {
                    line = line.replace(valueOf(port), "-\n");
                } else {
                    line = line + "\n";
                }
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile))
                printer.printError("Failed to rename tmp file, please change the name to port.txt");
        } else
            printer.printError("Failed to replace the port value in the file!");

    }

    public Socket getSocket(){
        return this.socket;
    }

    public static void setPort(int value){
        port = value;
    }

    public void portFileHandler(){
        //Look at all the files in the folder where the jar is to find port.txt
        File folder = new File(".");                                                     //Look for file in the current folder
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals("port.txt")) {
                    exist = true;
                    break;
                }

            }
        }

        //TODO - Sistemare problemi con più port aperti
        //Writing the port number on port.txt
        if(exist){
            //Add port value to the existing file
            try (BufferedReader reader = new BufferedReader(new FileReader("port.txt"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("port.txt", true))) {

                String line;
                String lastLine = "50000";
                while ((line = reader.readLine()) != null) {
                    lastLine = line;
                }

                if(lastLine.equals("-")){
                } else if(Integer.parseInt(lastLine) < 50000) {
                    writer.newLine();
                } else{}

                writer.write(Integer.toString(port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Create file and add the port value
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("port.txt"))) {
                writer.write(Integer.toString(port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = reader.readLine()) != null) {

                if(message.charAt(0) == '╬') {
                    String list = message.replace("╬", "");
                    TerminalServer.setPlayers(list);
                    TerminalServer.setPlayersCode(list);
                    System.out.println("List:" + list);
                    continue;
                }

                //Handler to not lose messages
                if(!TerminalServer.getUserIsWriting())
                    //Print the message received
                    printer.printMsgPlayers(message);
                else
                    //Creating a buffer with all the messages
                    TerminalServer.getBufferMessages().add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
