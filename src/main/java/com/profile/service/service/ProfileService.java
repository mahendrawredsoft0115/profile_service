package com.profile.service.service;

import com.profile.service.dto.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface defining operations related to user and creator profiles.
 */
public interface ProfileService {

    /**
     * Fetches the basic profile information of a user.
     *
     * @param userId UUID of the user
     * @return UserProfileDto containing user's profile data
     */
    UserProfileDto getUserProfile(String userId);

    /**
     * Fetches the public creator profile information.
     *
     * @param userId UUID of the creator
     * @return CreatorProfileDto containing creator-specific details
     */
    CreatorProfileDto getCreatorProfile(String userId);

    /**
     * Updates the profile information of a user.
     *
     * @param userId  UUID of the user
     * @param request Update request containing profile fields
     * @return Updated UserProfileDto
     */
    UserProfileDto updateUserProfile(String userId, UpdateProfileRequest request);

    /**
     * Updates the profile information of a creator.
     *
     * @param userId  UUID of the creator
     * @param request Update request containing profile fields
     * @return Updated CreatorProfileDto
     */
    CreatorProfileDto updateCreatorProfile(String userId, UpdateProfileRequest request);

    /**
     * Uploads a profile image for a user.
     *
     * @param userId UUID of the user
     * @param file   Profile image file
     * @return MediaUploadResponse containing file access URL
     */
    MediaUploadResponse uploadUserProfileImage(String userId, MultipartFile file);

    /**
     * Uploads a cover image for a user.
     *
     * @param userId UUID of the user
     * @param file   Cover image file
     * @return MediaUploadResponse containing file access URL
     */
    MediaUploadResponse uploadUserCoverImage(String userId, MultipartFile file);

    /**
     * Uploads a profile image for a creator.
     *
     * @param userId UUID of the creator
     * @param file   Profile image file
     * @return MediaUploadResponse containing file access URL
     */
    MediaUploadResponse uploadCreatorProfileImage(String userId, MultipartFile file);

    /**
     * Uploads a cover image for a creator.
     *
     * @param userId UUID of the creator
     * @param file   Cover image file
     * @return MediaUploadResponse containing file access URL
     */
    MediaUploadResponse uploadCreatorCoverImage(String userId, MultipartFile file);
}
