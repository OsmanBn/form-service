package org.SpeechForm.FormService;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir(){
        return uploadDir;
    }

    public void setUploadDir(String uploadDir){
        this.uploadDir = uploadDir;
    }
}
