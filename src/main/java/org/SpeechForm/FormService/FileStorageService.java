package org.SpeechForm.FormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties){
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch(Exception ex){
            throw new FileStorageException("Could not create the directory to upload");

        }
    }

    //function to store the file
    public String storeFile(MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        }catch (IOException ex){
            throw new FileStorageException("could not store file"+filename+". Please try again", ex);
        }
    }

    //function to load the file
    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new MyFileNotFoundException("file not found "+fileName);
            }
        }catch(MalformedURLException ex){
            throw new MyFileNotFoundException("File not found "+fileName);
        }
    }
}
