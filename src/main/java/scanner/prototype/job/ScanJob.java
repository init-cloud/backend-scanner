package scanner.prototype.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import scanner.prototype.env.Env;
import scanner.prototype.middleware.FileLoader;
import scanner.prototype.middleware.TerraformParser;
import scanner.prototype.response.CheckResponse;
import scanner.prototype.response.ResultResponse;
import scanner.prototype.response.ScanResponse;


@RequiredArgsConstructor
public class ScanJob {

    private final TerraformParser tfParser;

    /**
     * 구현 중
     * @param args
     * @return
     * @throws Exception
     */
    public ScanResponse<?> terrformScan(String args) 
    throws Exception 
    {
        FileLoader fl = new FileLoader();
        ScanResponse<?> scanResult;
        Process p;

        try {
            String[] cmd = {"/bin/bash", "-c", Env.SHELL_COMMAND.getValue()};
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            scanResult = resultToJson(br);

            p.waitFor();
            p.destroy();

            File file = fl.loadTerraformFile(args); 
            tfParser.parseToJsonString(file);
            boolean result = file.delete();

            return scanResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param scan
     * @return
     */
    public CheckResponse parseScanCheck(String scan){
        String[] lines = scan.strip().split(", ");
        String[] passed = lines[0].split("checks:");
        String[] failed = lines[1].split("checks:");
        String[] skipped = lines[2].split("checks:");

        return new CheckResponse(
            Integer.parseInt(passed[1].strip()), 
            Integer.parseInt(failed[1].strip()), 
            Integer.parseInt(skipped[1].strip())
        );
    }

    /**
     * 구현 중
     * @param scan
     * @return
     */
    public ResultResponse parseScanResult(
        String rawResult,
        ResultResponse result
    ){
        String[] lines;

        if(rawResult.contains("Check:")){
            lines = rawResult.split(": ");

            result.setRule_id(lines[1].strip());
            result.setDescription(lines[2].strip());
            result.setLevel("HIGH");
        }
        
        if(rawResult.contains("PASSED")){
            lines = rawResult.split(": ");

            result.setStatus("passed");
            result.setDetail("No");
            result.setTarget_resource(lines[1].strip());
        }
        else if(rawResult.contains("FAILED")){
            lines = rawResult.split(": ");

            result.setStatus("failed");
            result.setTarget_resource(lines[1].strip());
        }

        if(rawResult.contains("File")){
            lines = rawResult.split(":");

            result.setTarget_file(lines[1].strip());
            result.setLines(lines[2].strip());
        }

        return result;
    }

    /**
     * 구현 중
     * @param br
     * @return
     * @throws IOException
     */
    public ScanResponse<?> resultToJson(BufferedReader br) 
    throws IOException
    {
        String rawResult;
        StringBuilder sb = new StringBuilder();
        CheckResponse check = new CheckResponse();
        ResultResponse result = new ResultResponse();
        List<ResultResponse> resultLists = new ArrayList<>();

        while((rawResult = br.readLine()) != null){

            if(rawResult.contains("Passed checks")){
                check = parseScanCheck(rawResult);
                continue;
            }

            result = parseScanResult(rawResult, result);

            if(result.getTarget_file() != null){
                if(result.getStatus() == "passed"){
                    resultLists.add(result);
                    result = new ResultResponse();
                    sb = new StringBuilder();
                }
                else{
                    sb.append(rawResult);
                    sb.append("\n");

                    if(rawResult.contains(result.getLines().split("-")[1] + " |")){
                        result.setDetail(sb.toString());
                        resultLists.add(result);
                        result = new ResultResponse();
                        sb = new StringBuilder();
                    }
                }
            } 
        }

        return new ScanResponse(check, resultLists);
    }

    /**
     * 생성자
     */
    public ScanJob(){
        this.tfParser = new TerraformParser();
    }
}