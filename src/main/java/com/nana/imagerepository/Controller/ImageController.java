package com.nana.imagerepository.Controller;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Model.ImageResponse;
import com.nana.imagerepository.Service.ImageService;
import com.nana.imagerepository.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class ImageController {

    @Autowired
    ImageService imageService;


//    @PostMapping("/upload/image")
//    public ResponseEntity<?> uploadeImage(@RequestParam("image") MultipartFile image) {
//        try {
//            imageService.save(image);
//            return ResponseEntity.status(HttpStatus.OK).body(new Response("Uploaded the file successfully: " + image.getOriginalFilename()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response("Could not upload the file: " + image.getOriginalFilename()));
//        }
//    }


    @PostMapping("/upload/image/v1")
    public ResponseEntity<?> uploadeImageMinio(@RequestParam("image") MultipartFile image) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.saveToMinio(image));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response("Could not upload the file: " + image.getOriginalFilename()));
        }
    }




    @GetMapping("/images")
    public ResponseEntity<List<ImageResponse>> getAllImages (){
        System.out.println("Current time :: " + LocalDateTime.now());
        List<Image> allImages = imageService.getAllImages();
        System.out.println("Current time :: " + LocalDateTime.now());
        List<ImageResponse> images = allImages.stream().map(image -> {
           String imageUri = ServletUriComponentsBuilder
                   .fromCurrentContextPath()
                   .path("/images").path(String.valueOf(image.getId()))
                   .toUriString();

           return new ImageResponse();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(images);
    }




    @GetMapping("/images/v1")
    public ResponseEntity<List<ImageResponse>> findAllImages (){
        System.out.println("Current time :: " + LocalDateTime.now());
        List<Image> allImages = imageService.findAllImages();
        System.out.println("Current time :: " + LocalDateTime.now());
        List<ImageResponse> images = allImages.stream().map(image -> {
            String imageUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/images/v1").path(String.valueOf(image.getId()))
                    .toUriString();

            return new ImageResponse();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(images);
    }



    @PostMapping("/upload/images")
    public ResponseEntity<?> uploadFiles(@RequestParam("images") MultipartFile[] images) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.saveMultiple(images));
    }




}
