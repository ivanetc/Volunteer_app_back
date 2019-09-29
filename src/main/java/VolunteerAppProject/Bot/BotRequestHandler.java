package VolunteerAppProject.Bot;

import com.vk.api.sdk.callback.CallbackApi;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;

import java.util.*;

public class BotRequestHandler extends CallbackApi {

    private final VkApiClient apiClient;

    private final GroupActor actor;
    private final Properties properties;
    private final Random random;
    private HashSet<List<KeyboardButton>> userKeyboard;

    private static final String COMMAND_START = "начать";
    private static final String REFRESH = "кек";
    private static final String GO_TO_APP_FULL = "перейти в приложение";
    private static final String GO_TO_APP_1 = "приложение";

    BotRequestHandler(VkApiClient apiClient, GroupActor actor, Properties properties) {
        super();
        this.apiClient = apiClient;
        this.actor = actor;
        this.properties = properties;
        userKeyboard = new HashSet<>();
        random = new Random();
    }

    @Override
    public void messageNew(Integer groupId, String secret, Message message) {
        if (secret.equals(properties.getProperty("secretKey"))) {
            try {
                if (message.getFromId().equals(message.getPeerId())) { // check private
                    respondPrivate(message, apiClient.messages().send(actor)
                            .userId(message.getFromId()).randomId(random.nextInt()));
                }
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
        }
    }

    private void respondPrivate(Message message, MessagesSendQuery answer) throws ClientException, ApiException {
        String text = message.getText().toLowerCase().trim();
        switch (text) {
            case COMMAND_START:
                addGoToAppButton(answer);
                answer.message(
                        "Привет, это бот волонтёрской программы \"Спутник\"\n" +
                                "Через меня ты можешь перейти в приложение и записаться на мероприятие");
                break;
            case GO_TO_APP_FULL:
            case GO_TO_APP_1:
                addGoToAppButton(answer);
                break;
            case REFRESH:
                clearButtons(answer);
                break;
            default:
                answer.message("Не понимаю");
                break;
        }
        answer.execute();
    }

    private void addGoToAppButton(MessagesSendQuery answer) {
        addRowOfButtons(answer, Collections.singletonList(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setType(KeyboardButtonActionType.OPEN_APP)
                        .setAppId(Integer.parseInt(properties.getProperty("frontAppId")))
                        .setLabel("Перейти в приложение"))
        ));
        answer.message("Нажми кнопку, чтобы перейти в приложение");
    }

    private void addRowOfButtons(MessagesSendQuery answer, List<KeyboardButton> buttons) {
        userKeyboard.add(buttons);
        answer.keyboard(new Keyboard().setButtons(new ArrayList<>(userKeyboard)));
    }

    private void clearButtons(MessagesSendQuery answer) {
        userKeyboard.clear();
        answer.keyboard(new Keyboard().setButtons(Collections.emptyList()))
                .message("Buttons refreshed");
    }
}
