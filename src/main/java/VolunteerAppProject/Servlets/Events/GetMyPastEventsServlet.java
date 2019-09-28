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

@WebServlet(name = "GetMyPastEventsServlet", urlPatterns = { "/api/events/getMyPastEvents" })
public class GetMyPastEventsServlet extends HttpServlet {

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
            responseString = getPastEventsJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

    private String getPastEventsJson(){
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader
            jsonGenerator.writeStartArray();

            GetActualEventsServlet.addEventToJson(jsonGenerator,  "Имя мероприятия", "17 января 1970 14.30 - 15.00" ,5,367842, false, true, String.valueOf(127845), String.valueOf(145785));
            GetActualEventsServlet.addEventToJson(jsonGenerator,  "Имя мероприятия", "17 января 1970 14.30 - 15.00" ,6,147965, false, true, String.valueOf(745687), String.valueOf(206874));
            GetActualEventsServlet.addEventToJson(jsonGenerator,  "Имя мероприятия", "17 января 1970 14.30 - 15.00" ,7,379425, false, true, String.valueOf(745687), String.valueOf(206874));
            GetActualEventsServlet.addEventToJson(jsonGenerator,  "Имя мероприятия", "17 января 1970 14.30 - 15.00" ,8,164758, false, true, "", String.valueOf(206874));

            jsonGenerator.writeEndArray();
            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }

    }
}
