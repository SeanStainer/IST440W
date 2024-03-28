package application;

import java.io.File;

import javax.xml.crypto.Data;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Main extends Application {
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            primaryStage.setTitle("Loader Application");
            
            //create a gridpane layout
            GridPane grid = new GridPane();
            
            //sets spacing AROUND the entire grid
            grid.setPadding(new Insets(10, 10, 10, 10));
            
            //sets vertical spacing BETWEEN rows and columns 
            grid.setVgap(20);
            //sets horizontal spacing BETWEEN rows and columns 
            grid.setHgap(10);
            
            //create a label
            Label login = new Label("Database Login");
            GridPane.setConstraints(login, 1, 0);
            
            Label user = new Label("Username: ");
            GridPane.setConstraints(user, 0, 1);
            
            TextField name = new TextField("Database username");
            GridPane.setConstraints(name, 1, 1);
            
            Label password = new Label("Password: ");
            GridPane.setConstraints(password, 0, 2);
            
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            GridPane.setConstraints(passwordField, 1, 2);
            
            // IP Address field
            Label ipAddress = new Label("IP Address: ");
            GridPane.setConstraints(ipAddress, 0, 3);
            
            TextField ipInput = new TextField("IP Address");
            GridPane.setConstraints(ipInput, 1, 3);
            
            Button submitBtn = new Button("Log In");
            GridPane.setConstraints(submitBtn, 1, 4);
            
            Label upload = new Label("Upload Data");
            GridPane.setConstraints(upload, 4, 0);
            
            // File Selector
            FileChooser fileChooser = new FileChooser();
            Button selectFileBtn = new Button("Select File");
            selectFileBtn.setOnAction(e -> {
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
            });
            GridPane.setConstraints(selectFileBtn, 4, 1);
            
            Button uploadBtn = new Button("Upload");
            GridPane.setConstraints(uploadBtn, 4, 2);
            
            grid.getChildren().addAll(user, name, password, passwordField, submitBtn, selectFileBtn, uploadBtn, login, upload, ipAddress, ipInput);
            
            TableView<Data> table = new TableView<>();
            
            TableColumn<Data, String> nameColumn = new TableColumn<>("File Name");
            nameColumn.setMinWidth(150);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            
            // MySQL Server column
            TableColumn<Data, String> mysqlColumn = new TableColumn<>("MySQL Server");
            mysqlColumn.setMinWidth(150);
            mysqlColumn.setCellValueFactory(new PropertyValueFactory<>("mysqlServer"));
            
            table.getColumns().addAll(nameColumn, mysqlColumn);
            
            Button connectBtn = new Button("Connect");
            
            VBox layout = new VBox();
            layout.getChildren().addAll(grid, table, connectBtn);
            VBox.setMargin(connectBtn, new Insets(10, 0, 0, 0)); // Add top margin to Connect button
            layout.setAlignment(Pos.CENTER); // Center the Connect button
            
            Scene scene = new Scene(layout,550,550);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}