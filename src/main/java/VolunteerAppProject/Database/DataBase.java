package VolunteerAppProject.Database;

import VolunteerAppProject.ServerStarter;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataBase {

    private static String url(){
        return ServerStarter.properties.getProperty("dataBaseConnectionString");
    }

    private static String user(){
        return ServerStarter.properties.getProperty("dataBaseUserLogin");
    }
    private static String password(){
        return ServerStarter.properties.getProperty("dataBaseUserPassword");
    }

    private static Connection connection;
    private static Statement stmt;

    public static void initDB(){

        Scanner in = new Scanner(System.in);

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url(), user(), password());
            stmt = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean addNewUser(
            String vk_id,
            String surname,
            String first_name,
            String second_name,
            String birthday,
            String sex,
            String email,
            String phone,
            String occupation,
            String langs,
            String volunteer_experience,
            String children_work_experience,
            String skills,
            String expectations,
            String medical_contraindications,
            String specialty,
            String food_preferences,
            String clothes_size,
            String information_source,
            String mailing_agreement
    ){

        Integer vkId = null;
        try {
            vkId = Integer.parseInt(vk_id);
        } catch (NumberFormatException e){
            return false;
        }

        Integer newSex = null;

        try {
            newSex = Integer.parseInt(sex);
        } catch (NumberFormatException e){
            newSex = 0;
        }

        Boolean mailingAgreement = Boolean.getBoolean(mailing_agreement);
        return false;

    }

    public static boolean addNewEvent(
            String user_vk_id,
            String vk_id,
            String name,
            String description,
            String date,
            String volunteers_task,
            String volunteer_requirements,
            String place,
            String time_periods
    ) {
        Integer vkId = null;
        try {
            vkId = Integer.parseInt(vk_id);
        } catch (NumberFormatException e){
            return false;
        }

        Integer userVkId = null;
        try {
            userVkId = Integer.parseInt(user_vk_id);
        } catch (NumberFormatException e){
            return false;
        }

        String[] t_periods = time_periods.split("$");
        for(String pair : t_periods)
        {
            String[] entry = pair.split("%");
            Integer people_count = null;
            /*try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                people_count = Integer.parseInt(entry[1].trim());


                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }


                insertNewTimeInterval(, entry[0].trim(), people_count);
            } catch (NumberFormatException e){
                return false;
            }*/
        }
        //    private Boolean insertNewTimeInterval(
        //            int event_id,
        //            String time_period,
        //            int people_count
        //    )

        return false;

    }

    private Boolean insertNewUser(
            int vk_id,
            String surname,
            String first_name,
            String second_name,
            String birthday,
            int sex,
            String email,
            String phone,
            String occupation,
            String langs,
            String volunteer_experience,
            String children_work_experience,
            String skills,
            String expectations,
            String medical_contraindications,
            String specialty,
            String food_preferences,
            String clothes_size,
            String information_source,
            boolean mailing_agreement

    ){
        PreparedStatement preparedStatement = getPreparedStatement(SQL.insertNewVolunteer);
        prepareString(preparedStatement, surname, 1);
        prepareString(preparedStatement, first_name, 2);
        prepareString(preparedStatement, second_name, 3);
        prepareString(preparedStatement, birthday, 4);
        prepareInt(preparedStatement, sex, 5);
        prepareDouble(preparedStatement, null, 6);
        prepareString(preparedStatement, email, 7);
        prepareString(preparedStatement, phone, 8);
        prepareString(preparedStatement, occupation, 9);
        prepareString(preparedStatement, langs, 10);
        prepareString(preparedStatement, volunteer_experience, 11);
        prepareString(preparedStatement, children_work_experience, 12);
        prepareString(preparedStatement, skills, 13);
        prepareString(preparedStatement, expectations, 14);
        prepareString(preparedStatement, medical_contraindications, 15);
        prepareString(preparedStatement, specialty, 16);
        prepareString(preparedStatement, food_preferences, 17);
        prepareString(preparedStatement, clothes_size, 18);
        prepareString(preparedStatement, information_source, 19);
        prepareString(preparedStatement, email, 20);
        prepareBool(preparedStatement, mailing_agreement, 21);

        return insertContent(preparedStatement);
    }

    private Boolean insertNewEvent(
            int user_vk_id,
            int vk_id,
            String name,
            String description,
            String date,
            String volunteers_task,
            String volunteer_requirements,
            String place
    ) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL.insertNewEvent);
        prepareInt(preparedStatement, vk_id, 1);
        prepareString(preparedStatement, name, 2);
        prepareString(preparedStatement, description, 3);

        String[] dateList = date.split(".");
        String year = "1970";
        String month = "01";
        String day = "01";
        if (dateList.length == 3) {
            if (dateList[2].length() == 4)
                year = dateList[2];
            if (dateList[1].length() == 2)
                month = dateList[1];
            else if (dateList[1].length() == 1)
                month = String.format("0%s", dateList[1]);
            if (dateList[0].length() == 2)
                day = dateList[0];
            else if (dateList[0].length() == 1)
                day = String.format("0%s", dateList[1]);
        }
        String dateString = String.format("%s-%s-%s", year, month, day);
        prepareDate(preparedStatement, java.sql.Date.valueOf(dateString), 4);

        prepareString(preparedStatement, volunteers_task, 5);
        prepareString(preparedStatement, volunteer_requirements, 6);
        prepareString(preparedStatement, place, 7);

        return insertContent(preparedStatement);
    }

    /*private static Boolean insertNewTimeInterval(
            int event_id,
            String time_period,
            int people_count
    ) {
        PreparedStatement preparedStatement = getPreparedStatement(SQL.insertNewTimeInterval);

        prepareInt(preparedStatement, event_id, 1);
        prepareString(preparedStatement, time_period, 2);
        prepareInt(preparedStatement, people_count, 3);

        return insertContent(preparedStatement);
    }*/

    private boolean insertContent(PreparedStatement preparedStatement){
        try {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException sqlEx){
            System.out.println("Ошибка: Элемент с данным ключом уже существует");
            return false;
        } catch (SQLException sqlEx) {
            System.out.println("Unable to insert preparedStatement");
            sqlEx.printStackTrace();
            return false;
        }
    }

    private PreparedStatement getPreparedStatement(String query){
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("Unable to create preparedStatement");
            e.printStackTrace();
            return null;
        }
    }

    private void prepareString(PreparedStatement preparedStatement, String content, int contentIndex){
        try {
            preparedStatement.setString(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query string \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private void prepareBool(PreparedStatement preparedStatement, Boolean content, int contentIndex){
        try {
            preparedStatement.setBoolean(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query string \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private void prepareInt(PreparedStatement preparedStatement, int content, int contentIndex){
        try {
            preparedStatement.setInt(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query int \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private void prepareDouble(PreparedStatement preparedStatement, Double content, int contentIndex){
        try {
            preparedStatement.setDouble(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query int \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private void prepareDate(PreparedStatement preparedStatement, Date content, int contentIndex){
        try {
            preparedStatement.setDate(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query int \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try { connection.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    }

//    private static
}
