package com.profile.service.repository;
import com.profile.service.entity.CreatorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CreatorProfileRepository extends JpaRepository<CreatorProfile, Long> {

    Optional<CreatorProfile> findByCreatorUuid(String uuid);

}