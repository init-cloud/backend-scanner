package scanner.prototype.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class ScanJob {

    private final static String SHELL_COMMAND = ;
    private final static String FILE_UPLOAD_PATH = ;
    private final static String ERROR_STRING = "ERROR";

    public String terrformScan(String args) 
    throws Exception {
        String scanResult;
        StringBuilder sb = new StringBuilder();
        Process p;

        try {
            String[] cmd = {"/bin/bash","-c", SHELL_COMMAND};
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((scanResult = br.readLine()) != null){
                sb.append(scanResult);
                sb.append("\n");
            }

            p.waitFor();
            //System.out.println("exit: " + p.exitValue());
            p.destroy();

            File file = new File(FILE_UPLOAD_PATH + File.separator + args);
            boolean result = file.delete();

            return sb.toString();
        } catch (Exception e) {
            return ERROR_STRING;
        }
    }
}