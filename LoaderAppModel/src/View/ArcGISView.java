package View;
    

import Controller.ArcGISController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ArcGISView extends Application {
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            //create a label for screen name
            Label nameLabel = new Label("Screen Name: ");
            //create a text field
            TextField screenName = new TextField("");
            
            //create a label for date
            Label dateLabel = new Label("Date: ");
            //create a text field
            TextField date = new TextField("YYYY/MM/DD");
            
            HBox hbox1 = new HBox(10);
            hbox1.getChildren().addAll(nameLabel, screenName, dateLabel, date);
            
            
            //create  a label for hasthags
            Label hashtagLabel = new Label("Hashtags: ");
            //create text field
            TextField hashtags = new TextField("\" \" separated");
            
            //create a label for primary topic
            Label topicLabel = new Label("Primary Topic: ");
            //create dropdown for predefined set of topics
            ChoiceBox<String> topicList = new ChoiceBox<>();
            //fill list
            topicList.getItems().addAll("Economy","Environment"); //ADD MORE
            
            //create another hbox
            HBox hbox2 = new HBox(10);
            hbox2.getChildren().addAll(topicLabel, topicList, hashtagLabel, hashtags);
            
            
            
            //create a label for region
            Label regionsLabel = new Label("Region: ");
            
            //<string> means the checkbox will be a collection of strings for Region
            ChoiceBox<String> regionList = new ChoiceBox<>();
            //add items to the region List
            regionList.getItems().addAll("NorthWest", "MidWest", "South", "West");
            
            //create a label for party
            Label partyLabel = new Label("Party: ");
            //collection of strings for party
            ChoiceBox<String> partyList = new ChoiceBox<>();
            //add items to the region List
            partyList.getItems().addAll("Democratic", "Republican", "Independent");
            
            //create a label for Gender
            Label genderLabel = new Label("Gender: ");
            //collection of strings for gender
            ChoiceBox<String> genderList = new ChoiceBox<>();
            //add items to the region List
            genderList.getItems().addAll("Male", "Female");
            
            //create a label for standing
            Label standingsLabel = new Label("Standing: ");
            //collection of strings for standings
            ChoiceBox<String> standingsList = new ChoiceBox<>();
            //add items to the region List
            standingsList.getItems().addAll("Win", "Loss");
            
            //create hbox for label and dropdown
            //region
            HBox hbox3 = new HBox(10);
            hbox3.setPadding(new Insets(10, 30, 10, 30));
            hbox3.getChildren().addAll(regionsLabel, regionList);
            //party
            HBox hbox4 = new HBox(10);
            hbox4.setPadding(new Insets(10, 30, 10, 30));
            hbox4.getChildren().addAll(partyLabel, partyList);
            //gender
            HBox hbox5 = new HBox(10);
            hbox5.setPadding(new Insets(10, 30, 10, 30));
            hbox5.getChildren().addAll(genderLabel, genderList);
            //gender
            HBox hbox6 = new HBox(10);
            hbox6.setPadding(new Insets(10, 30, 10, 30));
            hbox6.getChildren().addAll(standingsLabel, standingsList);
            
            //vbox to store all labels and dropdowns
            VBox vbox1 = new VBox(10);
            vbox1.getChildren().addAll(hbox5, hbox6, hbox4, hbox3);
            
            //create a checkbox - must be javafx NOT awt (awt = java swing not javaFx)
            CheckBox box1 = new CheckBox("Retweets");
            CheckBox box2 = new CheckBox("Replies");
            CheckBox box3 = new CheckBox("Favorites");
            CheckBox box4 = new CheckBox("Mentions");
            CheckBox box5 = new CheckBox("Quotes");
            CheckBox box6 = new CheckBox("Sentiment");
            CheckBox box7 = new CheckBox("Toxicity");
            
            VBox vbox2 = new VBox(10);
            vbox2.setPadding(new Insets(30,30,30,30));
            vbox2.getChildren().addAll( box1, box2,box3, box4, box5, box6, box7);
            
            //store both vboxes
            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(vbox1, vbox2);
            
            //create a button for map generation
            Button btnGenerate = new Button("Generate Map");
            btnGenerate.setOnAction(e -> 
            ArcGISController.allData(box1, box2, box3, box4, box5, box6, box7, 
                    topicList, standingsList, regionList, partyList, genderList, 
                    screenName, date, hashtags));
            //create a button for viewing the database
            Button btnDataset = new Button("Dataset");
            
            HBox buttonBox = new HBox(10);
            buttonBox.setPadding(new Insets(0,30,0,30));
            buttonBox.getChildren().addAll(btnGenerate, btnDataset);
            
            
            //create a vbox layout w/ padding 10 between children and 30 all around vbox
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(30, 30, 30, 30));
            vbox.getChildren().addAll(hbox1, hbox2, hbox, buttonBox);
            
            

            Scene scene = new Scene(vbox,500,400);
            primaryStage.setTitle("CheckBoxes");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
}
