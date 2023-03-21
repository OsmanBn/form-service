package org.SpeechForm.FormService;

import org.SpeechForm.FormService.entities.Question;
import org.SpeechForm.FormService.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("files")
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;

    private QuestionRepository questionRepository;
    public FileController(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @PutMapping
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("audioFile") MultipartFile file, @RequestParam("imageFile") MultipartFile file2){
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileName)
                .toUriString();
        FileResponse fileResponse = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        System.out.println("UN FICHIER AUDIO A BIEN ETE UPLOADE: "+fileResponse.getFileDownloadUri());

        // For the second file (image)
        String file2Name = fileStorageService.storeFile(file2);
        String file2DownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(file2Name)
                .toUriString();
        FileResponse file2Response = new FileResponse(file2Name, file2DownloadUri, file2.getContentType(), file2.getSize());
        System.out.println("UN FICHIER IMAGE A BIEN ETE UPLOADE: "+file2Response.getFileDownloadUri());
        return new ResponseEntity<FileResponse>(fileResponse, HttpStatus.OK);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileStorageService.loadFileAsResource(filename);

        String contentType = null;

        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException ex){
            System.out.println("Could not determine fileType");
        }

        if(contentType==null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/{filename}")
    public String uploadQuestion(@PathVariable String filename){
        Question question1= new Question();
        question1.setName(filename);
        questionRepository.save(question1);
        return "Ah Oiui"+filename;
    }

    @GetMapping("/Questions")
    public List<Question> findAll(){
        return questionRepository.findAll();
    }
}
