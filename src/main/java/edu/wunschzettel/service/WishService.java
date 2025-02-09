package edu.wunschzettel.service;

import edu.wunschzettel.model.Wish;
import edu.wunschzettel.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;

    public List<Wish> getAllWishes() {
        return wishRepository.findAll();
    }

    public Optional<Wish> getWishById(UUID id) {
        return wishRepository.findById(id);
    }

    public Wish createWish(String owner, String wish, int age) {
        Wish newWish = Wish.builder()
            .owner(owner)
            .wish(wish)
            .age(age)
            .build();
        return wishRepository.save(newWish);
    }

    public void deleteWish(UUID id) {
        wishRepository.deleteById(id);
    }
}
