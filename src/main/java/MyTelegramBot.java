import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String naseUrl = "https://api.nasa.gov/planetary/apod?api_key=HAVR54ffdiqe2gNO9md5goRzlLMIWzgAPg7KoS6f";

    public MyTelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] separatedAction = update.getMessage().getText().split(" ");
            String action = separatedAction[0];
            long chatId = update.getMessage().getChatId();

            switch (action) {
                case "/help":
                    sendMessage("Я бот Nasa, шлю картинку дня", chatId);
                    break;
                case "/start":
                case "/image":
                    String imageUrl = Utils.getUrl(naseUrl);
                    sendMessage(imageUrl,chatId);
                    break;
                case "/date":
                    String date = separatedAction[1];
                    imageUrl = Utils.getUrl(naseUrl + "&date=" + date);
                    sendMessage(imageUrl,chatId);
                    break;
                default:
                    sendMessage("Я тебя не понимаю", chatId);
                    break;
            }
        }
    }

    void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            System.out.println("Не отправилось сообщение");
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
