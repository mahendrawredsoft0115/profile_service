package com.profile.service.controller;

import com.profile.service.dto.*;
import com.profile.service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing user and creator profiles.
 */
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // -------------------- User Profile APIs --------------------

    /**
     * Get user profile by UUID.
     * @param uuid unique user identifier
     * @return UserProfileDto containing profile details
     */
    @GetMapping("/user/{uuid}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String uuid) {
        return ResponseEntity.ok(profileService.getUserProfile(uuid));
    }

    /**
     * Update user profile information.
     * @param uuid unique user identifier
     * @param request updated profile information
     * @return updated UserProfileDto
     */
    @PutMapping("/user/{uuid}")
    public ResponseEntity<UserProfileDto> updateUserProfile(@PathVariable String uuid,
                                                            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateUserProfile(uuid, request));
    }

    /**
     * Upload profile image for a user.
     * @param uuid unique user identifier
     * @param file image file (Multipart)
     * @return response containing file upload metadata
     */
    @PostMapping("/user/{uuid}/upload/profile")
    public ResponseEntity<MediaUploadResponse> uploadUserProfileImage(@PathVariable String uuid,
                                                                      @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadUserProfileImage(uuid, file));
    }

    /**
     * Upload cover image for a user.
     * @param uuid unique user identifier
     * @param file image file (Multipart)
     * @return response containing file upload metadata
     */
    @PostMapping("/user/{uuid}/upload/cover")
    public ResponseEntity<MediaUploadResponse> uploadUserCoverImage(@PathVariable String uuid,
                                                                    @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadUserCoverImage(uuid, file));
    }

    // -------------------- Creator Profile APIs --------------------

    /**
     * Get creator profile by UUID.
     * @param uuid unique creator identifier
     * @return CreatorProfileDto containing profile details
     */
    @GetMapping("/creator/{uuid}")
    public ResponseEntity<CreatorProfileDto> getCreatorProfile(@PathVariable String uuid) {
        return ResponseEntity.ok(profileService.getCreatorProfile(uuid));
    }

    /**
     * Update creator profile information.
     * @param uuid unique creator identifier
     * @param request updated profile information
     * @return updated CreatorProfileDto
     */
    @PutMapping("/creator/{uuid}")
    public ResponseEntity<CreatorProfileDto> updateCreatorProfile(@PathVariable String uuid,
                                                                  @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateCreatorProfile(uuid, request));
    }

    /**
     * Upload profile image for a creator.
     * @param uuid unique creator identifier
     * @param file image file (Multipart)
     * @return response containing file upload metadata
     */
    @PostMapping("/creator/{uuid}/upload/profile")
    public ResponseEntity<MediaUploadResponse> uploadCreatorProfileImage(@PathVariable String uuid,
                                                                         @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadCreatorProfileImage(uuid, file));
    }

    /**
     * Upload cover image for a creator.
     * @param uuid unique creator identifier
     * @param file image file (Multipart)
     * @return response containing file upload metadata
     */
    @PostMapping("/creator/{uuid}/upload/cover")
    public ResponseEntity<MediaUploadResponse> uploadCreatorCoverImage(@PathVariable String uuid,
                                                                       @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadCreatorCoverImage(uuid, file));
    }
}
