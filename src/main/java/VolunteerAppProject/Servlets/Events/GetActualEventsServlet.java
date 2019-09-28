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

        response.getWriter().println(responseString);
    }

    private String getActualEventsJson(){
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader
            jsonGenerator.writeStartArray();

            addEventToJson(jsonGenerator, 546785, true, true, String.valueOf(127845), String.valueOf(145785));
            addEventToJson(jsonGenerator, 542985, false, false, String.valueOf(745687), String.valueOf(206874));
            addEventToJson(jsonGenerator, 547585, false, true, String.valueOf(745687), String.valueOf(206874));
            addEventToJson(jsonGenerator, 478185, true, false, "", String.valueOf(206874));

            jsonGenerator.writeEndArray();
            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }

    private void addEventToJson(
            JsonGenerator jsonGenerator,
            int eventId,
            boolean isOpenToApply,
            boolean isUserApplied,
            String organizerId,
            String managerId
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("vk_id",  String.valueOf(eventId));
        jsonGenerator.writeStringField("is_open_to_apply",  String.valueOf(isOpenToApply));
        jsonGenerator.writeStringField("is_user_applied",  String.valueOf(isUserApplied));
        jsonGenerator.writeStringField("organizer_id",  organizerId);
        jsonGenerator.writeStringField("manager_id",  managerId);
        jsonGenerator.writeEndObject();
    }
}
