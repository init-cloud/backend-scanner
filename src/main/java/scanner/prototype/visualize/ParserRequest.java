package scanner.prototype.visualize;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import scanner.prototype.exception.ScanException;
import scanner.prototype.env.Env;

@Service
public class ParserRequest {

    private final String API = Env.PARSER_API.getValue();
    private final String parse = "/api/v1/";
    private final JSONParser jsonParser = new JSONParser();
    private static final String[] providers = {"aws", "ncp", "ncloud"};

    public Object getTerraformParsingData(String directory, String provider)
    {
        try{
            if(provider == null || !Arrays.asList(providers).contains(provider))
                throw new ScanException("Invalid Provider");

            HttpURLConnection conn = null;
            URL url = new URL(API + parse + directory);
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
        catch(MalformedURLException e){
            throw new ScanException("Scan Error", e);
        }
        catch(Exception e){
            throw new ScanException(e);
        }
    }       
}
