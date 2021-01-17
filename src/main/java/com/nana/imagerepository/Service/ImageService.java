package com.nana.imagerepository.Service;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Entity.User;
import com.nana.imagerepository.Model.ImageResponse;
import com.nana.imagerepository.Model.PermissionDTO;
import com.nana.imagerepository.Model.Response;
import com.nana.imagerepository.Repository.ImageRepository;
import com.nana.imagerepository.Repository.UserRepository;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    MinioService minioService;


    public Response saveToMinio(MultipartFile file, String permission) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Response response = new Response();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Image> isImage = imageRepository.findImageByFilePathAndUser(fileName, user);
        try {
            if (isImage.size() < 1) {
                String filePath = minioService.saveToObjectStorage(file, fileName);
                imageRepository.save(new Image(filePath, Boolean.valueOf(permission), user));
                response.setMessage("Image Uploaded Successfully");
            } else {
               response.setMessage("Image has been uploaded by this user");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return response;

    }
        public Response saveMultiple(MultipartFile[] images) {
        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Response response = new Response();
        System.out.println("Current time :: " + LocalDateTime.now());
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(images).parallelStream().forEach(image -> {
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                List<Image> isImage = imageRepository.findImageByFilePathAndUser(fileName, user);
                try {
                    if(isImage.size() < 1) {
                        imageRepository.save(new Image(fileName, Boolean.FALSE, user));
                        minioService.saveToObjectStorage(image, fileName);
                    } else {
                      response.setMessage("Image has been uploaded by this user");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    response.setMessage("Images Upload failed");
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }

            });
            response.setMessage("Images Upload Successful");

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Images Upload failed");
        }
        System.out.println("Current time :: " + LocalDateTime.now());
        return response;
    }

    public List<ImageUrl> getPublicImages() throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
     List <String> publicImages = imageRepository.findPublicImages();
     return minioService.getPublicObjects(publicImages);

    }

    public List<ImageResponse> findAllPrivateImages() throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List <String> publicImages = imageRepository.findAllPrivateImages(user.getId());
        return minioService.getObjects(publicImages);
    }

    public List<ImageResponse> findAllPublicImagesForUser() throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List <String> publicImages = imageRepository.findAllPublicImagesForUser(user.getId());
        return minioService.getObjects(publicImages);
    }


   public Response editPermissions (PermissionDTO permissionDTO) {
        if(imageRepository.findById(permissionDTO.getImageId()).isPresent()){
            Image image = imageRepository.findImageById(permissionDTO.getImageId());
            image.setPrivate(Boolean.valueOf(permissionDTO.getIsPrivate()));
            imageRepository.save(image);
            return new Response("Permission edited successfully");
        }

        return new Response("Image not found");
   }

}
