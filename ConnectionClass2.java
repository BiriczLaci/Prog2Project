package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;

public class ConnectionClass2 {
    public static Connection connection;

    public static Connection getConnection() {
        String databaseName = "foglalasok";
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

    public static ObservableList<foglalido> getDatafoglalido(){

        Connection connection = getConnection();

        ObservableList<foglalido> list2 = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from foglal");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list2.add(new foglalido(rs.getString("nev"),rs.getString("ido1"),rs.getString("ido2")));
            }
        } catch (Exception e) {

        }
        return list2;

    }
}
