package edu.pja.mas.s22899.artgallery.service;

import edu.pja.mas.s22899.artgallery.model.Artwork;
import edu.pja.mas.s22899.artgallery.model.Exhibition;
import edu.pja.mas.s22899.artgallery.model.Section;
import edu.pja.mas.s22899.artgallery.repository.ArtworkRepository;
import edu.pja.mas.s22899.artgallery.repository.ExhibitionRepository;
import edu.pja.mas.s22899.artgallery.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final SectionRepository sectionRepository;
    private final ArtworkRepository artworkRepository;

    @Transactional
    public Exhibition createExhibition(Exhibition exhibition, List<Long> sectionIds) {
        Exhibition savedExhibition = exhibitionRepository.save(exhibition);

        for (Long sectionId : sectionIds) {
            Section section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new EntityNotFoundException("Section Not Found With ID: " + sectionId));
            section.assignExhibition(savedExhibition);
        }
        return savedExhibition;
    }

    @Transactional
    public void addArtworkToExhibition(long exhibitionId, long artworkId, LocalDate displayStart, LocalDate displayEnd) {
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new EntityNotFoundException("Exhibition Not Found With ID: " + exhibitionId));
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new EntityNotFoundException("Artwork Not Found With ID: " + artworkId));
        exhibition.addArtworkToDisplay(artwork, displayStart, displayEnd);
    }

    @Transactional
    public Exhibition updateExhibition(Exhibition exhibition) {
        if (exhibition == null || exhibition.getId() == 0) {
            throw new IllegalArgumentException("Cannot update an exhibition without an ID.");
        }
        return exhibitionRepository.save(exhibition);
    }

    @Transactional
    public void closeExhibition(long exhibitionId) {
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new EntityNotFoundException("Exhibition Not Found With ID: " + exhibitionId));
        exhibition.closeExhibition();
    }
}