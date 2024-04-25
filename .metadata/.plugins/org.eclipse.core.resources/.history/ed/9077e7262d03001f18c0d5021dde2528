package Controller;

import Model.Data;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class CSVUploader {
    private DatabaseController databaseController;

    public CSVUploader(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void uploadCSV(Data data) throws SQLException, IOException, CsvException {
        Connection connection = databaseController.getConnection();
        CSVReader reader = new CSVReaderBuilder(new FileReader(data.getFilePath()))
        	    .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        	    .build();
        	List<String[]> lines = reader.readAll();
    
        // Skip the header
        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);
            System.out.println("Line " + i + ": " + Arrays.toString(line));
            
            
            // Insert the region
            String regionQuery = "INSERT INTO Regions (region) VALUES (?)";
            PreparedStatement regionStatement = connection.prepareStatement(regionQuery, Statement.RETURN_GENERATED_KEYS);
            if (line[32] instanceof String) {
                regionStatement.setString(1, line[32]);
                regionStatement.executeUpdate();
            } else {
                System.out.println("Invalid region value: " + line[32]);
            }

                // Get the generated regionID
            ResultSet rsRegion = regionStatement.getGeneratedKeys();
            rsRegion.next();
            int regionID = rsRegion.getInt(1);

            // Insert the user
            String userQuery = "INSERT INTO Users (screenName, Gender, State, Win, Party, Served, regionID, Incumbent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement userStatement = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, line[2]);
            userStatement.setString(2, line[38]);
            userStatement.setString(3, line[33]);
            userStatement.setString(4, line[36]);
            userStatement.setString(5, line[35]);
            userStatement.setString(6, line[37]);
            userStatement.setInt(7, regionID);
            userStatement.setString(8, line[34]);
            userStatement.executeUpdate();
    
            // Get the generated userID
            ResultSet rs = userStatement.getGeneratedKeys();
            rs.next();
            int userID = rs.getInt(1);
    
            // Insert the tweet
            String tweetQuery = "INSERT INTO Tweets (userID, RealTweetID, conversationID, createdAt, month, day, year, text, favorites, referenceID, retweets, quotes, replies, isQuoted, isRetweet, isReply, liberal, conservative) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tweetStatement = connection.prepareStatement(tweetQuery, Statement.RETURN_GENERATED_KEYS);
            tweetStatement.setInt(1, userID);
            tweetStatement.setString(2, line[0]);
            tweetStatement.setString(3, line[1]);
            tweetStatement.setString(4, line[3]);
            tweetStatement.setInt(5, Integer.parseInt(line[4]));
            tweetStatement.setInt(6, Integer.parseInt(line[5]));
            tweetStatement.setInt(7, Integer.parseInt(line[6]));
            tweetStatement.setString(8, line[7]);
            tweetStatement.setInt(9, Integer.parseInt(line[10]));
            tweetStatement.setString(10, line.length > 11 && line[11] != null ? line[11] : "");
            tweetStatement.setInt(11, Integer.parseInt(line[12]));
            tweetStatement.setInt(12, Integer.parseInt(line[13]));
            tweetStatement.setInt(13, Integer.parseInt(line[14]));
            tweetStatement.setBoolean(14, Boolean.parseBoolean(line[16]));
            tweetStatement.setBoolean(15, Boolean.parseBoolean(line[15]));
            tweetStatement.setBoolean(16, Boolean.parseBoolean(line[17]));
            tweetStatement.setInt(17, Integer.parseInt(line[23]));
            tweetStatement.setInt(18, Integer.parseInt(line[24]));
            tweetStatement.executeUpdate();
            // Get the generated tweetID
            ResultSet rsTweet = tweetStatement.getGeneratedKeys();
            rsTweet.next();
            int tweetID = rsTweet.getInt(1);

          // Insert the tweet analysis
            String analysisQuery = "INSERT INTO tweetAnalysis (tweetID, profanity, threat, spam, insult, identityAttack, severeToxicity, toxicity, vaderOverall, vaderPos, vaderNeg, vaderNeutral, vader) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement analysisStatement = connection.prepareStatement(analysisQuery, Statement.RETURN_GENERATED_KEYS);
            analysisStatement.setInt(1, tweetID);
            analysisStatement.setDouble(2, line.length > 29 ? Double.parseDouble(line[29]) : 0);
            analysisStatement.setDouble(3, line.length > 30 ? Double.parseDouble(line[30]) : 0);
            analysisStatement.setDouble(4, line.length > 31 ? Double.parseDouble(line[31]) : 0);
            analysisStatement.setDouble(5, line.length > 28 ? Double.parseDouble(line[28]) : 0);
            analysisStatement.setDouble(6, line.length > 27 ? Double.parseDouble(line[27]) : 0);
            analysisStatement.setDouble(7, line.length > 25 ? Double.parseDouble(line[25]) : 0);
            analysisStatement.setDouble(8, line.length > 26 ? Double.parseDouble(line[26]) : 0);
            analysisStatement.setDouble(9, line.length > 21 ? Double.parseDouble(line[21]) : 0);
            analysisStatement.setDouble(10, line.length > 20 ? Double.parseDouble(line[20]) : 0);
            analysisStatement.setDouble(11, line.length > 18 ? Double.parseDouble(line[18]) : 0);
            analysisStatement.setDouble(12, line.length > 19 ? Double.parseDouble(line[19]) : 0);
            analysisStatement.setInt(13, line.length > 22 ? Integer.parseInt(line[22]) : 0);
            analysisStatement.executeUpdate();
    
            // Insert the hashtags and tweet_hashtags
            
            String[] hashtags = line[8] != null && !line[8].isEmpty() ? line[8].split(" ") : new String[]{""};
            for (String hashtag : hashtags) {
                // Check if the hashtag already exists
                String checkHashtagQuery = "SELECT * FROM Hashtags WHERE hashtag = ?";
                PreparedStatement checkHashtagStatement = connection.prepareStatement(checkHashtagQuery);
                checkHashtagStatement.setString(1, hashtag);
                ResultSet checkHashtagResultSet = checkHashtagStatement.executeQuery();
    
              // If the hashtag does not exist, insert it
                if (!checkHashtagResultSet.next()) {
                    String hashtagQuery = "INSERT INTO Hashtags (hashtag) VALUES (?)";
                    PreparedStatement hashtagStatement = connection.prepareStatement(hashtagQuery, Statement.RETURN_GENERATED_KEYS);
                    hashtagStatement.setString(1, hashtag);
                    hashtagStatement.executeUpdate();

                    // Get the generated hashtagID
                    ResultSet rsHashtag = hashtagStatement.getGeneratedKeys();
                    rsHashtag.next();
                    int hashtagID = rsHashtag.getInt(1);

                    // Insert the tweet_hashtag
                    String tweetHashtagQuery = "INSERT INTO tweet_hashtags (tweetID, hashtagID) VALUES (?, ?)";
                    PreparedStatement tweetHashtagStatement = connection.prepareStatement(tweetHashtagQuery);
                    tweetHashtagStatement.setInt(1, tweetID);
                    tweetHashtagStatement.setInt(2, hashtagID);
                    tweetHashtagStatement.executeUpdate();
                }
            }
        
            // Insert the mentions and tweet_mentions
            
            String[] mentions = line[9] != null && !line[9].isEmpty() ? line[9].split(" ") : new String[]{""};
            for (String mention : mentions) {
                // Check if the mention already exists
                String checkMentionQuery = "SELECT * FROM Mentions WHERE mention = ?";
                PreparedStatement checkMentionStatement = connection.prepareStatement(checkMentionQuery);
                checkMentionStatement.setString(1, mention);
                ResultSet checkMentionResultSet = checkMentionStatement.executeQuery();
    
               // If the mention does not exist, insert it
                if (!checkMentionResultSet.next()) {
                    String mentionQuery = "INSERT INTO Mentions (mention) VALUES (?)";
                    PreparedStatement mentionStatement = connection.prepareStatement(mentionQuery, Statement.RETURN_GENERATED_KEYS);
                    mentionStatement.setString(1, mention);
                    mentionStatement.executeUpdate();

                    // Get the generated mentionID
                    ResultSet rsMention = mentionStatement.getGeneratedKeys();
                    rsMention.next();
                    int mentionID = rsMention.getInt(1);

                    // Insert the tweet_mention
                    String tweetMentionQuery = "INSERT INTO tweet_mentions (tweetID, mentionID) VALUES (?, ?)";
                    PreparedStatement tweetMentionStatement = connection.prepareStatement(tweetMentionQuery);
                    tweetMentionStatement.setInt(1, tweetID);
                    tweetMentionStatement.setInt(2, mentionID);
                    tweetMentionStatement.executeUpdate();
                }
            }
        
      }
    
        reader.close();
    }
}