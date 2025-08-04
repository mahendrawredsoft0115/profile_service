package com.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing the user's public profile.
 * Used to expose profile information through APIs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    /**
     * Unique identifier for the user.
     */
    private String uuid;

    /**
     * URL to the user's profile image.
     */
    private String profileImageUrl;

    /**
     * URL to the user's cover image.
     */
    private String coverImageUrl;

    /**
     * Short biography or description of the user.
     */
    private String bio;

    /**
     * Link to the user's Facebook profile.
     */
    private String facebookLink;

    /**
     * Link to the user's Instagram profile.
     */
    private String instagramLink;
}
