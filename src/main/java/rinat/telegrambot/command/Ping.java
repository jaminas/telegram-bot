package rinat.telegrambot.command;

import rinat.telegrambot.core.Registry;
import rinat.telegrambot.service.TelegramService;
import rinat.telegrambot.telegram.object.Message;

/**
 * Команда пинг понга
 * @author Rinat N Ziganshin
 */
public class Ping extends AbstractCommand implements ICommand 
{
    @Override
    public void run(Message Message) throws Exception
    {
        TelegramService Telegram = (TelegramService) Registry.get(Registry.TELEGRAM);
        Telegram.sendMessage(Message.getChatId(), "pong");
    }

    @Override
    public String getDescription() {
        return "Пинг-понг. Тестовая комманда";
    }
}
