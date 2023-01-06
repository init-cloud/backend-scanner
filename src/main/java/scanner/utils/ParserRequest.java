package scanner.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.env.Env;


@Service
@RequiredArgsConstructor
public class ParserRequest {

    private String API=Env.PARSE_API.getValue();
    private final JSONParser jsonParser = new JSONParser();

    public Object getTerraformParsingData(String directory, String provider)
    throws MalformedURLException, IOException, ParseException
    {
        try{
            HttpURLConnection conn = null;
            URL url = new URL(API + "/" + provider + "/" + directory);
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
        catch(FileNotFoundException e){
            return null;
        }
    }       
}
