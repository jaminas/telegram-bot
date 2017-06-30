package rinat.telegrambot.service;

import rinat.telegrambot.core.Utils;
import rinat.telegrambot.telegram.Api;
import rinat.telegrambot.telegram.method.GetUpdates;
import rinat.telegrambot.telegram.method.SendMessage;
import rinat.telegrambot.telegram.object.Message;

/**
 * Сервис телеграмма 
 * @author Rinat N Ziganshin
 */
public class TelegramService
{
    /** хендлер месседжей */
    private final MessageHandler MessageHandler;
    
    public TelegramService(String Token, MessageHandler MessageHandler) 
    {
        Api.setToken(Token);
        this.MessageHandler = MessageHandler;
    }
    
    /**
     * Метод начинает слушать сообщения боту
     */
    public void listen()
    {
        do
        {
            GetUpdates Updates = new GetUpdates();
            Api.exec(Updates);
            if (true == Updates.getOk())
            {
                for(Message Message : Updates.getMessages()) {
                    MessageHandler.handle(Message);
                }
            }
            //System.exit(1);
            Utils.sleep(1);
        } while(true);
    }
    
    public void sendMessage(int ChatId, String Text)
    {
        SendMessage SendMessage = new SendMessage(ChatId, Text);
        Api.exec(SendMessage);
    }
    
}
