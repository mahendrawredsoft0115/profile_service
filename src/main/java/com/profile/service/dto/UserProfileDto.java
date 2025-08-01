package com.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String uuid;
    private String profileImageUrl;
    private String coverImageUrl;
    private String bio;
    private String facebookLink;
    private String instagramLink;
}
