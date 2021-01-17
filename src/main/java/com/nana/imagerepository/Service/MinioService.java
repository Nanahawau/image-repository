package com.nana.imagerepository.Service;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Entity.User;
import com.nana.imagerepository.Model.ImageResponse;
import com.nana.imagerepository.Model.PermissionDTO;
import com.nana.imagerepository.Repository.ImageRepository;
import com.nana.imagerepository.Repository.UserRepository;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class MinioService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${minio.url}")
    private String minioUrl;
    @Value("${access.key}")
    private String accessKey;
    @Value("${secret.key}")
    private String secretKey;
    @Value("${bucket.name}")
    private String bucketName;

    public String saveToObjectStorage (MultipartFile file, String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        ObjectWriteResponse response = null;

        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioUrl)
                            .credentials(accessKey, secretKey)
                            .build();
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket "  +bucketName+ " already exists.");
            }


        response =  minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                            file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());



        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }

       return response.object();
    }


    public List<ImageResponse> getObjects (List <String> filePath)  {

        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        // Create a minioClient with the MinIO server playground, its access key and secret key.
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioUrl)
                        .credentials(accessKey, secretKey)
                        .build();

        List<ImageResponse> response = filePath.stream().map(file -> {
            Long imageObject =  imageRepository.findImageIdAndPermission(file, user.getId());

            String url =
                    null;
            try {
                url = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucketName)
                                .object(file)
                                .expiry(1, TimeUnit.DAYS)
                                .build());
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } catch (InsufficientDataException e) {
                e.printStackTrace();
            } catch (InternalException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidResponseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (XmlParserException e) {
                e.printStackTrace();
            } catch (ServerException e) {
                e.printStackTrace();
            }


            return new ImageResponse(imageObject, url);
        }).collect(Collectors.toList());


        return response;

    }


    public List<ImageUrl> getPublicObjects (List <String> filePath)  {

        System.out.println(filePath.toString());

        User user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        // Create a minioClient with the MinIO server playground, its access key and secret key.
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioUrl)
                        .credentials(accessKey, secretKey)
                        .build();

        List<ImageUrl> response = filePath.stream().map(file -> {

            String url =
                    null;
            try {
                url = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucketName)
                                .object(file)
                                .expiry(1, TimeUnit.DAYS)
                                .build());
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } catch (InsufficientDataException e) {
                e.printStackTrace();
            } catch (InternalException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidResponseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (XmlParserException e) {
                e.printStackTrace();
            } catch (ServerException e) {
                e.printStackTrace();
            }


            return new ImageUrl(url);
        }).collect(Collectors.toList());


        return response;

    }

}
