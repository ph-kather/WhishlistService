package edu.wunschzettel.repository;

import edu.wunschzettel.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishRepository extends JpaRepository<Wish, UUID> {
    Optional<Wish> findByKid(String kid);
}
