package edu.pja.mas.s22899.artgallery.repository;

import edu.pja.mas.s22899.artgallery.model.Curator;
import edu.pja.mas.s22899.artgallery.model.Section;
import edu.pja.mas.s22899.artgallery.model.SectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    // Find a section by its unique identifier code
    Optional<Section> findBySectionIdentifier(String identifier);

    // Find all sections with a specific status (for example all FREE sections)
    List<Section> findByStatus(SectionStatus status);

    // Find all sections managed by a specific curator
    List<Section> findByManager(Curator manager);
}
