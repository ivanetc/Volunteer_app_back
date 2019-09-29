package VolunteerAppProject.Servlets.Events;

import VolunteerAppProject.Database.DataBase;
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

@WebServlet(name = "GetMyOrgEventsServlet", urlPatterns = { "api/events/getMyOrgEvents" })
public class GetMyOrgEventsServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("NO GET METHOD");


        System.out.println("POST");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");
        String responseString = "";

        if (authToken != null && authToken.equals(ServerStarter.token()))
            responseString = getMyOrgEventsJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

    private String getMyOrgEventsJson(){
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            Integer[] volunteers = { 123789, 576485, 124786, 321785};
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader
            jsonGenerator.writeStartArray();

            addEventToJson(jsonGenerator, "Тотальный диктант в Санкт-Петербурге", "4 апреля 2020 14:00",7,64509964, volunteers);
            addEventToJson(jsonGenerator, "VK Hackathon", "27 - 29 сентября 2019" ,7,103600381, volunteers);

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
            Integer[] volunteers
    ) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("event_id",  String.valueOf(eventId));
        jsonGenerator.writeStringField("vk_id",  String.valueOf(eventVkId));
        jsonGenerator.writeStringField("name",  name);
        jsonGenerator.writeStringField("date",  date);

        jsonGenerator.writeFieldName("volunteers");
        jsonGenerator.writeStartArray();
        for (int i = 0; i < volunteers.length; i++){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("volunteer_id", String.valueOf(volunteers[i]));
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }

}
