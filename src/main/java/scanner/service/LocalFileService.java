package scanner.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import scanner.exception.FileUploadFailureException;


@Service
public class LocalFileService implements FileService{

    @Value("${spring.servlet.multipart.location}")
    private String location;

    @PostConstruct
    void postConstruct(){
        File dir = new File(location);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename){
        try{
            file.transferTo(new File(location + filename));
        }
        catch(IOException e){
            throw new FileUploadFailureException(e);
        }
    }

    @Override
    public void delete(String filename){
        File f = new File(location + filename);
        f.delete();
    }
}
