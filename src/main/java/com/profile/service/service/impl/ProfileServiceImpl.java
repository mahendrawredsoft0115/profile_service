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
//    @Override
//    public UserProfileDto getUserProfile(String uuid) {
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
//        return mapToUserProfileDto(profile);
//    }
//
//    @Override
//    public UserProfileDto updateUserProfile(String uuid, UpdateProfileRequest request) {
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseGet(() -> UserProfile.builder().userUuid(uuid).build());
//
//        profile.setBio(request.getBio());
//        profile.setInstagramLink(request.getInstagramLink());
//        profile.setFacebookLink(request.getFacebookLink());
//
//        userProfileRepository.save(profile);
//        return mapToUserProfileDto(profile);
//    }
//
//    @Override
//    public MediaUploadResponse uploadUserProfileImage(String uuid, MultipartFile file) {
//        String url = minioService.uploadFile(file, Constants.USER_PROFILE_PIC_FOLDER);
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
//
//        profile.setProfilePictureUrl(url);
//        userProfileRepository.save(profile);
//        return new MediaUploadResponse(url, "User profile image uploaded successfully.");
//    }
//
//    @Override
//    public MediaUploadResponse uploadUserCoverImage(String uuid, MultipartFile file) {
//        String url = minioService.uploadFile(file, Constants.USER_COVER_PIC_FOLDER);
//        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
//
//        profile.setCoverPictureUrl(url);
//        userProfileRepository.save(profile);
//        return new MediaUploadResponse(url, "User cover image uploaded successfully.");
//    }
//
//    // === CREATOR PROFILE ===
//
//    @Override
//    public CreatorProfileDto getCreatorProfile(String uuid) {
//        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));
//        return mapToCreatorProfileDto(profile);
//    }
//
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

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;
    private final CreatorProfileRepository creatorProfileRepository;
    private final MinioService minioService;

    // === USER PROFILE ===

    @Override
    public UserProfileDto getUserProfile(String uuid) {
        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found"));
        return mapToUserProfileDto(profile);
    }

    @Override
    public UserProfileDto updateUserProfile(String uuid, UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder()
                            .userUuid(uuid)
                            .build();
                    return userProfileRepository.save(newProfile);
                });

        profile.setBio(request.getBio());
        profile.setInstagramLink(request.getInstagramLink());
        profile.setFacebookLink(request.getFacebookLink());

        userProfileRepository.save(profile);
        return mapToUserProfileDto(profile);
    }

    @Override
    public MediaUploadResponse uploadUserProfileImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.USER_PROFILE_PIC_FOLDER);

        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder()
                            .userUuid(uuid)
                            .build();
                    return userProfileRepository.save(newProfile);
                });

        profile.setProfilePictureUrl(url);
        userProfileRepository.save(profile);
        return new MediaUploadResponse(url, "User profile image uploaded successfully.");
    }

    @Override
    public MediaUploadResponse uploadUserCoverImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.USER_COVER_PIC_FOLDER);

        UserProfile profile = userProfileRepository.findByUserUuid(uuid)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder()
                            .userUuid(uuid)
                            .build();
                    return userProfileRepository.save(newProfile);
                });

        profile.setCoverPictureUrl(url);
        userProfileRepository.save(profile);
        return new MediaUploadResponse(url, "User cover image uploaded successfully.");
    }

    // === CREATOR PROFILE ===

    @Override
    public CreatorProfileDto getCreatorProfile(String uuid) {
        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));
        return mapToCreatorProfileDto(profile);
    }

    @Override
    public CreatorProfileDto updateCreatorProfile(String uuid, UpdateProfileRequest request) {
        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseGet(() -> CreatorProfile.builder().creatorUuid(uuid).build());

        profile.setBio(request.getBio());
        profile.setInstagramLink(request.getInstagramLink());
        profile.setFacebookLink(request.getFacebookLink());

        creatorProfileRepository.save(profile);
        return mapToCreatorProfileDto(profile);
    }

    @Override
    public MediaUploadResponse uploadCreatorProfileImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.CREATOR_PROFILE_PIC_FOLDER);
        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));

        profile.setProfilePictureUrl(url);
        creatorProfileRepository.save(profile);
        return new MediaUploadResponse(url, "Creator profile image uploaded successfully.");
    }

    @Override
    public MediaUploadResponse uploadCreatorCoverImage(String uuid, MultipartFile file) {
        String url = minioService.uploadFile(file, Constants.CREATOR_COVER_PIC_FOLDER);
        CreatorProfile profile = creatorProfileRepository.findByCreatorUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Creator profile not found"));

        profile.setCoverPictureUrl(url);
        creatorProfileRepository.save(profile);
        return new MediaUploadResponse(url, "Creator cover image uploaded successfully.");
    }

    // === MAPPERS ===

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

