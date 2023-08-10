package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class mySQLConnection {
    String url,user,password;
    private Connection connection = null;
    public mySQLConnection(){
        readPropertiesFile();

        try {
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("connecting to ChatServer...>>>");
            System.out.println("connected!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void readPropertiesFile(){
        File file = new File("C:\\Users\\ngcob\\IdeaProjects\\groupchat\\Properties.properties");

        Properties prop = new Properties();

        try {

            InputStream is = new FileInputStream(file);

            prop.load(is);
            url = prop.getProperty("url");
            user = prop.getProperty("buser");
            password = prop.getProperty("bpassword");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    public static void main(String[] args) {
        new mySQLConnection().readPropertiesFile();
    }
}
