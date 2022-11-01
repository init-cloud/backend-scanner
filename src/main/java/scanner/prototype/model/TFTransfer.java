package scanner.prototype.model;

import java.io.File;
import java.io.Serializable;


public class TFTransfer implements Serializable{
    
    private File file;

    public TFTransfer(File file){
        this.file = file;
    }
}
