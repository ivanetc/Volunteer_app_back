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

@WebServlet(name = "CreateEventServlet", urlPatterns = { "api/events/createEvent" })
public class CreateEventServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");
        String responseString = "";


        if (authToken != null && authToken.equals(ServerStarter.token()))
            responseString = getCreateEventStatusJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("NO GET METHOD");

        request.getParts();

        System.out.println("POST");
    }

    private String getCreateEventStatusJson() {
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("create_event_status", "success");
            jsonGenerator.writeStringField("message", "Мероприятие было успешно создано");
            jsonGenerator.writeEndObject();

            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }
}
