package VolunteerAppProject.Servlets.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetRatingServlet", urlPatterns = { "/api/user/getRating" })
public class GetRatingServlet extends HttpServlet {

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

        double rating = 4.5;
        response.getWriter().println(rating);

        System.out.println("GET RATING");
    }
}
