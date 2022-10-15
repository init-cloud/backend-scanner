package scanner.prototype.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import scanner.prototype.env.Env;
import scanner.prototype.exception.ExceptionMessage;
import scanner.prototype.middleware.FileLoader;


public class ScanJob {

    public String terrformScan(String args) 
    throws Exception 
    {
        FileLoader fl = new FileLoader();
        String scanResult;
        StringBuilder sb = new StringBuilder();
        Process p;

        try {
            String[] cmd = {"/bin/bash", "-c", Env.SHELL_COMMAND.getValue()};
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((scanResult = br.readLine()) != null){
                sb.append(scanResult);
                sb.append("\n");
            }

            p.waitFor();
            //System.out.println("exit: " + p.exitValue());
            p.destroy();

            File file = fl.loadTerraformFile(args); 
            // new File(FILE_UPLOAD_PATH + File.separator + args);
            boolean result = file.delete();

            return sb.toString();
        } catch (Exception e) {
            return ExceptionMessage.ERROR.getExceptionMessage();
        }
    }
}