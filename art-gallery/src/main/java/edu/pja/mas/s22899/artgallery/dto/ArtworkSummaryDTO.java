package edu.pja.mas.s22899.artgallery.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtworkSummaryDTO {
    private long id;
    private String title;
    private String artistNames;
    private int creationYear;
    private String genre;
    private String inventoryNumber;
}
