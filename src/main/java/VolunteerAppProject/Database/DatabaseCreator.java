package VolunteerAppProject.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseCreator {

//    private static final String url = "jdbc:mysql://95.213.37.11:3306/VolunteerAppDatabase";
    private static final String url = "jdbc:mysql://demo11.alpha.vkhackathon.com:3306/VolunteerAppDatabase";
    private static final String user = "AppServer";
    private static final String password = "vol2019";

    private static Connection connection;
    private static Statement stmt;



    private static final String createVolunteersTable = "CREATE TABLE Volunteer (\n" +
            "    id INT primary key AUTO_INCREMENT,\n" +
            "    vk_id INT NOT NULL,\n" +
            "    surname TEXT,\n" +
            "    first_name TEXT,\n" +
            "    second_name TEXT,\n" +
            "    birthday DATE,\n" +
            "    sex TEXT,\n" +
            "    email TEXT,\n" +
            "    phone TEXT,\n" +
            "    occupation TEXT,\n" +
            "    speciality TEXT,\n" +
            "    langs TEXT,\n" +
            "    volunteer_experience TEXT,\n" +
            "    children_work_experience TEXT,\n" +
            "    skills TEXT,\n" +
            "    expectations TEXT,\n" +
            "    medical_contraindications TEXT,\n" +
            "    food_preferences TEXT,\n" +
            "    clothes_size TEXT,\n" +
            "    information_source TEXT,\n" +
            "    mailing_agreement BOOL" +
            ")";

    public void main(String[] args) {
        initDB();
    }

    public static void initDB(){

        Scanner in = new Scanner(System.in);
//        password = in.next();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();


            PreparedStatement preparedCreateMessagesTableStatement = connection.prepareStatement(createVolunteersTable);
            preparedCreateMessagesTableStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
