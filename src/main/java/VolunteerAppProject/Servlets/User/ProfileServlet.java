package VolunteerAppProject.Servlets.User;

import VolunteerAppProject.ServerStarter;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "GetActualEventsServlet", urlPatterns = { "/api/user/Profile" })
public class ProfileServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("OK");

        String authToken = request.getParameter("auth");
        String user_id = request.getParameter("user_id");;
//        int vk_id = request.getParameter("vk_id");
        String surname = request.getParameter("surname");
        String first_name = request.getParameter("first_name");
        String second_name = request.getParameter("second_name");
        String birthday = request.getParameter("birthday");
        int sex = Integer.parseInt(request.getParameter("sex"));
        String email = request.getParameter("email");
        String phone  = request.getParameter("phone");
        String occupation  = request.getParameter("occupation");
        String langs  = request.getParameter("langs");
        String volunteer_experience  = request.getParameter("volunteer_experience");
        String children_work_experience  = request.getParameter("children_work_experience");
        String skills  = request.getParameter("skills");
        String expectations  = request.getParameter("expectations");
        String medical_contraindications  = request.getParameter("medical_contraindications");
        String specialty  = request.getParameter("specialty");
        String food_preferences  = request.getParameter("food_preferences");
        String clothes_size  = request.getParameter("clothes_size");
        String information_source  = request.getParameter("information_source");
        Boolean mailing_agreement  = Boolean.getBoolean(request.getParameter("mailing_agreement"));

        System.out.println(user_id + "\n" + surname + "\n" + first_name + "\n" +
                second_name + "\n" + birthday+ "\n" + sex + "\n" + email + "\n" + phone + "\n" +
                occupation + "\n" + langs + "\n" + volunteer_experience + "\n" + children_work_experience + "\n" +
                skills + "\n" + expectations + "\n" +
                medical_contraindications + "\n" + specialty + "\n" + food_preferences + "\n" + clothes_size
                        + "\n" + information_source + "\n" + mailing_agreement
                );


        request.getParts();

        System.out.println("POST");
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
