package com.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Response object for media file upload operations.
 * Contains the uploaded file's URL and a status message.
 */
@Data
@Builder
@AllArgsConstructor
public class MediaUploadResponse {

    /**
     * The full URL where the uploaded media file is accessible.
     */
    private String fileUrl;

    /**
     * A descriptive message about the upload status.
     */
    private String message;
}
