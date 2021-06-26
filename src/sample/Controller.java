package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DBConnect;
import model.Story;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField passwordInput;
    public TableView<Story> tableView;
    public TextField usernameInput;
    public TextField dateInput;
    public TableColumn<Story,String> userTable;
    public TableColumn<Story,String> passwordColumn;
    public TableColumn<Story,String> commentColumn;
    public TableColumn<Story,String> dateColumn;
    public ChoiceBox choiceCategory;
    public ChoiceBox choiceCommit;

    public void addClicked(ActionEvent actionEvent) {
        try {
            if (choiceCommit.getValue() == "personal use") {
                String valueCommit = "personal use";
                DBConnect.updateDb("INSERT INTO mysql.passwords (password,username,comments,date) VALUES('" + passwordInput.getText() + "', '" + usernameInput.getText() + "', '" + valueCommit + "','" + dateInput.getText() + "') ");
                tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords;"));
            }else{
                String valueCommit = "public use";
                DBConnect.updateDb("INSERT INTO mysql.passwords (password,username,comments,date) VALUES('" + passwordInput.getText() + "', '" + usernameInput.getText() + "', '" + valueCommit + "','" + dateInput.getText() + "') ");
                tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords;"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableView.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords;"));
            userTable.setCellValueFactory(new PropertyValueFactory<Story, String>("username"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<Story, String>("password"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<Story, String>("date"));
            commentColumn.setCellValueFactory(new PropertyValueFactory<Story, String>("comment"));
            choiceCategory.getItems().addAll("personal use", "public use", "all");
            choiceCategory.setValue("all");
            choiceCommit.getItems().addAll("personal use", "public use");
            choiceCommit.setValue("personal use");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void clear(ActionEvent e) {
        DBConnect.clearTable();
        try {
            tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords;"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tableView.refresh();
    }

    public void sorting(ActionEvent e) {
        String valueCheck;
        if (choiceCategory.getValue() == "personal use"){
            try {
                valueCheck = "personal use";
                //DBConnect.sortTable(valueCheck);
                tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords WHERE comments = 'personal use';"));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            tableView.refresh();
        }else if (choiceCategory.getValue() == "public use") {
            try {
                valueCheck = "public use";
                //DBConnect.sortTable(valueCheck);
                tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords WHERE comments = 'public use';"));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            tableView.refresh();
        }else if(choiceCategory.getValue() == "all"){
            try {
                tableView.setItems(DBConnect.getDataFromDb("SELECT * FROM mysql.passwords;"));
            }catch (Exception exception) {
                exception.printStackTrace();
            }
            tableView.refresh();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка!");
            alert.setContentText("Что-то случилось при выборе категории сортировки");
            alert.show();
        }
    }
}
