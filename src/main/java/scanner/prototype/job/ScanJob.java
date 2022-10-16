package scanner.prototype.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.RequiredArgsConstructor;
import scanner.prototype.env.Env;
import scanner.prototype.exception.ExceptionMessage;
import scanner.prototype.middleware.FileLoader;
import scanner.prototype.middleware.TerraformParser;
import scanner.prototype.response.CheckResponse;


@RequiredArgsConstructor
public class ScanJob {

    private final TerraformParser tfParser;

    public CheckResponse terrformScan(String args) 
    throws Exception 
    {
        FileLoader fl = new FileLoader();
        CheckResponse scanResult;
        Process p;

        try {
            String[] cmd = {"/bin/bash", "-c", Env.SHELL_COMMAND.getValue()};
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            scanResult = resultToJson(br);

            p.waitFor();
            //System.out.println("exit: " + p.exitValue());
            p.destroy();

            File file = fl.loadTerraformFile(args); 
            tfParser.parseToJsonString(file);
            // new File(FILE_UPLOAD_PATH + File.separator + args);
            boolean result = file.delete();

            return scanResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CheckResponse resultToJson(BufferedReader br) 
    throws IOException
    {
        //StringBuilder sb = new StringBuilder();
        String rawResult;

        while((rawResult = br.readLine()) != null){
            String[] lines;
            
            // sb.append(rawResult);
            // sb.append("\n");

            if(rawResult.contains("Passed checks")){
                lines = rawResult.split(", ");

                String[] passed = lines[0].split("checks:");
                String[] failed = lines[1].split("checks:");
                String[] skipped = lines[2].split("checks:");

                return new CheckResponse(
                    Integer.parseInt(passed[1].trim()), 
                    Integer.parseInt(failed[1].trim()), 
                    Integer.parseInt(skipped[1].trim()));
            }
        }

        return null;
    }

    public JSONObject initScanFormatToJSON(){
        return new JSONObject();
    }

    public ScanJob(){
        this.tfParser = new TerraformParser();
    }
}