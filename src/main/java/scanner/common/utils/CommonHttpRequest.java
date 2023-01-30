package scanner.common.utils;

import lombok.NoArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import scanner.common.dto.HttpRequestUrlParam;
import scanner.exception.ApiException;
import scanner.response.enums.ResponseCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@NoArgsConstructor
public class CommonHttpRequest {

    public Object HttpGetRequestBuffer(String baseUrl, HttpRequestUrlParam param) {
        try{
            JSONParser jsonParser = new JSONParser();
            URL url = new URL(baseUrl + param.getUri());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return jsonParser.parse(response.toString());
        } catch (IOException e){
            throw new ApiException(ResponseCode.STATUS_4014);
        } catch(ParseException e){
            throw new ApiException(ResponseCode.STATUS_4014);
        }
    }

    public Object HttpPostRequestBuffer(String baseUrl, HttpRequestUrlParam param, Object body) {
        try{
            JSONParser jsonParser = new JSONParser();
            URL url = new URL(baseUrl + param.getUri());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine = inputLine = in.readLine();
            StringBuffer response = new StringBuffer();

            if(inputLine.startsWith("{")){
                response.append(inputLine);
                while ((inputLine = in.readLine()) != null )
                    response.append(inputLine);

                in.close();
                return jsonParser.parse(response.toString());
            } else{
                response.append(inputLine);
                in.close();
                return response.toString();
            }
        } catch (IOException e){
            throw new ApiException(ResponseCode.STATUS_4014);
        } catch (ParseException e) {
            throw new ApiException(ResponseCode.STATUS_4014);
        }
    }
}
