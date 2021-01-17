package com.nana.imagerepository.Controller;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Model.ImageResponse;
import com.nana.imagerepository.Model.PermissionDTO;
import com.nana.imagerepository.Service.ImageService;
import com.nana.imagerepository.Model.Response;
import com.nana.imagerepository.Service.ImageUrl;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class ImageController {

    @Autowired
    ImageService imageService;



    @PostMapping("/image")
    public ResponseEntity<?> uploadeImageMinio(@RequestParam("image") MultipartFile image, @RequestParam("permission") String permission) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(imageService.saveToMinio(image, permission));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response("Could not upload the file: " + image.getOriginalFilename()));
        }
    }

    @GetMapping("private/images")
    public ResponseEntity<List<ImageResponse>> getAllImages () throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findAllPrivateImages());
    }

    @GetMapping("public/user/images")
    public ResponseEntity<List<ImageResponse>> getAllPublicImagesForAUser () throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.findAllPublicImagesForUser());
    }


    @GetMapping("public/images")
    public ResponseEntity<List<ImageUrl>> findAllPublicImages () throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getPublicImages());
    }



    @PostMapping("/images")
    public ResponseEntity<?> uploadFiles(@RequestParam("images") MultipartFile[] images) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.saveMultiple(images));
    }



    @PostMapping("/edit-permissions")
    public ResponseEntity <?> editPermissions (@RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.editPermissions(permissionDTO));
    }


}
