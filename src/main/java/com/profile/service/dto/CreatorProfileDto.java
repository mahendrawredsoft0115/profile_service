package com.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Creator Profile information.
 * This class is used to transfer profile-related data between client and server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorProfileDto {

    /**
     * Unique identifier for the creator (UUID format).
     */
    private String uuid;

    /**
     * URL to the creator's profile image.
     */
    private String profileImageUrl;

    /**
     * URL to the creator's cover/banner image.
     */
    private String coverImageUrl;


    /**
     * Username to the Creator's
     */
    private String username;

    /**
     * Short biography or description of the creator.
     */

    private String bio;

    /**
     * Link to the creator's Facebook profile.
     */
    private String facebookLink;

    /**
     * Link to the creator's Instagram profile.
     */
    private String instagramLink;
}
