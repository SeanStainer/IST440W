package Controller;

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

public class ArcGISController {

	public static void main(String[] args) 
	{
		

	}
	public static String allData
	(CheckBox box1,CheckBox box2, CheckBox box3, CheckBox box4, CheckBox box5,
	 CheckBox box6, CheckBox box7, ChoiceBox<String> topicList, 
	 ChoiceBox<String> standingList, ChoiceBox<String> regionList, 
	 ChoiceBox<String> partyList, ChoiceBox<String> genderList, TextField name,
	 TextField date, TextField hashtag)
	{
		String allSelected = "";
		String container = "";
		char con = ' ';
		int num;
		
		container = nameData(name);
		allSelected = allSelected.concat(container + ", ");
		container = dateData(date);
		allSelected = allSelected.concat(container + ", ");
		container = hashtagData(hashtag);
		allSelected = allSelected.concat(container + ", ");
		container = TopicData(topicList);
		allSelected = allSelected.concat(container + ", ");
		con = GenderData(genderList);
		allSelected = allSelected.concat("Gender: " + con + ", ");
		con = StandingData(standingList);
		allSelected = allSelected.concat("Standing: " + con + ", ");
		con = PartyData(partyList);
		allSelected = allSelected.concat("Party: " + con + ", ");
		num = RegionData(regionList);
		allSelected = allSelected.concat("Region " + num + ", ");
		container = numericData(box1, box2, box3, box4, box5, box6, box7);
		allSelected = allSelected.concat(container);
		
		System.out.print("*******************\n\n" + allSelected + "\n\n*******************");
		
		return allSelected;
	}
	
	//text boxes
	public static String nameData(TextField name)
	{
		String results = "Senator name: " + name.getText();
		System.out.println(results);
		return results;
				
	}
	
	public static String dateData(TextField date)
	{
		String results = "Date: " + date.getText();
		System.out.println(results);
		return results;
				
	}
	
	public static String hashtagData(TextField hashtag)
	{
		String results = "Hashtags: " + hashtag.getText();
		System.out.println(results);
		return results;
				
	}
	
	
	public static String TopicData(ChoiceBox<String> list)
	{
		String topic = list.getValue();
		
		System.out.println("Primary topic: " + topic);
		return topic;
	}
	
	public static char PartyData(ChoiceBox<String> list)
	{
		String partySelection = list.getValue();
		char party = ' ';
		
		if(partySelection == "Republican")
		{
			System.out.println("Senators that are Republican");
			party = 'R';
		}
		else if(partySelection == "Democratic")
		{
			System.out.println("Seantors that are Democratic");
			party = 'D';
		}
		else if(partySelection == "Independent")
		{
			System.out.println("Seantors that are Independent");
			party = 'I';
		}
		
		return party;
	}
	
	public static char StandingData(ChoiceBox<String> list)
	{
		String standingSelection = list.getValue();
		char standing = ' ';
		
		if(standingSelection == "Win")
		{
			System.out.println("Senators that won");
			standing = 'W';
		}
		else if(standingSelection == "Loss")
		{
			System.out.println("Seantors that lost");
			standing = 'L';
		}
		
		return standing;
	}
	
	public static char GenderData(ChoiceBox<String> list)
	{
		String genderSelection = list.getValue();
		char gender = ' ';
		
		if(genderSelection == "Male")
		{
			System.out.println("Male Senators selected");
			gender = 'M';
		}
		else if(genderSelection == "Female")
		{
			System.out.println("Female Senators selected");
			gender = 'F';
		}
		
		return gender;
	}
	
	public static int RegionData(ChoiceBox<String> list)
	{
		String region = list.getValue();
		System.out.println("Region selected: " + region);
		int regionID = 0;
		
		if(region == "NorthWest")
		{
			regionID = 1;
		}
		else if(region == "MidWest")
		{
			regionID = 2; 
		}
		else if(region == "South")
		{
			regionID = 3;
		}
		else if(region == "West")
		{
			regionID = 4;
		}
		
		return regionID;
	}
	
	public static String numericData(CheckBox box1,CheckBox box2, CheckBox box3, CheckBox box4, CheckBox box5, CheckBox box6, CheckBox box7)
	{
		
		String data = "";
		
		try
		{
			//retweets
			if(box1.isSelected())
			{
				System.out.println("Retweet was selected");
				data = data.concat("retweet, ");
			}
			//replies
			if(box2.isSelected())
			{
				System.out.println("Replies was selected");
				data = data.concat("replies, ");
			}
			//favorites
			if(box3.isSelected())
			{
				System.out.println("Favorites was selected");
				data = data.concat("favorites, ");
				
			}
			//mentions
			if(box4.isSelected())
			{
				System.out.println("Mentions was selected");
				data = data.concat("mentions, ");
			}
			//quotes
			if(box5.isSelected())
			{
				System.out.println("Quotes was selected");
				data = data.concat("quotes, ");
			}
			//sentiment
			if(box6.isSelected())
			{
				System.out.println("Sentiment was selected");
				data = data.concat("sentiment, ");
			}
			//toxicity
			if(box7.isSelected())
			{
				System.out.println("Toxicity was selected");
				data = data.concat("toxicity, ");
			}
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return data;
	}

}
