package VolunteerAppProject.Bot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.eclipse.jetty.server.Server;

import java.util.Properties;

public class BotController {

    private Properties properties;
    private GroupActor actor;
    private VkApiClient apiClient;

    private BotBroadcastController botBroadcastController;

    public BotController(Properties properties) {
        this.properties = properties;
        actor = new GroupActor(groupId(), groupToken());
        apiClient = new VkApiClient(new HttpTransportClient());

        botBroadcastController = new BotBroadcastController(apiClient, actor);
    }

    public void broadcast(Integer[] ids, String message) {
        botBroadcastController.broadcast(ids, message);
    }

    public void startBotServer() throws Exception {
        if (groupId() == 0 || groupToken() == null) throw new RuntimeException("Params are not set");

        BotRequestHandler botHandler = new BotRequestHandler(apiClient, actor, properties);
        Server server = new Server(botPort());

        server.setHandler(new RequestHandler(botHandler, properties.getProperty("confirmationCode")));

        server.start();
    }

    private int botPort() {
        return Integer.parseInt(properties.getProperty("botPort"));
    }

    private int groupId() {
        return Integer.parseInt(properties.getProperty("groupId"));
    }

    private String groupToken() {
        return properties.getProperty("groupToken");
    }
}
