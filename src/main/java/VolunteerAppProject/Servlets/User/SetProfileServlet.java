package VolunteerAppProject.Servlets.User;

import VolunteerAppProject.Database.DataBase;
import VolunteerAppProject.ServerStarter;
import com.fasterxml.jackson.core.JsonEncoding;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SetProfileServlet", urlPatterns = { "/api/user/setProfile" })
public class SetProfileServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        System.out.println("POST");
    }


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        String authToken = request.getParameter("auth");
        String responseString = "";

        String vk_id = request.getParameter("vk_id");

        System.out.println("UserId " + vk_id);

        System.out.println(authToken);

        if (authToken != null && authToken.equals(ServerStarter.token())) {
            System.out.println("!!!!");
            boolean success =  DataBase.addNewUser(
                    vk_id,
                    request.getParameter("surname"),
                    request.getParameter("first_name"),
                    request.getParameter("second_name"),
                    request.getParameter("museum"),
                    request.getParameter("birthday"),
                    request.getParameter("sex"),
                    request.getParameter("email"),
                    request.getParameter("phone"),
                    request.getParameter("occupation"),
                    request.getParameter("langs"),
                    request.getParameter("volunteer_experience"),
                    request.getParameter("children_work_experience"),
                    request.getParameter("skills"),
                    request.getParameter("expectations"),
                    request.getParameter("medical_contraindications"),
                    request.getParameter("specialty"),
                    request.getParameter("food_preferences"),
                    request.getParameter("clothes_size"),
                    request.getParameter("information_source"),
                    request.getParameter("mailing_agreement")
            );
            if (success){
                responseString = ServerStarter.getRequestStatusJson(true);
                System.out.println("User inserted!");
            }
            else {
                responseString = ServerStarter.getRequestStatusJson(false);
                System.out.println("False");
            }
        }
        else
            responseString = ServerStarter.getAccessDeniedResponce();


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());
        response.getWriter().println(responseString);
    }
}
