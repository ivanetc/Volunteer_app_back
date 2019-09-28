package VolunteerAppProject.Bot;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.Random;

public class BotRequestHandler extends CallbackApi {

    private final VkApiClient apiClient;

    private final GroupActor actor;
    private final String secretKey;
    private final Random random;

    public BotRequestHandler(VkApiClient apiClient, GroupActor actor, String secretKey) {
        super();
        this.apiClient = apiClient;
        this.actor = actor;
        this.secretKey = secretKey;
        random = new Random();
    }

    @Override
    public void messageNew(Integer groupId, String secret, Message message) {
        if (secret.equals(secretKey)) {
            try {
                apiClient.messages().send(actor).message("Hello my friend!").userId(message.getFromId()).randomId(random.nextInt()).execute();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
        }
    }
}
