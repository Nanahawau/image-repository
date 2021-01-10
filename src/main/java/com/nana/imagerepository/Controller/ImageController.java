package com.nana.imagerepository.Controller;

import com.nana.imagerepository.Service.ImageService;
import com.nana.imagerepository.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/")
public class ImageController {

    @Autowired
    ImageService imageService;


    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadeImage(@RequestParam("file") MultipartFile file) {
        try {
            imageService.save(file);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Uploaded the file successfully: " + file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response("Could not upload the file: " + file.getOriginalFilename()));
        }
    }


    @PostMapping("/upload/images")
    public ResponseEntity<?> uploadeImages() {
        return ResponseEntity.ok().build();
    }

}
