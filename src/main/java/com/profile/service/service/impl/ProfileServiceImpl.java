//package com.profile.service.service.impl;
//
//import com.profile.service.dto.*;
//import com.profile.service.entity.CreatorProfile;
//import com.profile.service.entity.UserProfile;
//import com.profile.service.exception.ResourceNotFoundException;
//import com.profile.service.minio.MinioService;
//import com.profile.service.repository.CreatorProfileRepository;
//import com.profile.service.repository.UserProfileRepository;
//import com.profile.service.service.ProfileService;
//import com.profile.service.util.Constants;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * Implementation of the ProfileService interface that handles business logic
// * for user and creator profile operations including CRUD and media uploads.
// */
//@Service
//@RequiredArgsConstructor
//public class ProfileServiceImpl implements ProfileService {
//
//    private final UserProfileRepository userProfileRepository;
//    private final CreatorProfileRepository creatorProfileRepository;
//    private final MinioService minioService;
//
//    // === USER PROFILE ===
//
//    /**
//     * Fetches the user profile by UUID.
//     *
//     * @param uuid user UUID
//     * @return UserProfileDto containing user profile data
//     * @throws ResourceNotFoundException if profile is not found
//     */
//    @Override
//    public UserProfileDto getUserProfile(String uuid) {
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
//        return mapToUserProfileDto(profile);
//    }
//
//    /**
//     * Updates or creates a user profile with the given data.
//     *
//     * @param uuid    user UUID
//     * @param request update request data
//     * @return updated UserProfileDto
//     */
//    @Override
//    public UserProfileDto updateUserProfile(String uuid, UpdateProfileRequest request) {
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseGet(() -> userProfileRepository.save(
//                        UserProfile.builder().userUuid(uuid).build()
//                ));
//
//        profile.setBio(request.getBio());
//        profile.setInstagramLink(request.getInstagramLink());
//        profile.setFacebookLink(request.getFacebookLink());
//
//        userProfileRepository.save(profile);
//        return mapToUserProfileDto(profile);
//    }
//
//    /**
//     * Uploads a user's profile image to storage and updates profile record.
//     *
//     * @param uuid user UUID
//     * @param file image file
//     * @return upload response containing image URL
//     */
//    @Override
//    public MediaUploadResponse uploadUserProfileImage(String uuid, MultipartFile file) {
//        String url = minioService.uploadFile(file, Constants.USER_PROFILE_PIC_FOLDER);
//
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseGet(() -> userProfileRepository.save(
//                        UserProfile.builder().userUuid(uuid).build()
//                ));
//
//        profile.setProfilePictureUrl(url);
//        userProfileRepository.save(profile);
//        return new MediaUploadResponse(url, "User profile image uploaded successfully.");
//    }
//
//    /**
//     * Uploads a user's cover image to storage and updates profile record.
//     *
//     * @param uuid user UUID
//     * @param file image file
//     * @return upload response containing image URL
//     */
//    @Override
//    public MediaUploadResponse uploadUserCoverImage(String uuid, MultipartFile file) {
//        String url = minioService.uploadFile(file, Constants.USER_COVER_PIC_FOLDER);
//
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseGet(() -> userProfileRepository.save(
//                        UserProfile.builder().userUuid(uuid).build()
//                ));
//
//        profile.setCoverPictureUrl(url);
//        userProfileRepository.save(profile);
//        return new MediaUploadResponse(url, "User cover image uploaded successfully.");
//    }
//
//    // === CREATOR PROFILE ===
//
//    /**
//     * Fetches the creator profile by UUID.
//     *
//     * @param uuid creator UUID
//     * @return CreatorProfileDto
//     * @throws ResourceNotFoundException if profile not found
//     */
//    @Override
//    public CreatorProfileDto getCreatorProfile(String uuid) {
//        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));
//        return mapToCreatorProfileDto(profile);
//    }
//
//    /**
//     * Updates or creates a creator profile with given data.
//     *
//     * @param uuid    creator UUID
//     * @param request update request
//     * @return updated CreatorProfileDto
//     */
//    @Override
//    public CreatorProfileDto updateCreatorProfile(String uuid, UpdateProfileRequest request) {
//        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
//                .orElseGet(() -> CreatorProfile.builder().creatorUuid(uuid).build());
//
//        profile.setBio(request.getBio());
//        profile.setInstagramLink(request.getInstagramLink());
//        profile.setFacebookLink(request.getFacebookLink());
//
//        creatorProfileRepository.save(profile);
//        return mapToCreatorProfileDto(profile);
//    }
//
//    /**
//     * Uploads a creator's profile image to storage and updates profile record.
//     *
//     * @param uuid creator UUID
//     * @param file image file
//     * @return upload response with image URL
//     */
//    @Override
//    public MediaUploadResponse uploadCreatorProfileImage(String uuid, MultipartFile file) {
//        String url = minioService.uploadFile(file, Constants.CREATOR_PROFILE_PIC_FOLDER);
//        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));
//
//        profile.setProfilePictureUrl(url);
//        creatorProfileRepository.save(profile);
//        return new MediaUploadResponse(url, "Creator profile image uploaded successfully.");
//    }
//
//    /**
//     * Uploads a creator's cover image to storage and updates profile record.
//     *
//     * @param uuid creator UUID
//     * @param file image file
//     * @return upload response with image URL
//     */
//    @Override
//    public MediaUploadResponse uploadCreatorCoverImage(String uuid, MultipartFile file) {
//        String url = minioService.uploadFile(file, Constants.CREATOR_COVER_PIC_FOLDER);
//        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));
//
//        profile.setCoverPictureUrl(url);
//        creatorProfileRepository.save(profile);
//        return new MediaUploadResponse(url, "Creator cover image uploaded successfully.");
//    }
//
//    // === MAPPERS ===
//
//    /**
//     * Maps UserProfile entity to DTO.
//     *
//     * @param profile UserProfile entity
//     * @return UserProfileDto
//     */
//    private UserProfileDto mapToUserProfileDto(UserProfile profile) {
//        return UserProfileDto.builder()
//                .uuid(profile.getUserUuid())
//                .bio(profile.getBio())
//                .profileImageUrl(profile.getProfilePictureUrl())
//                .coverImageUrl(profile.getCoverPictureUrl())
//                .facebookLink(profile.getFacebookLink())
//                .instagramLink(profile.getInstagramLink())
//                .build();
//    }
//
//    /**
//     * Maps CreatorProfile entity to DTO.
//     *
//     * @param profile CreatorProfile entity
//     * @return CreatorProfileDto
//     */
//    private CreatorProfileDto mapToCreatorProfileDto(CreatorProfile profile) {
//        return CreatorProfileDto.builder()
//                .uuid(profile.getCreatorUuid())
//                .bio(profile.getBio())
//                .profileImageUrl(profile.getProfilePictureUrl())
//                .coverImageUrl(profile.getCoverPictureUrl())
//                .facebookLink(profile.getFacebookLink())
//                .instagramLink(profile.getInstagramLink())
//                .build();
//    }
//}





package com.profile.service.service.impl;

import com.profile.service.dto.*;
import com.profile.service.entity.CreatorProfile;
import com.profile.service.entity.UserProfile;
import com.profile.service.exception.ResourceNotFoundException;
import com.profile.service.minio.MinioService;
import com.profile.service.repository.CreatorProfileRepository;
import com.profile.service.repository.UserProfileRepository;
import com.profile.service.service.ProfileService;
import com.profile.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service implementation for handling business logic related to user and creator profiles,
 * including CRUD operations and media uploads.
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;
    private final CreatorProfileRepository creatorProfileRepository;
    private final MinioService minioService;

    // === USER PROFILE METHODS ===

    /**
     * Retrieves a user profile by UUID.
     *
     * @param uuid the user UUID
     * @return UserProfileDto
     * @throws ResourceNotFoundException if user profile not found
     */
    @Override
    public UserProfileDto getUserProfile(String uuid) {
        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
        return mapToUserProfileDto(profile);
    }

    /**
     * Updates or creates a user profile based on UUID.
     *
     * @param uuid    user UUID
     * @param request update request object
     * @return updated UserProfileDto
     */
    @Override
    public UserProfileDto updateUserProfile(String uuid, UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseGet(() -> userProfileRepository.save(
                        UserProfile.builder().userUuid(uuid).build()
                ));

        profile.setBio(request.getBio());
        profile.setInstagramLink(request.getInstagramLink());
        profile.setFacebookLink(request.getFacebookLink());

        userProfileRepository.save(profile);
        return mapToUserProfileDto(profile);
    }

    /**
     * Uploads a profile image for a user and updates the profile record.
     *
     * @param uuid user UUID
     * @param file image file
     * @return MediaUploadResponse with file URL
     */
    @Override
    public MediaUploadResponse uploadUserProfileImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.USER_PROFILE_PIC_FOLDER);

        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseGet(() -> userProfileRepository.save(
                        UserProfile.builder().userUuid(uuid).build()
                ));

        profile.setProfilePictureUrl(url);
        userProfileRepository.save(profile);
        return new MediaUploadResponse(url, "User profile image uploaded successfully.");
    }

    /**
     * Uploads a cover image for a user and updates the profile record.
     *
     * @param uuid user UUID
     * @param file image file
     * @return MediaUploadResponse with file URL
     */
    @Override
    public MediaUploadResponse uploadUserCoverImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.USER_COVER_PIC_FOLDER);

        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseGet(() -> userProfileRepository.save(
                        UserProfile.builder().userUuid(uuid).build()
                ));

        profile.setCoverPictureUrl(url);
        userProfileRepository.save(profile);
        return new MediaUploadResponse(url, "User cover image uploaded successfully.");
    }

    // === CREATOR PROFILE METHODS ===

    /**
     * Retrieves a creator profile by UUID.
     *
     * @param uuid creator UUID
     * @return CreatorProfileDto
     * @throws ResourceNotFoundException if profile not found
     */
    @Override
    public CreatorProfileDto getCreatorProfile(String uuid) {
        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));
        return mapToCreatorProfileDto(profile);
    }

    /**
     * Updates or creates a creator profile.
     *
     * @param uuid    creator UUID
     * @param request update request object
     * @return updated CreatorProfileDto
     */
    @Override
    public CreatorProfileDto updateCreatorProfile(String uuid, UpdateProfileRequest request) {
        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseGet(() -> creatorProfileRepository.save(
                        CreatorProfile.builder().creatorUuid(uuid).build()
                ));

        profile.setBio(request.getBio());
        profile.setInstagramLink(request.getInstagramLink());
        profile.setFacebookLink(request.getFacebookLink());

        creatorProfileRepository.save(profile);
        return mapToCreatorProfileDto(profile);
    }

    /**
     * Uploads a profile image for a creator and updates the profile record.
     * Creates the profile if it doesn't exist.
     *
     * @param uuid creator UUID
     * @param file image file
     * @return MediaUploadResponse with file URL
     */
    @Override
    public MediaUploadResponse uploadCreatorProfileImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.CREATOR_PROFILE_PIC_FOLDER);

        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseGet(() -> creatorProfileRepository.save(
                        CreatorProfile.builder().creatorUuid(uuid).build()
                ));

        profile.setProfilePictureUrl(url);
        creatorProfileRepository.save(profile);
        return new MediaUploadResponse(url, "Creator profile image uploaded successfully.");
    }

    /**
     * Uploads a cover image for a creator and updates the profile record.
     * Creates the profile if it doesn't exist.
     *
     * @param uuid creator UUID
     * @param file image file
     * @return MediaUploadResponse with file URL
     */
    @Override
    public MediaUploadResponse uploadCreatorCoverImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.CREATOR_COVER_PIC_FOLDER);

        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseGet(() -> creatorProfileRepository.save(
                        CreatorProfile.builder().creatorUuid(uuid).build()
                ));

        profile.setCoverPictureUrl(url);
        creatorProfileRepository.save(profile);
        return new MediaUploadResponse(url, "Creator cover image uploaded successfully.");
    }

    // === PRIVATE MAPPERS ===

    /**
     * Converts UserProfile entity to DTO.
     *
     * @param profile UserProfile
     * @return UserProfileDto
     */
    private UserProfileDto mapToUserProfileDto(UserProfile profile) {
        return UserProfileDto.builder()
                .uuid(profile.getUserUuid())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfilePictureUrl())
                .coverImageUrl(profile.getCoverPictureUrl())
                .facebookLink(profile.getFacebookLink())
                .instagramLink(profile.getInstagramLink())
                .build();
    }

    /**
     * Converts CreatorProfile entity to DTO.
     *
     * @param profile CreatorProfile
     * @return CreatorProfileDto
     */
    private CreatorProfileDto mapToCreatorProfileDto(CreatorProfile profile) {
        return CreatorProfileDto.builder()
                .uuid(profile.getCreatorUuid())
                .bio(profile.getBio())
                .profileImageUrl(profile.getProfilePictureUrl())
                .coverImageUrl(profile.getCoverPictureUrl())
                .facebookLink(profile.getFacebookLink())
                .instagramLink(profile.getInstagramLink())
                .build();
    }
}

