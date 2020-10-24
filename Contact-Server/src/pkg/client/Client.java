package pkg.client;

import pkg.data.Constants;
import pkg.data.Contact;
import pkg.protocal.EventHandler;

import java.net.Socket;

public class Client {
    public static void request(String ip, String type, Contact contact, EventHandler handler) {
        try {
            Socket socket = new Socket(ip, Constants.PORT);
            new SocketRequest(socket, type, contact, handler).start();

        } catch (Exception ignored) {
            System.out.println("Fail to connect to server!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // if you want to use multiple requests, use `Thread.sleep(...)` between them.
        Client.request(Constants.IP, Constants.SELECT, null, System.out::println);
    }
}
