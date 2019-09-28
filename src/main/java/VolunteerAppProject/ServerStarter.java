package VolunteerAppProject;

import VolunteerAppProject.Servlets.Events.GetActualEventsServlet;
import VolunteerAppProject.Servlets.Events.GetEventServlet;
import VolunteerAppProject.Servlets.User.GetRatingServlet;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.*;
import java.util.Properties;
import java.net.URL;
import java.security.ProtectionDomain;

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

        // HTTP connector
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);

        // HTTPS configuration
        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        // Configuring SSL
        SslContextFactory sslContextFactory = new SslContextFactory();


        sslContextFactory.setKeyStorePath("/home/ubuntu/keystore");
        sslContextFactory.setKeyStorePassword("vol2019");
        sslContextFactory.setKeyManagerPassword("vol2019");

        // Configuring the connector
        ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https));
        sslConnector.setPort(443);

        // Setting HTTP and HTTPS connectors
        server.setConnectors(new Connector[]{connector, sslConnector});

        setServlets(server);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setServlets(Server server) {
        ServletContextHandler serverHandler = new ServletContextHandler(server, "/");

        serverHandler.addServlet(GetRatingServlet.class, "/api/user/getRating");
        serverHandler.addServlet(GetActualEventsServlet.class, "/api/events/getActualEvents");
        serverHandler.addServlet(GetEventServlet.class, "/api/events/getEvent");
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
