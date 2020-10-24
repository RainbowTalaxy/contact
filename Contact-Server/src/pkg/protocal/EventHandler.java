package pkg.protocal;

import pkg.data.Contact;

import java.util.List;

public interface EventHandler {
    void emit(List<Contact> contacts);
}
