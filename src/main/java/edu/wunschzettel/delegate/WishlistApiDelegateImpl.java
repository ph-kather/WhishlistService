package edu.wunschzettel.delegate;

import edu.wunschzettel.api.WishApiDelegate;
import edu.wunschzettel.model.WishDto;
import edu.wunschzettel.model.WishInputDto;
import edu.wunschzettel.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementierung der WishlistApiDelegate-Schnittstelle.
 */
@Service
@RequiredArgsConstructor
public class WishlistApiDelegateImpl implements WishApiDelegate {

    private final WishService wishService;

    @Override
    public ResponseEntity<WishDto> createWish(WishInputDto wishInputDto) {
        var createdWish = wishService.createWish(wishInputDto.getOwner(), wishInputDto.getWish(), wishInputDto.getAge());
        return ResponseEntity.status(201).body(mapToWishDto(createdWish));
    }

    @Override
    public ResponseEntity<Void> deleteWish(UUID id) {
        Optional<WishDto> wish = getWishById(id).getBody() != null ? Optional.of(getWishById(id).getBody()) : Optional.empty();
        if (wish.isPresent()) {
            wishService.deleteWish(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<WishDto>> getAllWishes() {
        var wishes = wishService.getAllWishes()
            .stream()
            .map(this::mapToWishDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(wishes);
    }

    @Override
    public ResponseEntity<WishDto> getWishById(UUID id) {
        return wishService.getWishById(id)
            .map(wish -> ResponseEntity.ok(mapToWishDto(wish)))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Hilfsmethode zur Umwandlung von Wish in WishDto.
     */
    private WishDto mapToWishDto(edu.wunschzettel.model.Wish wish) {
        return new WishDto()
            .id(wish.getId())
            .owner(wish.getOwner())
            .age(wish.getAge())
            .wish(wish.getWish());
    }
}
