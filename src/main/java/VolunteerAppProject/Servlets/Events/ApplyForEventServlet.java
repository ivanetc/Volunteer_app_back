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


        response.getWriter().println("NO POST METHOD");

        request.getParts();

        System.out.println("POST");

    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");

        String eventId = request.getParameter("event_id");
        String userVkId = request.getParameter("user_vk_id");
        String timePeriodIds = request.getParameter("time_period_ids");

        System.out.println("ApplyForEventServlet: " + eventId + " \n " + userVkId + " \n " + timePeriodIds);

        String responseString = "";

        if (authToken != null && authToken.equals(ServerStarter.token()))
            responseString = ServerStarter.getRequestStatusJson(true);
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }


}
