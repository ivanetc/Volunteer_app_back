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

            GetActualEventsServlet.addEventToJson(jsonGenerator,  "VK Hackathon", "27 - 29 сентября 2019" ,7,103600381, false, true, String.valueOf(745687), String.valueOf(78062857));
            GetActualEventsServlet.addEventToJson(jsonGenerator,  "Конференция «Менеджмент Будущего»", "4 - 6 апреля 2019" ,5,33351123, false, true, String.valueOf(52298374), String.valueOf(78062857));
            GetActualEventsServlet.addEventToJson(jsonGenerator,  "Неделя когнитивных экспериментов", " 20 - 24 августа 2019" ,6,169749729, false, true, String.valueOf(52298374), String.valueOf(78062857));

            jsonGenerator.writeEndArray();
            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }

    }
}
