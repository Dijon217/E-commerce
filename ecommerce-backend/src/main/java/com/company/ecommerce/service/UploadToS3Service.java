package com.company.ecommerce.service;

import com.company.ecommerce.general.S3KeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

@Service
public class UploadToS3Service {

    private final S3Client s3Client;

    public UploadToS3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public String uploadFileToBucket(MultipartFile file, String name, String type) throws IOException {
        String s3Key = S3KeyGenerator.builder()
                .originalFileName(file.getName())
                .build()
                .generateKey(name, type);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .acl("public-read")
                .contentType("image/png")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return  "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + s3Key;
    }

    public String imageSkey(String imagePath){
        int lastSlashIndex = imagePath.lastIndexOf('/');
        if(lastSlashIndex == -1){
            return "Invalid";
        }
        return imagePath.substring(lastSlashIndex + 1);
    }

    public void deleteFileBucket(String imageUrl){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(imageSkey(imageUrl))
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }


}
