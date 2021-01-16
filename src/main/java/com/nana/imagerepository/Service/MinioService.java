package com.nana.imagerepository.Service;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {


    @Value("${minio.url}")
    private String minioUrl;
    @Value("${access.key}")
    private String accessKey;
    @Value("${secret.key}")
    private String secretKey;
    @Value("${bucket.name}")
    private String bucketName;

    public String saveToObjectStorage (MultipartFile file, String fileName,  String permission) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
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


            return response.object();

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }

        System.out.println(response.etag());
       return response.etag();
    }

}
