import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class Main {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Вас приветствует тестовый бот Шокопаудер!");
        sendMessage();
    }
        public static void sendMessage() {
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
            String apiToken = "1526549442:AAHAAAMNkkYt0iTMTTh-fQB1SiBn13kfYQk";
            String chatId = "688696597";
            String text = "I gave up";

            urlString = String.format(urlString, apiToken, chatId, text);

            try {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                InputStream is = new BufferedInputStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
