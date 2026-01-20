package com.urlspace.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.urlspace.backend.model.Url;
import com.urlspace.backend.model.User;

public interface UrlRepository extends JpaRepository<Url, Long> {

    boolean existsByShortCode(String shortCode);

    Optional<Url> findByShortCode(String shortCode);

    List<Url> findByUser(User user);

    Optional<Url> findByUserAndLongUrl(User user, String longUrl);

}
