import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class TelegramBot extends TelegramLongPollingBot {
    private String user_name = "No info";
    private String user_surname = "No info";
    private String user_age = "No info";
    private String user_address = "No info";

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String ans = message.getText();
            if (ans.equals("Привет")) {
                // Формат - Мое имя _ (имя чела)
                sendMsg(message, ans + "! Как тебя зовут?");
            } else if (ans.contains("имя")) {
                user_name = ans.substring(7);
                sendMsg(message, "Приятно познакомиться, " + user_name);
            } else if (ans.contains("фамилия")) {
                user_surname = ans.substring(11);
            } else if (ans.contains("возраст")) {
                user_age = ans.substring(11);
            } else if (ans.contains("адрес")) {
                user_address = ans.substring(9);
            } else if (ans.contains("данные")) {
                String str = "Ваши данные\n" + "Имя " + user_name +  "\nФамилия " + user_surname
                           + "\nВозраст " + user_age + "\nАдрес " + user_address;
                sendMsg(message, str);
            } else if (ans.equals("Меню")) {
//                sendMsg(message, "Здравствуй, товарищ! Держи меню: ");
                try {
                    execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                sendMsg(message, "Приветствие лучшее начало диалога :) У нас есть меню!");
            }
        } else if(update.hasCallbackQuery()){
            try {
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    //функция посылки сообщения
    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static SendMessage sendInlineKeyBoardMessage(Long chat_id) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Имя").setCallbackData("Формат: Мое имя _"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Фамилия").setCallbackData("Формат: Моя фамилия _"));
        keyboardButtonsRow3.add(new InlineKeyboardButton().setText("Возраст").setCallbackData("Формат: Мой возраст _"));
        keyboardButtonsRow4.add(new InlineKeyboardButton().setText("Адрес").setCallbackData("Формат: Мой адрес _"));
        keyboardButtonsRow5.add(new InlineKeyboardButton().setText("Инфо").setCallbackData("Формат: Мои данные"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);

        markupInline.setKeyboard(rowList);
        return new SendMessage().setChatId(chat_id).setText("Здравствуй, товарищ! Держи меню: ")
                                .setReplyMarkup(markupInline);
    }
    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @TelegramBot, it must return 'TelegramBot'
        return "TelegramBot_ChocoPowder";
    }

    @Override
    public String getBotToken() {
        return "1526549442:AAHAAAMNkkYt0iTMTTh-fQB1SiBn13kfYQk";
    }
}