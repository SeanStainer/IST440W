package View;

import Controller.CSVUploader;
import Controller.DatabaseController;
import Model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
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
import com.opencsv.exceptions.CsvException;

public class Main extends Application {
    private DatabaseController databaseController = new DatabaseController(); // Declare the databaseController
    private ObservableList<Data> tableData = FXCollections.observableArrayList(); // List to hold table data

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
            
            TextField name = new TextField();
            name.setPromptText("Database username"); // Set prompt text
            GridPane.setConstraints(name, 1, 1);
            
            Label password = new Label("Password: ");
            GridPane.setConstraints(password, 0, 2);
            
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            GridPane.setConstraints(passwordField, 1, 2);
            
            // IP Address field
            Label ipAddress = new Label("IP Address: ");
            GridPane.setConstraints(ipAddress, 0, 3);
            
            TextField ipInput = new TextField();
            ipInput.setPromptText("IP Address"); // Set prompt text
            GridPane.setConstraints(ipInput, 1, 3);
            
            Label upload = new Label("Upload Data");
            GridPane.setConstraints(upload, 4, 0);
            
            // Dataset Name field
            Label datasetName = new Label("Dataset Name: ");
            GridPane.setConstraints(datasetName, 4, 1);
            
            TextField datasetInput = new TextField();
            datasetInput.setPromptText("Dataset Name"); // Set prompt text
            datasetInput.setDisable(true); // Disable the field
            GridPane.setConstraints(datasetInput, 5, 1);
            
            // File Selector
            FileChooser fileChooser = new FileChooser();
            Button selectFileBtn = new Button("\uD83D\uDCC1 Select File");
            selectFileBtn.setDisable(true); // Disable the button
            Label selectedFileName = new Label(); // Label to display the selected file name
            File[] selectedFile = new File[1]; // Array to hold the selected file
            selectFileBtn.setOnAction(e -> {
                selectedFile[0] = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile[0] != null) {
                    selectedFileName.setText(selectedFile[0].getName());
                }
            });
            GridPane.setConstraints(selectFileBtn, 4, 2);
            GridPane.setConstraints(selectedFileName, 5, 2); // Place the label next to the button
            
            Button uploadBtn = new Button("Upload");
            uploadBtn.setDisable(true); // Disable the button
            uploadBtn.setOnAction(e -> {
                if (selectedFile[0] != null) {
                    Data data = new Data(datasetInput.getText(), selectedFile[0].getName(), ipInput.getText(), selectedFile[0].getAbsolutePath());
                    tableData.add(data); // Add the new data to the table data list

                    try {
                            CSVUploader uploader = new CSVUploader(databaseController);
                            uploader.uploadCSV(data);
                        } catch (SQLException | IOException | CsvException ex) {
                            ex.printStackTrace();
                        }
                }
            });
            GridPane.setConstraints(uploadBtn, 4, 3);
            
            Button submitBtn = new Button("Log In");
            Label connectionStatus = new Label();
            submitBtn.setOnAction(e -> {
                try {
                    databaseController.connect(ipInput.getText(), name.getText(), passwordField.getText());
                    connectionStatus.setText("Connection Successful");
                    datasetInput.setDisable(false); // Enable the TextField
                    selectFileBtn.setDisable(false); // Enable the Button
                    uploadBtn.setDisable(false); // Enable the Button
                } catch (SQLException ex) {
                    connectionStatus.setText("Connection Failed");
                }
            });

            // Create a HBox to hold the button and the label
            HBox hbox = new HBox();
            hbox.setSpacing(10); // Set spacing between the button and the label
            hbox.getChildren().addAll(submitBtn, connectionStatus);
            GridPane.setConstraints(hbox, 1, 4); // Place the HBox in the grid
            
            grid.getChildren().addAll(user, name, password, passwordField, hbox, selectFileBtn, uploadBtn, login, upload, ipAddress, ipInput, datasetName, datasetInput, selectedFileName);
            
            TableView<Data> table = new TableView<>();
            table.setItems(tableData); // Set the table's items to the table data list
            
            // Dataset Name column
            TableColumn<Data, String> datasetNameColumn = new TableColumn<>("Dataset Name");
            datasetNameColumn.setMinWidth(150);
            datasetNameColumn.setCellValueFactory(new PropertyValueFactory<>("datasetName"));

            TableColumn<Data, String> nameColumn = new TableColumn<>("File Name");
            nameColumn.setMinWidth(150);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
            
            // MySQL Server column
            TableColumn<Data, String> mysqlColumn = new TableColumn<>("MySQL Server");
            mysqlColumn.setMinWidth(150);
            mysqlColumn.setCellValueFactory(new PropertyValueFactory<>("mysqlServer"));

            // File Directory Path column
            TableColumn<Data, String> filePathColumn = new TableColumn<>("File Directory Path");
            filePathColumn.setMinWidth(150);
            filePathColumn.setCellValueFactory(new PropertyValueFactory<>("filePath"));

            table.getColumns().addAll(datasetNameColumn, nameColumn, mysqlColumn, filePathColumn);
            
            Button connectBtn = new Button("Connect");
            
            VBox layout = new VBox();
            layout.getChildren().addAll(grid, table, connectBtn);
            VBox.setMargin(connectBtn, new Insets(10, 0, 0, 0)); // Add top margin to Connect button
            layout.setAlignment(Pos.CENTER); // Center the Connect button
            
            Scene scene = new Scene(layout,800,550);
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}