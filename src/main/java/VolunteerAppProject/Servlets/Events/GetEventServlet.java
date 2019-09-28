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

@WebServlet(name = "GetEventServlet", urlPatterns = { "/api/events/getEvent" })
public class GetEventServlet extends HttpServlet {

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
            responseString = getEventJson();
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

    private String getEventJson(){
        try {

            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader

            jsonGenerator.writeStartObject();

            jsonGenerator.writeStringField("vk_id", String.valueOf(124678));
            jsonGenerator.writeStringField("name", "Название мероприятия с сервера");
            jsonGenerator.writeStringField("description", "Описание мероприятия с сервера");
            jsonGenerator.writeStringField("date", "29.09.19 12.00");
            jsonGenerator.writeStringField("volunteers_task", "Задачи для волонтеров. \n ТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиТекстзадачиачиТекстзадачиТекстзадачи");
            jsonGenerator.writeStringField("volunteer_requirements", "Требования к волонтерам. \n ТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребованийТекстТребований");
            jsonGenerator.writeStringField("place", "Место проведения. Прям адрес. Прям такой длинный-длинный адрес");

            jsonGenerator.writeFieldName("time_periods");
            jsonGenerator.writeStartArray();

            addTimePeriodToJson(jsonGenerator, 1, "27.09.19 14.30 - 18.00", true, false);
            addTimePeriodToJson(jsonGenerator, 2, "27.09.19 18.00 - 29.09.19 12.00", false, true);
            addTimePeriodToJson(jsonGenerator, 3, "29.09.19 12.00 - 16.00", false, false);

            jsonGenerator.writeEndArray();

            jsonGenerator.writeEndObject();


            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }

    private void addTimePeriodToJson(
            JsonGenerator jsonGenerator,
            int timePeriodId,
            String period,
            boolean isApplied,
            boolean isAvailable
    ) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("time_period_id", String.valueOf(timePeriodId));
        jsonGenerator.writeStringField("time_period", period);
        jsonGenerator.writeStringField("is_applied", String.valueOf(isApplied));
        jsonGenerator.writeStringField("is_available", String.valueOf(isAvailable));

        jsonGenerator.writeEndObject();
    }
}
