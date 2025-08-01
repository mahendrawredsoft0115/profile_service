package com.profile.service.controller;

import com.profile.service.dto.*;
import com.profile.service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // --- User Profile APIs ---
    @GetMapping("/user/{uuid}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String uuid) {
        return ResponseEntity.ok(profileService.getUserProfile(uuid));
    }

    @PutMapping("/user/{uuid}")
    public ResponseEntity<UserProfileDto> updateUserProfile(@PathVariable String uuid,
                                                            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateUserProfile(uuid, request));
    }

    @PostMapping("/user/{uuid}/upload/profile")
    public ResponseEntity<MediaUploadResponse> uploadUserProfileImage(@PathVariable String uuid,
                                                                      @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadUserProfileImage(uuid, file));
    }

    @PostMapping("/user/{uuid}/upload/cover")
    public ResponseEntity<MediaUploadResponse> uploadUserCoverImage(@PathVariable String uuid,
                                                                    @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadUserCoverImage(uuid, file));
    }

    // --- Creator Profile APIs ---
    @GetMapping("/creator/{uuid}")
    public ResponseEntity<CreatorProfileDto> getCreatorProfile(@PathVariable String uuid) {
        return ResponseEntity.ok(profileService.getCreatorProfile(uuid));
    }

    @PutMapping("/creator/{uuid}")
    public ResponseEntity<CreatorProfileDto> updateCreatorProfile(@PathVariable String uuid,
                                                                  @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateCreatorProfile(uuid, request));
    }

    @PostMapping("/creator/{uuid}/upload/profile")
    public ResponseEntity<MediaUploadResponse> uploadCreatorProfileImage(@PathVariable String uuid,
                                                                         @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadCreatorProfileImage(uuid, file));
    }

    @PostMapping("/creator/{uuid}/upload/cover")
    public ResponseEntity<MediaUploadResponse> uploadCreatorCoverImage(@PathVariable String uuid,
                                                                       @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(profileService.uploadCreatorCoverImage(uuid, file));
    }
}
