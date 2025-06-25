package edu.pja.mas.s22899.artgallerygui.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkSummaryDTO {
    private long id;
    private String title;
    private String artistNames;
    private int creationYear;
    private String genre;
    private String inventoryNumber;
}