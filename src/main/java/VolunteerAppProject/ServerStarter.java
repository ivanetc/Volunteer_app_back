package VolunteerAppProject;

import VolunteerAppProject.Bot.BotStarter;
import VolunteerAppProject.Servlets.Events.GetActualEventsServlet;
import VolunteerAppProject.Servlets.TestServlet;
import VolunteerAppProject.Servlets.User.GetRatingServlet;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServerStarter {

    public static final String token = "oX5n!E2i.VpWpHeo8E6F0q";

    public static void main(String[] args) {

        startApiServer();

        try {
           // BotStarter.startBotServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startApiServer() {
        Server server = new Server(8080);

        ServletContextHandler serverHandler = new ServletContextHandler(server, "/");

        serverHandler.addServlet(TestServlet.class, "/test");
        serverHandler.addServlet(GetRatingServlet.class, "/api/user/getRating");
        serverHandler.addServlet(GetActualEventsServlet.class, "/api/events/getActualEvents");

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAccessDeniedResponce() {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            OutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8); // or Stream, Reader
            jsonGenerator.writeStartObject();

            jsonGenerator.writeStringField("error", "Access denied");

            jsonGenerator.writeEndObject();
            jsonGenerator.close();

            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<error>Server error of creating json</error>";
        }
    }
}
