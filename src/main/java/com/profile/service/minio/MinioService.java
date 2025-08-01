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

@Service
@RequiredArgsConstructor
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    private final MinioClient minioClient;

    @Value("${minio.base-url}")
    private String baseUrl;

    private static final String bucketName = Constants.PROFILE_BUCKET;

    /**
     * Uploads a file to MinIO under a specified folder path.
     *
     * @param file       Multipart file to upload
     * @param folderPath Folder path inside the bucket (e.g. "user/profile-pic/")
     * @return Public file URL
     */
    public String uploadFile(MultipartFile file, String folderPath) {
        try {
            // Check if bucket exists
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // Get file extension
            String extension = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf(".")))
                    .orElse("");

            // Generate unique file name
            String objectName = folderPath + UUID.randomUUID() + extension;

            // Upload to MinIO
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

            // Return full file URL
            return baseUrl + "/" + bucketName + "/" + objectName;

        } catch (Exception e) {
            logger.error("❌ Error uploading to MinIO: ", e);
            throw new CustomException("File upload failed: " + e.getMessage());
        }
    }
}
