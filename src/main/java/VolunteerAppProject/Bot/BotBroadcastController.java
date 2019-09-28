package VolunteerAppProject.Bot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.Random;

public class BotBroadcastController {

    private final VkApiClient apiClient;

    private final GroupActor actor;
    private final Random random;

    public BotBroadcastController(VkApiClient apiClient, GroupActor actor) {
        super();
        this.apiClient = apiClient;
        this.actor = actor;
        random = new Random();
    }

    public void broadcast(Integer[] ids, String message) {
        try {
            apiClient.messages().sendWithUserIds(actor, ids).message(message).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
