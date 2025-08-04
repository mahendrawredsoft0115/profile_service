package com.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for updating a user's profile.
 * Contains optional fields such as bio and social media links.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    /**
     * Short biography or description about the user.
     */
    private String bio;

    /**
     * URL to the user's Facebook profile.
     */
    private String facebookLink;

    /**
     * URL to the user's Instagram profile.
     */
    private String instagramLink;
}
