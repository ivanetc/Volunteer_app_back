package VolunteerAppProject;

import VolunteerAppProject.Bot.BotStarter;
import VolunteerAppProject.Servlets.Events.GetActualEventsServlet;
import VolunteerAppProject.Servlets.User.GetRatingServlet;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.*;
import java.util.Properties;

public class ServerStarter {

    private static Properties properties;

    public static String token() {
        return properties.getProperty("apiToken");
    }

    private static int apiPort() {
        return Integer.parseInt(properties.getProperty("apiPort"));
    }

    private static final String PROPERTIES_FILE = "config.properties";


    public static void main(String[] args) throws FileNotFoundException {
        properties = readProperties();

        startApiServer();

        BotStarter botStarter = new BotStarter(properties);
        try {
            botStarter.startBotServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startApiServer() {
        Server server = new Server(apiPort());

        ServletContextHandler serverHandler = new ServletContextHandler(server, "/");

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

    private static Properties readProperties() throws FileNotFoundException {
        InputStream inputStream = ServerStarter.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream == null)
            throw new FileNotFoundException("property file '" + PROPERTIES_FILE + "' not found in the classpath");
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Incorrect properties file");
        }
    }
}
