package VolunteerAppProject.Bot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.eclipse.jetty.server.Server;


import java.util.Properties;

public class BotStarter {

    private Properties properties;

    public BotStarter(Properties properties) {
        this.properties = properties;
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

    public void startBotServer() throws Exception {
        HttpTransportClient client = new HttpTransportClient();
        VkApiClient apiClient = new VkApiClient(client);

        if (groupId() == 0 || groupToken() == null) throw new RuntimeException("Params are not set");
        GroupActor actor = new GroupActor(groupId(), groupToken());

        BotRequestHandler botHandler = new BotRequestHandler(apiClient, actor);

        Server server = new Server(botPort());

        server.setHandler(new RequestHandler(botHandler, properties.getProperty("confirmationCode")));

        server.start();
        server.join();
    }

}
