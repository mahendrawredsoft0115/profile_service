package com.profile.service.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a general User's Profile.
 * Maps to the 'user_profiles' table in the database.
 */
@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    /**
     * Primary key (auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier for the user (UUID from auth/user service).
     */
    @Column(name = "user_uuid", nullable = false, unique = true)
    private String userUuid;

    /**
     * URL to the user's profile picture.
     */
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    /**
     * URL to the user's cover picture.
     */
    @Column(name = "cover_picture_url")
    private String coverPictureUrl;

    /**
     * Short biography of the user (max 1000 characters).
     */
    @Column(name = "bio", length = 1000)
    private String bio;

    /**
     * Link to the user's Instagram profile.
     */
    @Column(name = "instagram_link")
    private String instagramLink;

    /**
     * Link to the user's Facebook profile.
     */
    @Column(name = "facebook_link")
    private String facebookLink;

    /**
     * Timestamp in milliseconds when the profile was created.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    /**
     * Timestamp in milliseconds when the profile was last updated.
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * Set timestamps before inserting a new profile record.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

    /**
     * Update the timestamp before updating an existing profile record.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}
