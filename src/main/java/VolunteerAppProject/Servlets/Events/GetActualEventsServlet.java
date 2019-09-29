package VolunteerAppProject.Servlets.Events;

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
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "GetActualEventsServlet", urlPatterns = { "/api/events/getActualEvents" })
public class GetActualEventsServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("OK");

        request.getParts();

        System.out.println("POST");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");
        String responseString = "";


        if (authToken != null && authToken.equals(ServerStarter.token()))
            responseString = getActualEventsJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

    private String getActualEventsJson(){
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader
            jsonGenerator.writeStartArray();

            addEventToJson(jsonGenerator, "18-й Санкт-Петербургский Фестиваль Татуировки", "20 июня 2020 12:00 — 21 июня 2020 22:00",5,65337441, false, false, String.valueOf(745687), String.valueOf(206874));
            addEventToJson(jsonGenerator, "Всероссийский фестиваль красок — Санкт-Петербург", "12 июня 2020 15:00 — 14 июня 2020 19:00",6,55031798, true, true, String.valueOf(54162110), String.valueOf(206874));
            addEventToJson(jsonGenerator, "Тотальный диктант в Санкт-Петербурге", "4 апреля 2020 14:00",7,64509964, true, false, String.valueOf(39707896), String.valueOf(206874));
            addEventToJson(jsonGenerator, "JAZZMAN FESTIVAL", "7 марта 17:00",8,144628605, true, true, String.valueOf(157287437), String.valueOf(206874));

            jsonGenerator.writeEndArray();
            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }

    public static void addEventToJson(
            JsonGenerator jsonGenerator,
            String name,
            String date,
            int eventId,
            int eventVkId,
            boolean isOpenToApply,
            boolean isUserApplied,
            String organizerId,
            String managerId
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("event_id",  String.valueOf(eventId));
        jsonGenerator.writeStringField("vk_id",  String.valueOf(eventVkId));
        jsonGenerator.writeStringField("is_open_to_apply",  String.valueOf(isOpenToApply));
        jsonGenerator.writeStringField("name",  name);
        jsonGenerator.writeStringField("date",  date);
        jsonGenerator.writeStringField("is_user_applied",  String.valueOf(isUserApplied));
        jsonGenerator.writeStringField("organizer_id",  organizerId);
        jsonGenerator.writeStringField("manager_id",  managerId);
        jsonGenerator.writeEndObject();
    }
}
