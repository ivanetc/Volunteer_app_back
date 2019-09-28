package VolunteerAppProject.Bot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.Random;

public class BotRequestHandler {

    private final VkApiClient apiClient;

    private final GroupActor actor;
    private final Random random = new Random();

    public BotRequestHandler(VkApiClient apiClient, GroupActor actor) {
        this.apiClient = apiClient;
        this.actor = actor;
    }

    public void handle(int userId) {
        try {
            apiClient.messages().send(actor).message("Hello my friend!").userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException ignored) {
        }
    }
}
