package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.users;


import java.sql.*;

public class ConnectionClass {
    public static Connection connection;

    public static Connection getConnection() {
        String databaseName = "abd";
        String databaseUser = "root";
        String databasePassword = "biriczlaci";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static ObservableList<users> getDatausers(){

        Connection connection = getConnection();

        ObservableList<users> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new users(rs.getString("nev"),rs.getString("szul_ev") ,rs.getString("nem"),rs.getString("ido"),rs.getString("id")));
            }
        } catch (Exception e) {

        }
        return list;

    }
}
