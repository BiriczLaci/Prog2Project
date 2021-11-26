package sample;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    @FXML
    private DatePicker dateig;

    @FXML
    private DatePicker datetol;

    @FXML
    private Button foglalasok;

    @FXML
    private TextField ID;

    @FXML
    private Button Delete;

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

    @FXML
    private TableColumn<users, String> tableid;

    @FXML
    private TableView<foglalido> table2;

    @FXML
    private TableColumn<foglalido, String> tablename2;

    @FXML
    private TableColumn<foglalido, String> tablemeddig;

    @FXML
    private TableColumn<foglalido, String> tablemettol;

    @FXML
    private TextField search;

    @FXML
    private TextField search2;


    ObservableList<users> listM;
    ObservableList<foglalido> listF;

    int index = -1;


    public void loadData(){
        gend.getItems().add("Férfi");
        gend.getItems().add("Nő");
    }

    public void Refresh(){
        tablename.setCellValueFactory(new PropertyValueFactory<users,String>("name"));
        tablecalendar.setCellValueFactory(new PropertyValueFactory<users,String>("age"));
        tablegender.setCellValueFactory(new PropertyValueFactory<users,String>("gender"));
        tablefreedom.setCellValueFactory(new PropertyValueFactory<users,String>("freedom"));
        tableid.setCellValueFactory(new PropertyValueFactory<users,String>("id"));

        listM = ConnectionClass.getDatausers();
        table.setItems(listM);

        tablename2.setCellValueFactory(new PropertyValueFactory<foglalido,String>("nev"));
        tablemettol.setCellValueFactory(new PropertyValueFactory<foglalido,String>("tol"));
        tablemeddig.setCellValueFactory(new PropertyValueFactory<foglalido,String>("dig"));

        listF = ConnectionClass2.getDatafoglalido();
        table2.setItems(listF);
    }
    @FXML
    public void getSelected(){
        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        name.setText(tablename.getCellData(index).toString());
        calendar.setValue(LocalDate.parse(tablecalendar.getCellData(index).toString()));
        gend.setValue(tablegender.getCellData(index).toString());
        freedom.setText(tablefreedom.getCellData(index).toString());
        ID.setText(tableid.getCellData(index).toString());
    }

    public List<String> megjegyzes = new ArrayList<String>();

    public void Delete() throws SQLException {
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "DELETE FROM abd.user WHERE user.id = ('" + ID.getText() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        Refresh();
        search();
        megjegyzes.add(ID.getText());

        ConnectionClass2 connectionClass2=new ConnectionClass2();
        Connection connection2 = connectionClass2.getConnection();

        String sql2 = "DELETE FROM foglalasok.foglal WHERE foglal.id = ('"+ID.getText()+"')";
        Statement statement2 = connection2.createStatement();
        statement2.executeUpdate(sql2);
        Refresh();
        search();
    }
    public void Search2() throws SQLException{


        tablename2.setCellValueFactory(new PropertyValueFactory<foglalido,String>("nev"));
        tablemettol.setCellValueFactory(new PropertyValueFactory<foglalido,String>("tol"));
        tablemeddig.setCellValueFactory(new PropertyValueFactory<foglalido,String>("dig"));

        listF = ConnectionClass2.getDatafoglalido();
        table2.setItems(listF);

        FilteredList<foglalido> filteredData2 = new FilteredList<>(listF, c -> true);
        search.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredData2.setPredicate(foglalido -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (foglalido.nev.toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                else
                return false;


            });
        });

        SortedList<foglalido> sortedData2 = new SortedList<>(filteredData2);
        table2.setItems(sortedData2);



    }

    public void Search() throws SQLException{

        tablename.setCellValueFactory(new PropertyValueFactory<users,String>("name"));
        tablecalendar.setCellValueFactory(new PropertyValueFactory<users,String>("age"));
        tablegender.setCellValueFactory(new PropertyValueFactory<users,String>("gender"));
        tablefreedom.setCellValueFactory(new PropertyValueFactory<users,String>("freedom"));
        tableid.setCellValueFactory(new PropertyValueFactory<users,String>("id"));

        listM = ConnectionClass.getDatausers();
        table.setItems(listM);

        FilteredList<users> filteredData = new FilteredList<>(listM, b -> true);
        search.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredData.setPredicate(users -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(users.name.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (users.freedom.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });

        SortedList<users> sortedData = new SortedList<>(filteredData);
        table.setItems(sortedData);
    }

    public void search(){
        try {
            Search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Search2();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Refresh();
        loadData();
        search();

    }


    public void foglalasButton(ActionEvent event) throws  SQLException{

        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        ConnectionClass2 connectionClass2=new ConnectionClass2();
        Connection connection2 = connectionClass2.getConnection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");

        LocalDate szam1 = LocalDate.parse(datetol.getEditor().getText(),formatter);
        LocalDate szam2 = LocalDate.parse(dateig.getEditor().getText(),formatter);

        long days = ChronoUnit.DAYS.between(szam1,szam2);
        int day;
        day = Integer.parseInt(freedom.getText())- (int)days;

        if (day < 0) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Több szabadság");
            alert.setHeaderText(null);
            alert.setContentText("Túl sok szabadságot vesz ki");
            alert.showAndWait();

        }
        else {


            String sql = "update user set ido = '"+day+"' where id = '"+ID.getText()+"' ";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            Refresh();

            String sql3 = "SELECT user.id AS j FROM user WHERE id = '"+ID.getText()+"'";
            ResultSet rs = connection.createStatement().executeQuery(sql3);
            int i = 0;
            if (rs.next()){
                i = rs.getInt("j");
            }


            if (i == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Hiba");
                alert.setHeaderText(null);
                alert.setContentText("Nincs ilyen alkalmazott");
                alert.showAndWait();

            }
            else {
                String sql2 = "INSERT INTO foglal VALUES('" + name.getText() + "','" + datetol.getValue() + "','"+dateig.getValue()+"','"+ID.getText()+"')";
                Statement statement2 = connection2.createStatement();
                statement2.executeUpdate(sql2);
                Refresh();
                search();
            }



        }

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

            if (megjegyzes.isEmpty()){
                String sql2 = "Select Count(*) AS num FROM user";
                ResultSet rs = connection.createStatement().executeQuery(sql2);
                int i = 0;
                if (rs.next()){
                    i = rs.getInt("num");
                }


                String sql = "INSERT INTO USER VALUES('" + name.getText() + "','" + calendar.getValue() + "' , '" + gend.getValue() + "', '" + freedom.getText() + "' , '" + Integer.toString(i+1) + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                Refresh();
                search();
            }
            else {
                String sql = "INSERT INTO USER VALUES('" + name.getText() + "','" + calendar.getValue() + "' , '" + gend.getValue() + "', '" + freedom.getText() + "' , '" + megjegyzes.get(0) + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                Refresh();
                search();
                megjegyzes.remove(0);
            }





        }


    }


}


