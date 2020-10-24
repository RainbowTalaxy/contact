package pkg.client;

import pkg.data.Constants;
import pkg.data.Contact;
import pkg.protocal.EventHandler;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SocketRequest extends Thread {
    private final Socket socket;
    private final String type;
    private final Contact contact;
    private final EventHandler handler;
    private Scanner reader;
    private PrintWriter writer;

    public SocketRequest(Socket socket, String type, Contact contact, EventHandler handler) {
        this.socket = socket;
        this.type = type;
        this.contact = contact;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            reader = new Scanner(socket.getInputStream(), Constants.CHARSET);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Constants.CHARSET), true);

            Integer id;
            String name, address, telephone;

            switch (type) {
                case Constants.SELECT:
                    writer.println(type);
                    Integer size = readInt();
                    List<Contact> contacts = new ArrayList<>();
                    for (int i = 0; i < size; i += 1) {
                        if (readString().equals(Constants.INFO_DIVIDER)) {
                            id = readInt();
                            name = readString();
                            address = readString();
                            telephone = readString();
                            contacts.add(new Contact(id, name, address, telephone));
                        }
                    }
                    handler.emit(contacts);
                    break;

                case Constants.INSERT:
                case Constants.UPDATE:
                    writer.println(type);
                    writer.println(contact.name);
                    writer.println(contact.address);
                    writer.println(contact.telephone);
                    break;

                case Constants.DELETE:
                    writer.println(type);
                    writer.println(contact.id);
                    break;

                default:
                    System.out.println("Invalid command!");
            }

        } catch (Exception e) {
            System.out.println("request: error!");
        }
    }

    public String readString() {
        while (reader.hasNextLine()) {
            return reader.nextLine().trim();
        }
        return null;
    }

    public Integer readInt() {
        while (reader.hasNextLine()) {
            Integer result = reader.nextInt();
            reader.nextLine();
            return result;
        }
        return null;
    }
}
