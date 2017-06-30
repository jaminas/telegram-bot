package rinat.telegrambot.telegram.method;

import java.util.HashMap;

/**
 * @author Rinat N ZIganshin
 */
public class SendMessage extends AbstractMethod implements IMethod
{
    private final int ChatId;
    private final String Text;
    
    public SendMessage(int ChatId, String Text) {
        this.ChatId = ChatId;
        this.Text = Text;
    }
    
    @Override
    public String getName() {
        return "sendMessage";
    }
    
    @Override
    public HashMap<String, String> getParams() {
        return new HashMap() {{ this.put("chat_id", String.valueOf(ChatId)); this.put("text", Text); }};
    }

    @Override
    public void parseResponse(Object JsonResponse) {
    }
}
