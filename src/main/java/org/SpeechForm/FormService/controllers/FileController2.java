package org.SpeechForm.FormService.controllers;

import org.SpeechForm.FormService.entities.AudioFile;
import org.SpeechForm.FormService.services.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class FileController2 {

    private FileService fileService;
    Logger logger = Logger.getLogger(FileController2.class.getName());

    public FileController2(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/profile/pic")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
        logger.info("HIT -/upload | File Name : "+multipartFile.getOriginalFilename());
        //AudioFile audioFile = new AudioFile();
        return fileService.upload(multipartFile);
    }

    @PostMapping("/profile/pic/{fileName}")
    public Object download(@PathVariable String fileName) throws IOException {
        logger.info("HIT -/download | File Name : "+fileName);
        return fileService.download(fileName);
    }

    @GetMapping(path = "/audios")
    public List<AudioFile> allAudio(){
        return fileService.listAudio();
    }

    /*@GetMapping(path = "/audios/{name}")
    public AudioFile getAudio(@PathVariable String name){
        return fileService.getAudio();
    }*/

    /*@PostMapping(path = "/audios")
    public AudioFile save(AudioFile audioFile){
        return this.fileService.save(audioFile);
    }*/
}
