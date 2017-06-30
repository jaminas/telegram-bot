package rinat.telegrambot.service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rinat.telegrambot.command.ICommand;
import rinat.telegrambot.core.Registry;
import rinat.telegrambot.telegram.object.Message;

/**
 * Обработчик сообщений
 * @author Rinat N Ziganshin
 */
public class MessageHandler 
{
    private static final Logger LOG = LogManager.getLogger(MessageHandler.class);
    
    /** зарегистрированные комманды обработки */
    private final HashMap<String, Class> Commands = new HashMap();
    private final HashMap<String, String> CommandsHelp = new HashMap();
    
    /**
     * Метод добавляет команду для обработки
     * @param Command
     * @return 
     */
    public MessageHandler addCommand(ICommand Command) 
    {
        Commands.put(Command.getClass().getSimpleName().toLowerCase(), Command.getClass());
        CommandsHelp.put(Command.getClass().getSimpleName().toLowerCase(), Command.getDescription());
        return this;
    }
    
    public void handle(Message Message)
    {
        LOG.debug("new Message: " + Message.getText());
        
        String[] Elements = Message.getText().replace("  ", "").split(" ");
        String ProbablyCommandName = Elements[0];
        
        if ("/help".equals(ProbablyCommandName))
        {
            LOG.debug("Show help");
            this.help(Message);
        }
        else if (ProbablyCommandName.startsWith("/") && ProbablyCommandName.length() > 1)
        {
            String CommandName = ProbablyCommandName.substring(1);
            Class ClassName = Commands.get(CommandName);
            if (ClassName != null)
            {
                try {
                    LOG.debug("Run command: " + CommandName);
                    Constructor con = ClassName.getConstructor();
                    ICommand Command = (ICommand) con.newInstance();
                    Command.run(Message);
                } catch (Exception e) {
                    LOG.warn("Create command object fail", e);
                }
            }
            else
            {
                LOG.debug("Invalid command name: " + ProbablyCommandName);
                TelegramService Telegram = (TelegramService) Registry.get(Registry.TELEGRAM);
                Telegram.sendMessage(Message.getChatId(), "Не знаю такой комманды. Введи /help чтобы узнать список доступных команд");
            }
        } 
        else
        {
            LOG.debug("Non command name: " + ProbablyCommandName);
            TelegramService Telegram = (TelegramService) Registry.get(Registry.TELEGRAM);
            Telegram.sendMessage(Message.getChatId(), "Пока кроме комманд меня ничему не научили. Введи /help чтобы узнать список доступных команд");         
        }
    }
    
    /**
     * Помощь
     * @param Message 
     */
    private void help(Message Message)
    {
        TelegramService Telegram = (TelegramService) Registry.get(Registry.TELEGRAM);
        StringBuilder Help = new StringBuilder();
        
        Help.append("Usage: /command args1 arg2 ... argN\n");
        Help.append("Доступные команды:\n");
        CommandsHelp.keySet().forEach((command_name) -> {
            Help.append("/").append(command_name).append(" - ").append(CommandsHelp.get(command_name));
        });
        Telegram.sendMessage(Message.getChatId(), Help.toString());
    }
    
}
