package pkg.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Contact {
    public Integer id;
    public String name;
    public String address;
    public String telephone;

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public Contact(Integer id, String name, String address, String telephone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public static List<Contact> selectAll() {
        List<Contact> result = new ArrayList<>();
        Connection connection = SQL.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM information";
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                result.add(new Contact(
                        set.getInt("i_id"),
                        set.getString("i_name"),
                        set.getString("i_addr"),
                        set.getString("i_tele")
                ));
            }
        } catch (Exception ignored) { } finally {
            try {
                connection.close();
            } catch (Exception ignored) { }
        }
        return result;
    }

    public static void insert(Contact contact) {
        Connection connection = SQL.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO information (i_name, i_addr, i_tele) VALUES (?, ?, ?)";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, contact.name);
            pre.setString(2, contact.address);
            pre.setString(3, contact.telephone);
            pre.executeUpdate();
        } catch (Exception ignored) {
            try {
                connection.rollback();
            } catch (Exception ignored1) { }
        } finally {
            try {
                connection.commit();
                connection.close();
            } catch (Exception ignored) { }
        }
    }

    public static void update(Contact contact) {
        Connection connection = SQL.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE information SET i_name=?, i_addr=?, i_tele=? WHERE i_id=?";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, contact.name);
            pre.setString(2, contact.address);
            pre.setString(3, contact.telephone);
            pre.setInt(4, contact.id);
            pre.executeUpdate();
        } catch (Exception ignored) {
            try {
                connection.rollback();
            } catch (Exception ignored1) { }
        } finally {
            try {
                connection.commit();
                connection.close();
            } catch (Exception ignored) { }
        }
    }

    public static void deleteById(Integer id) {
        Connection connection = SQL.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM information WHERE i_id=?";
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            pre.executeUpdate();
        } catch (Exception ignored) {
            try {
                connection.rollback();
            } catch (Exception ignored1) { }
        } finally {
            try {
                connection.commit();
                connection.close();
            } catch (Exception ignored) { }
        }
    }
}
