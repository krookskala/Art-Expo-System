package edu.pja.mas.s22899.artgallery.repository;

import edu.pja.mas.s22899.artgallery.model.ArtGenre;
import edu.pja.mas.s22899.artgallery.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    // Find artwork by its unique inventory number
    Optional<Artwork> findByInventoryNumber(int inventoryNumber);

    // Find artworks by a part of their title
    List<Artwork> findByTitleContainingIgnoreCase(String titleFragment);

    // Find all artworks of a specific genre
    List<Artwork> findByGenre(ArtGenre genre);

    // Find all artworks within a given price range
    List<Artwork> findByPriceBetween(double minPrice, double maxPrice);

    // Find all artworks created in a specific year
    List<Artwork> findByCreationYear(int year);
}
