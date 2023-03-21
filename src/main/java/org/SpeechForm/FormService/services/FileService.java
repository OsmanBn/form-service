package org.SpeechForm.FormService.services;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.SpeechForm.FormService.entities.AudioFile;
import org.SpeechForm.FormService.repositories.AudioFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private  AudioFileRepository audioFileRepository;
    public FileService(AudioFileRepository audioFileRepository){
        this.audioFileRepository = audioFileRepository;
    }
    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("audioformfirebase.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:/Users/BenPC/Documents/JavaProjects/SpeechForm/Form-Service/src/main/resources/static/audioformfirebase-firebase-adminsdk-7c9rb-363fbcbc72.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format("https://firebasestorage.googleapis.com/v0/b/audioformfirebase.appspot.com/o/%s?alt=media", URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Object upload(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            //fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            AudioFile audioFile = new AudioFile();
            audioFile.setName(fileName);
            audioFile.setFilePath(TEMP_URL);
            Object audioFileRESULT = download(fileName);
            //return "audioFileRESULT";
            return this.save(audioFile);//"Successfully Uploaded ! "+TEMP_URL;                     // Your customized response
        } catch (Exception e) {
            e.printStackTrace();
            return "500 "+e+" Unsuccessfully Uploaded!";
        }

    }

    public Object download(String fileName) throws IOException {
        //String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));     // to set random strinh for destination file name
        String destFilePath = "C:/Users/BenPC/Documents/JavaProjects/SpeechForm/Form-Service/src/main/resources/audio/" + fileName;
        //String destFilePath = "./HERE/" + fileName;
        // to set destination file path

        ////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:/Users/BenPC/Documents/JavaProjects/SpeechForm/Form-Service/src/main/resources/static/audioformfirebase-firebase-adminsdk-7c9rb-363fbcbc72.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        Blob blob = storage.get(BlobId.of("audioformfirebase.appspot.com", fileName));
        blob.downloadTo(Paths.get(destFilePath));
        System.out.println(blob);
        return blob/*"200"+" Successfully Downloaded!"*/;
    }

    public List<AudioFile> listAudio() {
        return audioFileRepository.findAll();
    }

    public AudioFile save(AudioFile audioFile){
        return this.audioFileRepository.save(audioFile);
    }
/*
    public AudioFile getAudio(String name) {
        return audioFileRepository.findBy().get();
        audioFileRepository.
    }*/
}
