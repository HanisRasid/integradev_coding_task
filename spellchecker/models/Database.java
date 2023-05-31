package spellchecker.models;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.*;

import java.util.UUID;

public class Database {
  private Connection connection;

  public boolean createTable() {

    String query = "create table if not exists words (id varchar(36) NOT NULL, word varchar(255) NOT NULL, primary key (id))";

    try {
      Statement statement = this.connection.createStatement();
      statement.setQueryTimeout(30);
      // run the query
      statement.executeUpdate(query);
    } catch (SQLException ex) {
      System.out.println(ex);
      return false;
    }

    return true;
  }

  public boolean resetDatabase() {
    String query = "drop table if exists words";

    // log event
    System.out.println("Resetting database");

    try {
      Statement statement = this.connection.createStatement();
      statement.setQueryTimeout(30);
      // run the query
      statement.executeUpdate(query);
    } catch (SQLException ex) {
      System.out.println(ex);
      return false;
    }

    // log event
    System.out.println("Reset database");

    return true;
  }

  public int getWordCount() {

    String query = "select count(*) as count from words";
    int count = 0; // default
    String countStr;

    try {
      Statement statement = this.connection.createStatement();
      statement.setQueryTimeout(30);

      // run query
      ResultSet set = statement.executeQuery(query);
      countStr = set.getString("count");
      count = Integer.valueOf(countStr);
      System.out.println("count = " + String.valueOf(count));

    } catch (SQLException ex) {
      System.out.println(ex);
    }

    return count;
  }

  public boolean addWordToDatabase(String word) {

   String baseQuery =  "insert into words (id, word) values ";
   String uuid = UUID.randomUUID().toString();

   String query = baseQuery + "(\"" + uuid + "\", \"" + word + "\")";



   try {
     Statement statement = this.connection.createStatement();
     statement.setQueryTimeout(30);
     statement.executeUpdate(query);
   } catch (SQLException ex) {
     System.out.println(ex);
     return false;
   }

   return true;
  }

  public ArrayList<String> getWords() {

    System.out.println("Getting all words");
    String query = "select * from words";
    String word;

    ArrayList<String> words = new ArrayList<String>();

    try {
      Statement statement = this.connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet set = statement.executeQuery(query);
      // populate the arraylist
      while(set.next()) {
        set.getString("id");
        word = set.getString("word");

        words.add(word);
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    }

    return words;
  }

  public boolean checkWord(String word) {
    /**
     * Check if a word is in the database
     */
    int count;

    if(word.length() == 0) {
      return true;
    }

    // check for upper case
    word = word.substring(0, 1).toLowerCase() + word.substring(1);

    // remove punctuation using regex
    word = word.replaceAll("[^a-zA-Z ]", "");


    String query = "select count(*) as count from words where word = '" + word + "'";

    try {
      Statement statement = this.connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet set = statement.executeQuery(query);
      count = Integer.parseInt(set.getString("count"));

      // check the query
      if(count > 0) {
        return true;
      }
    } catch (SQLException ex) {
      return false;
    }

    return false;
  }

  public boolean seedDatabase() {
    // seed the database
    System.out.println("Seeding database, please wait.");
    String mainDir = System.getProperty("user.dir") + '/' + "spellchecker";
    File f = new File(mainDir + '/' + "dictionary/" + "dictionary.txt");
    String word;

    try {
      BufferedReader reader = new BufferedReader(new FileReader(f));
      while ((word = reader.readLine()) != null) {
        addWordToDatabase(word);
      }
      reader.close();
    } catch (FileNotFoundException ex) {
      // file does not exist
      System.out.println(ex);
    } catch (IOException ex) {
      // error reading file
      System.out.println(ex);
    }

    System.out.println("Seeded database");
    return true;
  }

  public Connection getConnection() {
    String fileName = "test.db";
    String mainDir = System.getProperty("user.dir") + '/' + "spellchecker";
    String destinationDirectory =  mainDir + "/" + "db";
    String url = "jdbc:sqlite:" + destinationDirectory + "/" + fileName;

    try {
      Connection conn = DriverManager.getConnection(url);
      if (conn != null) {
        this.connection = conn;
        return conn;
      }
    } catch (SQLException e) {
      System.out.println(e);
    }

    return null;

  }

  public void run() {
    System.out.println("Running database");

    try {
      Class.forName("org.sqlite.JDBC");
    } catch (Exception ex) {
      System.out.println(ex);
    }

    /**
     * Create the new database, if it doesn't already exist
     * and seed it if necessary
     */
    String fileName = "test.db";
    String mainDir = System.getProperty("user.dir") + '/' + "spellchecker";
    String destinationDirectory =  mainDir + "/" + "db";
    String url = "jdbc:sqlite:" + destinationDirectory + "/" + fileName;

    boolean resetDatabase = false;
    boolean seedDatabase = true;
    boolean printDatabase = true;
    // print out the database URL
    System.out.println("URL is " + url);

    try {
      Connection conn = DriverManager.getConnection(url);
      if (conn != null) {
        this.connection = conn;
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("Database driver name is " + meta.getDriverName());

        // reset the database
        if (resetDatabase) {
          resetDatabase();
        }

        // create the words table
        createTable();

        // seed database if empty
        boolean empty = (getWordCount() == 0);
        if (empty && seedDatabase) {
          seedDatabase();
        }

        // print all the words
        if(printDatabase) {
          ArrayList<String> words = getWords();
          for(int i=0; i<words.size(); i++) {
            System.out.println(words.get(i));
          }
          System.out.println();
        }

      }
    } catch (SQLException e) {
      // Print SQL Exception
      System.out.println(e.getMessage());
    }
  }

}
