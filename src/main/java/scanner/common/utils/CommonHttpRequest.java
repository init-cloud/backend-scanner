package scanner.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.simple.parser.JSONParser;
import scanner.common.dto.HttpParam;
import scanner.exception.ApiException;
import scanner.dto.enums.ResponseCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @deprecated
 */
@Deprecated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonHttpRequest {

    public static Object requestHttpGet(String baseUrl, String uri, HttpParam.Header header) {
        try{
            URL url = new URL(baseUrl + uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            List<String> keys = header.getKeys();
            List<String> values = header.getValues();
            for(int i = 0 ; i < keys.size() ; i++)
                conn.setRequestProperty(keys.get(i), values.get(i));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e){
            throw new ApiException(ResponseCode.STATUS_4014);
        }
    }

    public static Object requestHttpPost(String baseUrl, String uri, Object body) {
        try{
            JSONParser jsonParser = new JSONParser();
            URL url = new URL(baseUrl + uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine = inputLine = in.readLine();
            StringBuffer response = new StringBuffer();

            response.append(inputLine);
            while ((inputLine = in.readLine()) != null )
                response.append(inputLine);

            in.close();
            return response.toString();

        } catch (IOException e){
            throw new ApiException(ResponseCode.STATUS_4014);
        }
    }
}
