        package com.profile.service.entity;

        import jakarta.persistence.*;
        import lombok.*;

        /**
         * Entity representing a Creator's Profile.
         * Maps to the table 'creator_profiles' in the database.
         */
        @Entity
        @Table(name = "creator_profiles")
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public class CreatorProfile {

            /**
             * Primary key (auto-generated).
             */
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            /**
             * Unique identifier for the creator (UUID from auth/user service).
             */
            @Column(name = "creator_uuid", nullable = false, unique = true)
            private String creatorUuid;

            /**
             * URL to the creator's profile picture.
             */
            @Column(name = "profile_picture_url")
            private String profilePictureUrl;

            /**
             * URL to the creator's cover picture.
             */
            @Column(name = "cover_picture_url")
            private String coverPictureUrl;


            /**
             * Username to creator's
             */
            @Column(name="username")
            private String usernme;


            /**
             * Short biography of the creator (max 1000 chars).
             */
            @Column(name = "bio", length = 1000)
            private String bio;

            /**
             * Link to the creator's Instagram profile.
             */
            @Column(name = "instagram_link")
            private String instagramLink;

            /**
             * Link to the creator's Facebook profile.
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
             * Sets timestamps before inserting a new record.
             */
            @PrePersist
            protected void onCreate() {
                this.createdAt = System.currentTimeMillis();
                this.updatedAt = this.createdAt;
            }

            /**
             * Updates the timestamp before updating the record.
             */
            @PreUpdate
            protected void onUpdate() {
                this.updatedAt = System.currentTimeMillis();
            }
        }
