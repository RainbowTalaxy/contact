package pkg.server;

import pkg.data.Constants;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(Constants.PORT);
            System.out.println("Start listening on port " + Constants.PORT + "...");

            while (true) {
                Socket socket = server.accept();
                new SocketHandler(socket).start();
            }

        } catch (Exception e) {
            System.out.println("Fail to start server!");
        }
    }
}
