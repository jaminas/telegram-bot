package rinat.telegrambot.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Rinat N Ziganshin
 */
public class Utils 
{
    private static final Logger LOG = LogManager.getLogger(Utils.class);
    
    /**
     * Метод sleep
     * @param Sec 
     */
    public static void sleep(int Sec)
    {
        try {
            Thread.sleep(Sec * 1000);
        } catch (InterruptedException e) {
            LOG.warn(e);
        }
    }
}
