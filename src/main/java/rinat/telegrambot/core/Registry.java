package rinat.telegrambot.core;

import java.util.HashMap;

/**
 * Реестр
 * @author Rinat N Ziganshin
 */
public class Registry 
{
    protected static HashMap <String, Object> registry = new HashMap<>();

    public static String TELEGRAM  = "telegram";

    public static void add(String key, Object value)
    {
        registry.put(key, value);
    }

    public static Object get(String key)
    {
        return registry.get(key);
    }
}
