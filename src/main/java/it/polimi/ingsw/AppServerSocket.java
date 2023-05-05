package it.polimi.ingsw;

import it.polimi.ingsw.network.server.ClientSkeleton;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServerSocket {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(1234)) {
            while(true) {
                try (Socket socket =serverSocket.accept();) {
                    ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                    Server server = new ServerImpl();
                    while(true) {
                        ClientSkeleton.receive(server);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create server socket: " + e.getMessage());
        }
    }
}
