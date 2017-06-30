package rinat.telegrambot.telegram;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rinat.telegrambot.telegram.method.IMethod;

/**
 * API телеграмма
 * @author Rinat N Ziganshin
 */
public class Api 
{
    private static final Logger LOG = LogManager.getLogger(Api.class);
    
    /** URL API - https://api.telegram.org/bot<token>/НАЗВАНИЕ_МЕТОДА */
    private final static String API_URI = "https://api.telegram.org/"; 
    
    /** токен бота */
    private static String Token;
    
    public static void setToken(String Token) {
        Api.Token = Token;
    }
    
     /**
     * Выполняет метод telegram
     * @param Method 
     */
    public static void exec(IMethod Method) 
    {
        ArrayList<NameValuePair> RequestParams = new ArrayList<>();
        StringBuilder RequestParamsLog = new StringBuilder();
        Method.getParams().entrySet().forEach((entry) -> {
            RequestParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            RequestParamsLog.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        });
        
        try {
            String Url = API_URI + "bot" + Api.Token + "/" + Method.getName();
            LOG.debug("Request:\n - url: " + Url + "\n - params: " + RequestParamsLog);
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(Url);
            post.setEntity(new UrlEncodedFormEntity(RequestParams, "UTF-8"));
            HttpResponse HttpResponse = client.execute(post);

            // проверяем статус ответа и ответ
            int statusCode = HttpResponse.getStatusLine().getStatusCode();
            String Response = EntityUtils.toString(HttpResponse.getEntity());
            if (statusCode == HttpStatus.SC_OK) 
            {
                LOG.debug("Response:" + Response);
                parseResponse(Response, Method);
            }
            else
            {
                Method.setOk(false);
                LOG.info("Http status code telegram API: " + statusCode + "\nResponse: " + Response);
            }
        }
        catch (Exception e) 
        {
            Method.setOk(false);
            LOG.warn("Not send request to telegram API", e);
        }
    }
    
    private static void parseResponse(String Response, IMethod Method)
    {
        try {
            JSONParser parser = new JSONParser();
            JSONObject JsonResponse = (JSONObject) parser.parse(Response);

            Object IsOk = JsonResponse.get("ok");
            Object Data = JsonResponse.get("result");
                
            if (IsOk != null) 
            {
                boolean bIsOk = (boolean) IsOk;
                Method.setOk(bIsOk);
                if (bIsOk && Data != null) {
                    Method.parseResponse(Data);
                }
            } else {
                Method.setOk(false);
                LOG.warn("Parsing json fail. Node 'ok' not found. Response: " + Response);
            }
        } catch (Exception e) {
                Method.setOk(false);
                LOG.warn("Parsing json fail. Response: " + Response, e);
        }
    }
    
}
