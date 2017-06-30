package rinat.telegrambot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rinat.telegrambot.core.Registry;
import rinat.telegrambot.service.MessageHandler;
import rinat.telegrambot.service.TelegramService;

/**
 * @author Rinat N Ziganshin
 */
public class Main 
{
    private static final Logger LOG = LogManager.getLogger(Main.class);
    private static Registry Registry = new Registry();
    
    public static void init(String[] args)
    {
        
    }
    
    public static void main(String[] args) throws Exception
    {
        init(args);

        MessageHandler MessageListiner = new MessageHandler()
                .addCommand(new rinat.telegrambot.command.Ping());
        
        TelegramService Telegram = new TelegramService("306772147:AAHpJZbTHo8vb75AUE9wTTrwX6ESEWnjgCg", MessageListiner);
        Registry.add(Registry.TELEGRAM, Telegram);
        Telegram.listen();
        
        //SendMessage Message = new SendMessage(157020029, "Я запустился");
        //Api.exec(Message);
        
    }

    
}
