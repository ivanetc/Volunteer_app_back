package VolunteerAppProject.Servlets.User;

import VolunteerAppProject.Database.DataBase;
import VolunteerAppProject.Database.SQL;
import VolunteerAppProject.ServerStarter;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@WebServlet(name = "GetActualEventsServlet", urlPatterns = { "/api/user/Profile" })
public class GetProfileServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

//        String authToken = request.getParameter("auth");
//        String vk_id = request.getParameter("vk_id");
//
//        System.out.println("UserId " + vk_id);
//
//        String responseString = "";
//
//        DataBase dataBase = new DataBase();
//
//        System.out.println(authToken);
//
//        if (authToken != null && authToken.equals(ServerStarter.token())) {
//            System.out.println("!!!!");
//            try {
//
//                System.out.println("in_try");
//                insertInDb();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            boolean success =  dataBase.addNewUser(
//                    vk_id,
//                    request.getParameter("surname"),
//                    request.getParameter("first_name"),
//                    request.getParameter("second_name"),
//                    request.getParameter("birthday"),
//                    request.getParameter("sex"),
//                    request.getParameter("email"),
//                    request.getParameter("phone"),
//                    request.getParameter("occupation"),
//                    request.getParameter("langs"),
//                    request.getParameter("volunteer_experience"),
//                    request.getParameter("children_work_experience"),
//                    request.getParameter("skills"),
//                    request.getParameter("expectations"),
//                    request.getParameter("medical_contraindications"),
//                    request.getParameter("specialty"),
//                    request.getParameter("food_preferences"),
//                    request.getParameter("clothes_size"),
//                    request.getParameter("information_source"),
//                    request.getParameter("mailing_agreement")
//            );
//            if (success){
//                responseString = ServerStarter.getRequestStatusJson(true);
//                System.out.println("User inserted!");
//            }
//            else {
//                responseString = ServerStarter.getRequestStatusJson(false);
//                System.out.println("False");
//            }
//        }
//        else
//            responseString = ServerStarter.getAccessDeniedResponce();
//
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
//        response.getWriter().println(responseString);
//
//        request.getParts();
//
//        System.out.println("POST");
    }

    private void insertInDb() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://demo11.alpha.vkhackathon.com:3306/VolunteerAppDatabase", "AppServer", "vol2019");
        connection.prepareStatement(SQL.insertNewVolunteer);
        System.out.println("i prepared statement");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");
        String responseString = "";

        if (authToken != null && authToken.equals(ServerStarter.token()))
            responseString = getProfileJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }



    private String getProfileJson(){
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("user_id", String.valueOf(1));
            jsonGenerator.writeStringField("vk_id", String.valueOf(4521));
            jsonGenerator.writeStringField("surname", "Иванец");
            jsonGenerator.writeStringField("first_name", "Александр");
            jsonGenerator.writeStringField("second_name", "Сергеевич");
            jsonGenerator.writeStringField("birthday", "14.05.1998");
            jsonGenerator.writeStringField("sex", String.valueOf(0));
            jsonGenerator.writeStringField("email", "ivanetsas@yandex.ru");
            jsonGenerator.writeStringField("phone", "89052668317");
            jsonGenerator.writeStringField("occupation", "Полиметалл Инжиниринг. Программист-разработчик");
            jsonGenerator.writeStringField("langs", "Английский - норм, Русский - не норм, Java - отвратительно");
            jsonGenerator.writeStringField("volunteer_experience", "ОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпытОпыт");
            jsonGenerator.writeStringField("children_work_experience", "ОпытCДетьмиОпытCДетьмиОпытCДетьмиОпытCДетьмиОпытCДетьмиОпытCДетьмиОпытCДетьмиОпытCДетьми");
            jsonGenerator.writeStringField("skills", "УменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменьяУменья");
            jsonGenerator.writeStringField("expectations", "ОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжиданияОжидания");
            jsonGenerator.writeStringField("medical_contraindications", "МедицинскоеЧто-То(НеЯндексЗдоровье)МедицинскоеЧто-То(НеЯндексЗдоровье)МедицинскоеЧто-То(НеЯндексЗдоровье)МедицинскоеЧто-То(НеЯндексЗдоровье)МедицинскоеЧто-То(НеЯндексЗдоровье)");
            jsonGenerator.writeStringField("specialty", "Профессиональное гадание по коду");
            jsonGenerator.writeStringField("food_preferences", "Ем все, что не прикрученоЕм все, что не прикрученоЕм все, что не прикрученоЕм все, что не прикрученоЕм все, что не прикручено");
            jsonGenerator.writeStringField("clothes_size", "Сергеевич");
            jsonGenerator.writeStringField("information_source", "Не узнавал и не собираюсь о вас узнаватьНе узнавал и не собираюсь о вас узнаватьНе узнавал и не собираюсь о вас узнаватьНе узнавал и не собираюсь о вас узнаватьНе узнавал и не собираюсь о вас узнавать");
            jsonGenerator.writeStringField("mailing_agreement", String.valueOf(true));

            jsonGenerator.writeEndObject();


            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }
}
