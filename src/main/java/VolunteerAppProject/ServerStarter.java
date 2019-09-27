package VolunteerAppProject;

import VolunteerAppProject.Servlets.TestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServerStarter {

    public static void main(String [] args) {

        Server server = new Server(8080);

        ServletContextHandler getFileHandler = new ServletContextHandler(server, "/test");
        getFileHandler.addServlet(TestServlet.class, "/");


        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
