package edu.pja.mas.s22899.artgallery.service;

import edu.pja.mas.s22899.artgallery.model.Gallery;
import edu.pja.mas.s22899.artgallery.model.Section;
import edu.pja.mas.s22899.artgallery.model.SectionStatus;
import edu.pja.mas.s22899.artgallery.repository.GalleryRepository;
import edu.pja.mas.s22899.artgallery.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final SectionRepository sectionRepository;

    @Transactional
    public Gallery createGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    @Transactional
    public Section addSectionToGallery(long galleryId, Section section) {
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new EntityNotFoundException("Gallery not found with id: " + galleryId));
        gallery.addSection(section);
        return sectionRepository.save(section);
    }

    @Transactional(readOnly = true)
    public List<Section> findAvailableSections(long galleryId) {
        if (!galleryRepository.existsById(galleryId)) {
            throw new EntityNotFoundException("Gallery not found with id: " + galleryId);
        }
        return sectionRepository.findByStatus(SectionStatus.FREE);
    }

    @Transactional
    public void decommissionSection(long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException("Section not found with id: " + sectionId));
        section.decommissionSection();
    }

    @Transactional
    public Section updateSection(Section section) {
        if (section == null || section.getId() == 0) {
            throw new IllegalArgumentException("Cannot update a section without an ID.");
        }
        return sectionRepository.save(section);
    }
}
