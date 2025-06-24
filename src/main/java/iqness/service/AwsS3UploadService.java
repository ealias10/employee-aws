package iqness.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import iqness.config.AwsS3BucketConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
public class AwsS3UploadService implements StorageService {
    private final AmazonS3 amazonS3Client;

    private final AwsS3BucketConfig awsS3BucketConfig;

    @Override
    public void uploadToStorage(MultipartFile file, String fileName) throws IOException {
        try {
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3Client.putObject(
                    new PutObjectRequest(
                            awsS3BucketConfig.getBucketName(),
                            fileName,
                            file.getInputStream(),
                            objectMetadata));
            log.info("Uploaded to s3 bucket successfully, filename: {}", fileName);
        } catch (Exception exception) {
            log.error("Error while uploading file, filename :{}", fileName);
            throw exception;
        }
    }

    @Override
    public URL createLink(String fileName, Long expiryTime) {
        try {
            var expiryDate = new Date(expiryTime);
            return amazonS3Client.generatePresignedUrl(
                    awsS3BucketConfig.getBucketName(), fileName, expiryDate, HttpMethod.GET);
        } catch (Exception exception) {
            log.error("Error while creating link, filename :{}", fileName);
            throw exception;
        }
    }
}
