package com.profile.service.repository;

import com.profile.service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserUuid(String uuid);

    boolean existsByUserUuid(String uuid);
}
