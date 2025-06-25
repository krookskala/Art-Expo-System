package edu.pja.mas.s22899.artgallery.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExhibitionDetailDTO {
    private long id;
    private String title;
    private String description;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private int artworkCapacity;
}
