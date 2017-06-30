package rinat.telegrambot.telegram.method;

import java.util.HashMap;

/**
 * @author Rinat N Ziganshin
 */
public class AbstractMethod 
{
    private Boolean Ok;
 
    public void setOk(boolean Ok)
    {
        this.Ok = Ok;
    }
    
    public Boolean getOk()
    {
        return Ok;
    }
    
    public HashMap<String, String> getParams() {
        return new HashMap();
    }
}
