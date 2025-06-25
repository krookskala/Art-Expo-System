package edu.pja.mas.s22899.artgallery.service;

import edu.pja.mas.s22899.artgallery.model.Artist;
import edu.pja.mas.s22899.artgallery.model.Artwork;
import edu.pja.mas.s22899.artgallery.repository.ArtistRepository;
import edu.pja.mas.s22899.artgallery.repository.ArtworkRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public Artwork createArtwork(Artwork artwork, List<Long> artistIds) {
        if (artistIds == null || artistIds.isEmpty()) {
            throw new IllegalArgumentException("An Artwork Must Have At Least One Creator.");
        }
        for(Long artistId : artistIds) {
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new EntityNotFoundException("Artist Not Found With ID: " + artistId));
            artwork.addCreator(artist);
        }
        return artworkRepository.save(artwork);
    }

    @Transactional(readOnly = true)
    public double getArtworkPrice(long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new EntityNotFoundException("Artwork Not Found With ID: " + artworkId));
        return artwork.getPrice();
    }

    @Transactional(readOnly = true)
    public int getArtworkTotalVisitorView(long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new EntityNotFoundException("Artwork Not Found With ID: " + artworkId));
        return artwork.getTotalVisitorsView();
    }
}