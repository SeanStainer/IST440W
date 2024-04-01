package Controller;

import Model.Data;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CSVUploader {
    private DatabaseController databaseController;

    public CSVUploader(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void uploadCSV(Data data) throws SQLException, IOException, CsvException {
        Connection connection = databaseController.getConnection();
        CSVReader reader = new CSVReader(new FileReader(data.getFilePath()));
        List<String[]> lines = reader.readAll();

        // Skip the header
        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);

            // Insert the state
            String stateQuery = "INSERT INTO States (state) VALUES (?)";
            PreparedStatement stateStatement = connection.prepareStatement(stateQuery);
            stateStatement.setString(1, line[32]);
            stateStatement.executeUpdate();

            // Insert the region
            String regionQuery = "INSERT INTO Regions (region) VALUES (?)";
            PreparedStatement regionStatement = connection.prepareStatement(regionQuery);
            regionStatement.setString(1, line[31]);
            regionStatement.executeUpdate();

            // Insert the user
            String userQuery = "INSERT INTO Users (screen_name, Gender, StateID, Win, Party, Served, regionID, Incumbent) VALUES (?, ?, (SELECT stateID FROM States WHERE state = ?), ?, ?, ?, (SELECT regionID FROM Regions WHERE region = ?), ?)";
            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(1, line[2]);
            userStatement.setString(2, line[37]);
            userStatement.setString(3, line[32]);
            userStatement.setString(4, line[35]);
            userStatement.setString(5, line[34]);
            userStatement.setInt(6, Integer.parseInt(line[36]));
            userStatement.setString(7, line[31]);
            userStatement.setString(8, line[33]);
            userStatement.executeUpdate();

// Insert the tweet
String tweetQuery = "INSERT INTO Tweets (tweetID, userID, conversationID, createdAt, month, day, year, text, favorites, referenceID, retweets, quotes, replies, isQuoted, isRetweet, isReply, liberal, conservative) VALUES (?, (SELECT userID FROM Users WHERE screen_name = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
PreparedStatement tweetStatement = connection.prepareStatement(tweetQuery);
tweetStatement.setLong(1, Long.parseLong(line[0]));
tweetStatement.setString(2, line[2]);
tweetStatement.setLong(3, Long.parseLong(line[1]));
tweetStatement.setTimestamp(4, Timestamp.valueOf(line[3]));
tweetStatement.setInt(5, Integer.parseInt(line[4]));
tweetStatement.setInt(6, Integer.parseInt(line[5]));
tweetStatement.setInt(7, Integer.parseInt(line[6]));
tweetStatement.setString(8, line[7]);
tweetStatement.setInt(9, Integer.parseInt(line[10]));
tweetStatement.setLong(10, Long.parseLong(line[11]));
tweetStatement.setInt(11, Integer.parseInt(line[12]));
tweetStatement.setInt(12, Integer.parseInt(line[13]));
tweetStatement.setInt(13, Integer.parseInt(line[14]));
tweetStatement.setBoolean(14, Boolean.parseBoolean(line[15]));
tweetStatement.setBoolean(15, Boolean.parseBoolean(line[16]));
tweetStatement.setBoolean(16, Boolean.parseBoolean(line[17]));
tweetStatement.setBoolean(17, Boolean.parseBoolean(line[18]));
tweetStatement.setBoolean(18, Boolean.parseBoolean(line[19]));
tweetStatement.executeUpdate();

// Insert the tweet analysis
String analysisQuery = "INSERT INTO tweetAnalysis (tweetID, profanity, threat, spam, insult, identityAttack, severeToxicity, toxicity, vaderOverall, vaderPos, vaderNeg, vaderNeutral, vader) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
PreparedStatement analysisStatement = connection.prepareStatement(analysisQuery);
analysisStatement.setLong(1, Long.parseLong(line[0]));
analysisStatement.setDouble(2, Double.parseDouble(line[29]));
analysisStatement.setDouble(3, Double.parseDouble(line[30]));
analysisStatement.setDouble(4, Double.parseDouble(line[31]));
analysisStatement.setDouble(5, Double.parseDouble(line[27]));
analysisStatement.setDouble(6, Double.parseDouble(line[26]));
analysisStatement.setDouble(7, Double.parseDouble(line[25]));
analysisStatement.setDouble(8, Double.parseDouble(line[24]));
analysisStatement.setDouble(9, Double.parseDouble(line[21]));
analysisStatement.setDouble(10, Double.parseDouble(line[20]));
analysisStatement.setDouble(11, Double.parseDouble(line[19]));
analysisStatement.setDouble(12, Double.parseDouble(line[18]));
analysisStatement.setBoolean(13, Boolean.parseBoolean(line[22]));
analysisStatement.executeUpdate();

// Insert the hashtags and tweet_hashtags
String[] hashtags = line[8].split(" ");
for (String hashtag : hashtags) {
    // Insert the hashtag
    String hashtagQuery = "INSERT INTO Hashtags (hashtag) VALUES (?)";
    PreparedStatement hashtagStatement = connection.prepareStatement(hashtagQuery);
    hashtagStatement.setString(1, hashtag);
    hashtagStatement.executeUpdate();

    // Insert the tweet_hashtag
    String tweetHashtagQuery = "INSERT INTO tweet_hashtags (tweetID, hashtagID) VALUES (?, (SELECT hashtagID FROM Hashtags WHERE hashtag = ?))";
    PreparedStatement tweetHashtagStatement = connection.prepareStatement(tweetHashtagQuery);
    tweetHashtagStatement.setLong(1, Long.parseLong(line[0]));
    tweetHashtagStatement.setString(2, hashtag);
    tweetHashtagStatement.executeUpdate();
}

// Insert the mentions and tweet_mentions
String[] mentions = line[9].split(" ");
for (String mention : mentions) {
    // Insert the mention
    String mentionQuery = "INSERT INTO Mentions (mention) VALUES (?)";
    PreparedStatement mentionStatement = connection.prepareStatement(mentionQuery);
    mentionStatement.setString(1, mention);
    mentionStatement.executeUpdate();

    // Insert the tweet_mention
    String tweetMentionQuery = "INSERT INTO tweet_mentions (tweetID, mentionID) VALUES (?, (SELECT mentionID FROM Mentions WHERE mention = ?))";
    PreparedStatement tweetMentionStatement = connection.prepareStatement(tweetMentionQuery);
    tweetMentionStatement.setLong(1, Long.parseLong(line[0]));
    tweetMentionStatement.setString(2, mention);
    tweetMentionStatement.executeUpdate();
}
        }

        reader.close();
    }
}