package VolunteerAppProject.Database;

import VolunteerAppProject.ServerStarter;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataBase {

    private static Properties properties;

    private static String url() throws FileNotFoundException {
//        return properties.getProperty("dataBaseConnectionString");
        return "jdbc:mysql://demo11.alpha.vkhackathon.com:3306/VolunteerAppDatabase";
    }

    private static String user(){
//        return properties.getProperty("dataBaseUserLogin");
        return "AppServer";
    }
    private static String password(){
//        return properties.getProperty("dataBaseUserPassword");
        return "vol2019";
    }

    private static Connection connection;

    public static void connectDB(){

        try {
            properties = ServerStarter.readProperties();

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url(), user(), password());
//            stmt = connection.createStatement();

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
        connectDB();

        Integer vkId = null;
        try {
            vkId = Integer.parseInt(vk_id);
        } catch (NumberFormatException e){
            System.out.println("Ошибка при парсинге");
            e.printStackTrace();
            return false;
        }

        Integer newSex = null;

        try {
            newSex = Integer.parseInt(sex);
        } catch (NumberFormatException e){
            newSex = 0;
        }

        boolean mailingAgreement = Boolean.getBoolean(mailing_agreement);

        System.out.println("ReadyToInsert");

        return insertNewUser(
                vkId,
                surname,
                first_name,
                second_name,
                birthday,
                newSex,
                email,
                phone,
                occupation,
                langs,
                volunteer_experience,
                children_work_experience,
                skills,
                expectations,
                medical_contraindications,
                specialty,
                food_preferences,
                clothes_size,
                information_source,
                mailingAgreement
        );
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

    private static Boolean insertNewUser(
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
        prepareInt(preparedStatement, vk_id, 1);
        prepareString(preparedStatement, surname, 2);
        prepareString(preparedStatement, first_name, 3);
        prepareString(preparedStatement, second_name, 4);
        prepareString(preparedStatement, birthday, 5);
        prepareInt(preparedStatement, sex, 6);
        prepareDouble(preparedStatement, -1.0, 7);
        prepareDouble(preparedStatement, -1.0, 7);
        prepareString(preparedStatement, email, 8);
        prepareString(preparedStatement, phone, 9);
        prepareString(preparedStatement, occupation, 10);
        prepareString(preparedStatement, langs, 11);
        prepareString(preparedStatement, volunteer_experience, 12);
        prepareString(preparedStatement, children_work_experience, 13);
        prepareString(preparedStatement, skills, 14);
        prepareString(preparedStatement, expectations, 15);
        prepareString(preparedStatement, medical_contraindications, 16);
        prepareString(preparedStatement, specialty, 17);
        prepareString(preparedStatement, food_preferences, 18);
        prepareString(preparedStatement, clothes_size, 19);
        prepareString(preparedStatement, information_source, 20);
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

    private static boolean insertContent(PreparedStatement preparedStatement){
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

    private static PreparedStatement getPreparedStatement(String query){
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("Unable to create preparedStatement");
            e.printStackTrace();
            return null;
        }
    }

    private static void prepareString(PreparedStatement preparedStatement, String content, int contentIndex){
        try {
            if (content == null)
                content = "";
            preparedStatement.setString(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query string \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private static void prepareBool(PreparedStatement preparedStatement, Boolean content, int contentIndex){
        try {
            if (content == null)
                content = false;
            preparedStatement.setBoolean(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query string \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private static void prepareInt(PreparedStatement preparedStatement, Integer content, int contentIndex){
        try {
            if (content == null)
                content = -1;
            preparedStatement.setInt(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query int \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private static void prepareDouble(PreparedStatement preparedStatement, Double content, int contentIndex){
        try {
            if (content == null)
                content = -1.0;
            preparedStatement.setDouble(contentIndex, content);
        } catch (SQLException e) {
            System.out.println("In query int \"" + content + "\" error");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("preparedStatement null");
            e.printStackTrace();
        }
    }

    private static void prepareDate(PreparedStatement preparedStatement, Date content, int contentIndex){
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
//        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    }
}
