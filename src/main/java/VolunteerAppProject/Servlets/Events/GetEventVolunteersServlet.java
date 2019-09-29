package VolunteerAppProject.Servlets.Events;

import VolunteerAppProject.Database.DataBase;
import VolunteerAppProject.ServerStarter;
import com.fasterxml.jackson.core.JsonEncoding;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateEventServlet", urlPatterns = { "api/events/GetEventVolunteers" })
public class GetEventVolunteersServlet  extends HttpServlet {
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
            responseString = ServerStarter.getRequestStatusJson(true);
        else
            responseString = ServerStarter.getAccessDeniedResponce();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }

}
