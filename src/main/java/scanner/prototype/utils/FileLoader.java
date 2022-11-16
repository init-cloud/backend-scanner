package scanner.prototype.utils;

import java.io.File;
import java.io.IOException;

import scanner.prototype.env.Env;

public class FileLoader {
 
    public File loadTerraformFile(String args) 
    throws IOException
    {
        return new File(Env.FILE_UPLOAD_PATH.getValue() + File.separator + args);
    }
}
