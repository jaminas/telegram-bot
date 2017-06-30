package rinat.telegrambot.telegram.method;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import rinat.telegrambot.telegram.object.Message;

/**
 * Метод запроса обновления
 * @author Rinat N ZIganshin
 */
public class GetUpdates extends AbstractMethod implements IMethod
{    
    private Message[] Messages;
    private static final ArrayList<Integer> UpdateIdCache = new ArrayList();
    private static final ArrayList<Integer> ResponseCache = new ArrayList();
    
    @Override
    public String getName() {
        return "getUpdates";
    }

    @Override
    public void parseResponse(Object JsonResult) 
    {
        ArrayList<Message> MessageArray = new ArrayList();
        int ReponseHashCode = JsonResult.toString().hashCode();
        if (!ResponseCache.contains(ReponseHashCode))
        {
            JSONArray ResultArray = (JSONArray) JsonResult;
            Iterator<String> iterator = ResultArray.iterator();
            while (iterator.hasNext()) 
            {
                Object ItemObj = iterator.next();
                JSONObject ItemJson = (JSONObject) ItemObj;
                int UpdateId = Integer.parseInt(ItemJson.get("update_id").toString());
                if (!UpdateIdCache.contains(UpdateId))
                {
                    JSONObject MessageJson = (JSONObject) ItemJson.get("message");
                    JSONObject ChatJson = (JSONObject) MessageJson.get("chat");
                    int MessageId = Integer.parseInt(MessageJson.get("message_id").toString());
                    int ChatId = Integer.parseInt(ChatJson.get("id").toString());

                    Message Message = new Message(MessageId, ChatId);
                    if (MessageJson.get("text") != null) {
                        Message.setText(MessageJson.get("text").toString());
                    }
                    MessageArray.add(Message);
                    UpdateIdCache.add(UpdateId);
                }
            }
            ResponseCache.add(ReponseHashCode);
        }
        
        Messages = new Message[MessageArray.size()];
        MessageArray.toArray(Messages);        
    }
    
    public Message[] getMessages() {
        return this.Messages;
    }
}
