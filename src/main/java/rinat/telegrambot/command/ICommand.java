package rinat.telegrambot.command;

import rinat.telegrambot.telegram.object.Message;

/**
 * Интерфейс воркера бота
 * @author Rinat N ZIganshin
 */
public interface ICommand 
{
    public String getDescription();
    
    /**
     * Основной метод запуска воркера
     * @param Message
     * @throws java.lang.Exception
     */
    public void run(Message Message) throws Exception;
}
