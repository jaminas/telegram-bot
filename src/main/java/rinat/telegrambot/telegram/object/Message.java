package rinat.telegrambot.telegram.object;

/**
 * Сообщение апдейта
 * @author Rinat N Ziganshin
 */
public class Message 
{
    private final int Id;
    private final int ChatId;
    private String Text;
    
    public Message(int Id, int ChatId) 
    {
        this.Id = Id;
        this.ChatId = ChatId;
    }
    
    public int getId() {
        return Id;
    }
    
    public String getText() {
        return Text;
    }
    
    public int getChatId() {
        return ChatId;
    }
    
    public void setText(String Text) {
        this.Text = Text;
    }
}
