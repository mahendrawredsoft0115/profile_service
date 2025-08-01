package com.profile.service.service;

import com.profile.service.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    UserProfileDto getUserProfile(String userId);

    CreatorProfileDto getCreatorProfile(String userId);

    UserProfileDto updateUserProfile(String userId, UpdateProfileRequest request);

    CreatorProfileDto updateCreatorProfile(String userId, UpdateProfileRequest request);

    MediaUploadResponse uploadUserProfileImage(String userId, MultipartFile file);

    MediaUploadResponse uploadUserCoverImage(String userId, MultipartFile file);

    MediaUploadResponse uploadCreatorProfileImage(String userId, MultipartFile file);

    MediaUploadResponse uploadCreatorCoverImage(String userId, MultipartFile file);
}
