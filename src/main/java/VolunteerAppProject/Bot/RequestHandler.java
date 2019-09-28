package VolunteerAppProject.Bot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class RequestHandler extends AbstractHandler {

    private final static String CONFIRMATION_TYPE = "confirmation";
    private final static String MESSAGE_TYPE = "message_new";
    private final static String OK_BODY = "ok";

    private final BotRequestHandler botRequestHandler;
    private final String confirmationCode;
    private final Gson gson;

    public RequestHandler(BotRequestHandler handler, String confirmationCode) {
        this.botRequestHandler = handler;
        this.confirmationCode = confirmationCode;
        this.gson = new GsonBuilder().create();
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            throw new ServletException("This method is unsupported");
        }

        Reader reader = request.getReader();
        JsonObject requestJson = gson.fromJson(reader, JsonObject.class);
        String type = requestJson.get("type").getAsString();

        if (type.equals(CONFIRMATION_TYPE)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println(confirmationCode);
            return;
        }

        if (botRequestHandler.parse(requestJson)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().println(OK_BODY);
        }
    }
}
