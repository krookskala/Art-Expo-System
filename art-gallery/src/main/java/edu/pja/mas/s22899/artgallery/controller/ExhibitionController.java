package edu.pja.mas.s22899.artgallery.controller;

import edu.pja.mas.s22899.artgallery.dto.ArtworkSummaryDTO;
import edu.pja.mas.s22899.artgallery.dto.ExhibitionDetailDTO;
import edu.pja.mas.s22899.artgallery.dto.ExhibitionSummaryDTO;
import edu.pja.mas.s22899.artgallery.model.*;
import edu.pja.mas.s22899.artgallery.repository.ExhibitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
public class ExhibitionController {

    private final ExhibitionRepository exhibitionRepository;

    /**
     * "Fetch All Exhibition Records"
     */
    @GetMapping
    public ResponseEntity<List<ExhibitionSummaryDTO>> getAllExhibitions() {
        List<Exhibition> allExhibitions = exhibitionRepository.findAll();
        System.out.println("DEBUG: Found " + allExhibitions.size() + " total exhibitions in the database.");
        List<ExhibitionSummaryDTO> relevantExhibitions = allExhibitions.stream()
                .sorted(Comparator.comparing(Exhibition::getStartDate))
                .map(e -> ExhibitionSummaryDTO.builder()
                        .id(e.getId())
                        .title(e.getTitle())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .build())
                .collect(Collectors.toList());
        System.out.println("DEBUG: Returning " + relevantExhibitions.size() + " active or upcoming exhibitions to the GUI.");
        return ResponseEntity.ok(relevantExhibitions);
    }

    /**
     * "Retrieve Selected Exhibition Details"
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ExhibitionDetailDTO> getExhibitionDetails(@PathVariable long id) {
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exhibition Not Found With ID: " + id));

        String type = "Unknown";
        if (exhibition instanceof ThemedPermanentExhibition || exhibition instanceof ThemedTemporaryExhibition) {
            type = "Themed";
        } else if (exhibition instanceof IndividualPermanentExhibition || exhibition instanceof IndividualTemporaryExhibition) {
            type = "Individual";
        }

        ExhibitionDetailDTO dto = ExhibitionDetailDTO.builder()
                .id(exhibition.getId())
                .title(exhibition.getTitle())
                .description(exhibition.getDescription())
                .type(type)
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .artworkCapacity(exhibition.getArtworkCapacity())
                .build();

        return ResponseEntity.ok(dto);
    }

    /**
     * "Find Artwork Links for Exhibition" .
     */
    @GetMapping("/{id}/artworks")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ArtworkSummaryDTO>> getArtworksForExhibition(@PathVariable long id) {
        Exhibition exhibition = exhibitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exhibition Not Found With ID: " + id));
        List<ArtworkSummaryDTO> artworks = exhibition.getAssociatedArtworks().stream()
                .map(artwork -> ArtworkSummaryDTO.builder()
                        .id(artwork.getId())
                        .title(artwork.getTitle())
                        .artistNames(artwork.getCreators().stream()
                                .map(artist -> artist.getFirstName() + " " + artist.getLastName())
                                .collect(Collectors.joining(", ")))
                        .creationYear(artwork.getCreationYear())
                        .genre(artwork.getGenre().toString())
                        .inventoryNumber(String.valueOf(artwork.getInventoryNumber()))
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(artworks);
    }
}