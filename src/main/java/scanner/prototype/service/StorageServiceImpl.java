package scanner.prototype.service;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService{

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    
    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory.");
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new Exception("File is empty.");
            }
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }

            try (InputStream inputStream = file.getInputStream()) {

                if(isNotValidExt(file.getOriginalFilename()))
                    throw new RuntimeException("Could not store the file. Error: ");

                UUID uniqName = UUID.randomUUID();
                String saved = uniqName.toString() + "_" + file.getOriginalFilename();
                Files.copy(inputStream, root.resolve(saved), StandardCopyOption.REPLACE_EXISTING);
                return saved;
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: ");// + e.getMessage());
        }
    }

    public boolean isNotValidExt(String fullname){
        String pt = "..|\\/|\\|;";
        
        return fullname.matches(pt);
    }

    public boolean getExtension(String fullname){
        return false;
    }

    @Override
    public Stream<Path> loadAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Path load(String filename) {
        return Paths.get(this.uploadPath).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename).normalize();
            Resource rsc = new UrlResource(file.toUri());

            if(rsc.exists() || rsc.isReadable())
                return rsc;
            else
                throw new RuntimeException("Error");
        }
        catch(MalformedURLException e){
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
    }
}