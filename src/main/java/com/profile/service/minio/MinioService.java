package com.profile.service.minio;

import com.profile.service.exception.CustomException;
import com.profile.service.util.Constants;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for interacting with MinIO for file uploads.
 * Ensures bucket existence, uploads files, and returns accessible file URLs.
 */
@Service
@RequiredArgsConstructor
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    private final MinioClient minioClient;

    @Value("${minio.base-url}")
    private String baseUrl;

    private static final String bucketName = Constants.PROFILE_BUCKET;

    /**
     * Uploads a file to MinIO under the specified folder path.
     *
     * @param file       Multipart file to upload
     * @param folderPath Folder path inside the bucket (e.g., "user/profile-pic/")
     * @return Public URL to access the uploaded file
     * @throws CustomException if any error occurs during upload
     */
    public String uploadFile(MultipartFile file, String folderPath) {
        try {

            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String extension = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf(".")))
                    .orElse("");

            String objectName = folderPath + UUID.randomUUID() + extension;

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            return baseUrl + "/" + bucketName + "/" + objectName;

        } catch (Exception e) {
            logger.error("Error uploading file to MinIO", e);
            throw new CustomException("File upload failed: " + e.getMessage());
        }
    }
}
