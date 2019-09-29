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

@WebServlet(name = "CreateEventServlet", urlPatterns = { "api/events/createEvent" })
public class CreateEventServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println("NO GET METHOD");

        Boolean success = DataBase.addNewEvent(
                request.getParameter("user_vk_id"),
                request.getParameter("vk_id"),
                request.getParameter("name"),
                request.getParameter("description"),
                request.getParameter("date"),
                request.getParameter("volunteers_task"),
                request.getParameter("volunteer_requirements"),
                request.getParameter("place"),
                request.getParameter("time_periods")
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
            responseString = ServerStarter.getRequestStatusJson(true);
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

}
