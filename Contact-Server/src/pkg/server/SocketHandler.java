package pkg.server;

import pkg.data.Constants;
import pkg.data.Contact;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class SocketHandler extends Thread {

    private final Socket socket;
    private Scanner reader;
    private PrintWriter writer;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            reader = new Scanner(socket.getInputStream(), Constants.CHARSET);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Constants.CHARSET), true);

            Integer id;
            String name, address, telephone;

            String cmd = readString();
            if (cmd == null) {
                return;
            } else {
                System.out.println(cmd);
            }

            switch (cmd) {
                case Constants.SELECT:
                    List<Contact> contacts = Contact.selectAll();
                    writer.println(contacts.size());
                    for (Contact contact: contacts) {
                        writer.println(Constants.INFO_DIVIDER);
                        writer.println(contact.id);
                        writer.println(contact.name);
                        writer.println(contact.address);
                        writer.println(contact.telephone);
                    }
                    break;

                case Constants.INSERT:
                    name = readString();
                    address = readString();
                    telephone = readString();
                    if (name != null && !name.equals("null") && !name.equals("")) {
                        Contact.insert(new Contact(null, name, address, telephone));
                    }
                    break;

                case Constants.UPDATE:
                    id = readInt();
                    name = readString();
                    address = readString();
                    telephone = readString();
                    if (name != null && !name.equals("null") && !name.equals("")) {
                        Contact.update(new Contact(id, name, address, telephone));
                    }
                    break;

                case Constants.DELETE:
                    id = readInt();
                    if (id != null) {
                        System.out.println(id);
                        Contact.deleteById(id);
                    }
                    break;

                case Constants.ECHO:
                    String word = readString();
                    writer.println("Hello " + word + "!");
                    break;

                default:
                    System.out.println("Invalid command!");
            }

            writer.println(Constants.END_IDENTIFIER);

        } catch (Exception e) {
            System.out.println("handle: error!");
        }
    }

    public String readString() {
        if (reader.hasNextLine()) {
            return reader.nextLine().trim();
        }
        return null;
    }

    public Integer readInt() {
        if (reader.hasNextLine()) {
            Integer result = reader.nextInt();
            reader.nextLine();
            return result;
        }
        return null;
    }
}
