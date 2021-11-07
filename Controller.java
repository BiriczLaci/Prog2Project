package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ChoiceBox;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private DatePicker calendar;

    @FXML
    private TextField freedom;

    @FXML
    private ChoiceBox<String> gend;

    @FXML
    private TableView<users> table;

    @FXML
    private TableColumn<users, String> tablecalendar;

    @FXML
    private TableColumn<users, String> tablegender;

    @FXML
    private TableColumn<users, String> tablename;

    @FXML
    private TableColumn<users, String> tablefreedom;

    ObservableList<users> listM;

    int index = -1;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void loadData(){
        gend.getItems().add("Férfi");
        gend.getItems().add("Nő");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadData();
        tablename.setCellValueFactory(new PropertyValueFactory<users,String>("name"));
        tablecalendar.setCellValueFactory(new PropertyValueFactory<users,String>("age"));
        tablegender.setCellValueFactory(new PropertyValueFactory<users,String>("gender"));
        tablefreedom.setCellValueFactory(new PropertyValueFactory<users,String>("freedom"));

        listM = ConnectionClass.getDatausers();
        table.setItems(listM);
    }



    public void addButton(ActionEvent event) throws SQLException {

        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        if (name.getText().isEmpty() | calendar.getEditor().getText().isEmpty() | gend.getSelectionModel().isEmpty()| freedom.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Helytelen adatok");
            alert.setHeaderText(null);
            alert.setContentText("Kérlek tölts ki minden mezőt");
            alert.showAndWait();
        }
        else {
            String sql = "INSERT INTO USER VALUES('" + name.getText() + "','" + calendar.getValue() + "', '" + gend.getValue() + "', '" + freedom.getText() + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }


    }


}


