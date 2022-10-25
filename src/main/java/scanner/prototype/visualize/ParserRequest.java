package scanner.prototype.visualize;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import scanner.prototype.env.Env;

public class ParserRequest {

    private final String API = Env.PARSER_API.getValue();
    private final String parse = "/api/v1/";


    public String getTerraformParsingData()
    throws MalformedURLException
    {
        HttpURLConnection conn = null;
        URL url = new URL(API + parse);

        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); 
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(conn.getInputStream())
                                ); 
            String inputLine; 
            StringBuffer response = new StringBuffer(); 
            while ((inputLine = in.readLine()) != null) { 
                response.append(inputLine); 
            } 
            in.close();
            return response.toString();

        } catch(Exception e){
            return "{ \"status\" : \"ERROR\"}";
        }
    }       
}
