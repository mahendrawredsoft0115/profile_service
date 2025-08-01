package com.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MediaUploadResponse {
    private String fileUrl;
    private String message;
}
