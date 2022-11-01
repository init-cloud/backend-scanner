package scanner.prototype.visualize;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.parser.JSONParser;

import scanner.prototype.env.Env;

public class ParserRequest {

    private final String API = Env.PARSER_API.getValue();
    private final String parse = "/api/v1/";
    private final JSONParser jsonParser = new JSONParser();


    public Object getTerraformParsingData(String directory)
    throws MalformedURLException
    {
        HttpURLConnection conn = null;
        URL url = new URL(API + parse + directory);

        try{
            conn = (HttpURLConnection) url.openConnection();
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
        } 
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }       
}
