package rinat.telegrambot.telegram.method;

import java.util.HashMap;

/**
 *
 * @author rinat
 */
public interface IMethod 
{
    /**
     * Метод возвращает имя метода
     * @return 
     */
    public String getName();
    
    /**
     * Метод возвращает параметры запроса
     * @return 
     */
    public HashMap<String, String> getParams();
    
    /**
     * Метод устанавливает тру в случает ответа API ok:true
     * @param Ok 
     */
    public void setOk(boolean Ok);
    
    /**
     * Метод возвращает метку результата 
     * @return 
     */
    public Boolean getOk();
    
    /**
     * Метод парсит параметры конкретные для данного метода
     * @param JsonResponse 
     */
    public void parseResponse(Object JsonResponse);
}
