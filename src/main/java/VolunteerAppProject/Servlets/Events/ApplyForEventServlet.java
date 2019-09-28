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

@WebServlet(name = "ApplyForEventServlet", urlPatterns = { "api/events/applyForEvent" })
public class ApplyForEventServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");
        String responseString = "";


        if (authToken != null && authToken.equals(ServerStarter.token()))
            responseString = getApplyStatusJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("NO POST METHOD");

        request.getParts();

        System.out.println("POST");
    }

    private String getApplyStatusJson() {
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("apply_status", "success");
            jsonGenerator.writeStringField("message", "Вы были успешно записаны на мероприятие");
            jsonGenerator.writeEndObject();

            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }
}
