package VolunteerAppProject;

import VolunteerAppProject.Bot.BotController;
import VolunteerAppProject.Servlets.Events.*;
import VolunteerAppProject.Servlets.User.GetRatingServlet;
import VolunteerAppProject.Servlets.User.ProfileServlet;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.*;
import java.util.Properties;

public class ServerStarter {

    private static Properties properties;

    public static String token() {
        return properties.getProperty("apiToken");
    }

    private static int apiHttpPort() {
        return Integer.parseInt(properties.getProperty("apiHttpPort"));
    }

    private static int apiHttpsPort() {
        return Integer.parseInt(properties.getProperty("apiHttpsPort"));
    }

    private static final String PROPERTIES_FILE = "config.properties";


    public static void main(String[] args) throws FileNotFoundException {
        properties = readProperties();

        startApiServer();

        BotController botController = new BotController(properties);
        try {
            botController.startBotServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startApiServer() {
        Server server = new Server(apiHttpPort());

        configureServer(server);
        setServlets(server);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void configureServer(Server server) {
        // HTTP connector
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(apiHttpPort());

        // HTTPS configuration
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        https.setSecureScheme("https");
        https.setSecurePort(apiHttpsPort());

        // Configuring SSL
        SslContextFactory sslContextFactory = new SslContextFactory();

        sslContextFactory.setKeyStorePath("/home/ubuntu/keystore.jks");
        sslContextFactory.setKeyStorePassword("vol2019");
        sslContextFactory.setKeyManagerPassword("vol2019");
        sslContextFactory.setTrustStorePath("/home/ubuntu/keystore.jks");
        sslContextFactory.setTrustStorePassword("vol2019");

        // Configuring the connector
        ServerConnector sslConnector = new ServerConnector(
                server, new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(https)
        );
        sslConnector.setPort(apiHttpsPort());

        // Setting HTTP and HTTPS connectors
        server.setConnectors(new Connector[]{connector, sslConnector});
    }

    private static void setServlets(Server server) {
        ServletContextHandler serverHandler = new ServletContextHandler(server, "/");

        serverHandler.addServlet(GetRatingServlet.class, "/api/user/getRating");
        serverHandler.addServlet(ProfileServlet.class, "/api/user/Profile");
        serverHandler.addServlet(GetActualEventsServlet.class, "/api/events/getActualEvents");
        serverHandler.addServlet(ApplyForEventServlet.class, "/api/events/applyForEvent");
        serverHandler.addServlet(GetMyPastEventsServlet.class, "/api/events/getMyPastEvents");
        serverHandler.addServlet(GetEventServlet.class, "/api/events/getEvent");
        serverHandler.addServlet(CreateEventServlet.class, "/api/events/createEvent");
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
