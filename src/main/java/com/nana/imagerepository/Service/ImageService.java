package com.nana.imagerepository.Service;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Model.ImagePermission;
import com.nana.imagerepository.Model.Response;
import com.nana.imagerepository.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    MinioService minioService;


//        public Image save (MultipartFile file) throws IOException {
//            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//            Image image = new Image(fileName, file.getSize(), file.getBytes(), ImagePermission.PUBLIC);
//            return imageRepository.save(image);
//        }

    public Response saveToMinio  (MultipartFile file) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        minioService.saveToObjectStorage(file, fileName,  "PUBLIC");
        return new Response("Image Uploaded Successfully");
    }



        public List<Image> getAllImages(){
            return imageRepository.findAll();
        }

    public List<Image> findAllImages(){
        return imageRepository.findAllImages();
    }

        public Response saveMultiple(MultipartFile[] images) {
            Response response = new Response();
            System.out.println("Current time :: " + LocalDateTime.now());
            try {
                List<String> fileNames = new ArrayList<>();
                Arrays.asList(images).parallelStream().forEach(image -> {
                    String fileName = StringUtils.cleanPath(image.getOriginalFilename());
//                    Image imageFile = null;
                    try {
                        minioService.saveToObjectStorage(image, fileName, "PUBLIC");
//                        imageFile = new Image(fileName, image.getSize(), image.getBytes(), ImagePermission.PUBLIC);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.setMessage("Images Upload failed");
                    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
//                    imageRepository.save(imageFile);

                });
                response.setMessage("Images Upload Successful");

            } catch (Exception e) {
             e.printStackTrace();
                response.setMessage("Images Upload failed");
            }
            System.out.println("Current time :: " + LocalDateTime.now());
            return response;
        }


}
