package edu.pja.mas.s22899.artgallery.service;

import edu.pja.mas.s22899.artgallery.model.Artist;
import edu.pja.mas.s22899.artgallery.model.Curator;
import edu.pja.mas.s22899.artgallery.model.Gallery;
import edu.pja.mas.s22899.artgallery.repository.ArtistRepository;
import edu.pja.mas.s22899.artgallery.repository.CuratorRepository;
import edu.pja.mas.s22899.artgallery.repository.GalleryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ArtistRepository artistRepository;
    private final CuratorRepository curatorRepository;
    private final GalleryRepository galleryRepository;

    @Transactional
    public Artist hireArtist(long galleryId, Artist artist) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new EntityNotFoundException("Gallery Not Found With ID: " + galleryId));
        gallery.addMember(artist);
        return artistRepository.save(artist);
    }

    @Transactional
    public Curator hireCurator(long galleryId, Curator curator) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new EntityNotFoundException("Gallery Not Found With ID: " + galleryId));
        gallery.addMember(curator);
        return curatorRepository.save(curator);
    }

    @Transactional
    public Curator updateCurator(Curator curator) {
        if (curator == null || curator.getId() == 0) {
            throw new IllegalArgumentException("Cannot update a curator without an ID.");
        }
        return curatorRepository.save(curator);
    }
}
